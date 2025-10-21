package com.pikolinc.meliecommerce.service;

import com.pikolinc.meliecommerce.domain.dto.order.OrderCreateForClientDTO;
import com.pikolinc.meliecommerce.domain.dto.order.OrderResponseDTO;
import com.pikolinc.meliecommerce.domain.entity.Client;
import com.pikolinc.meliecommerce.domain.entity.Item;
import com.pikolinc.meliecommerce.domain.entity.Order;
import com.pikolinc.meliecommerce.exception.ForbiddenException;
import com.pikolinc.meliecommerce.exception.NotFoundException;
import com.pikolinc.meliecommerce.repository.ClientRepository;
import com.pikolinc.meliecommerce.repository.ItemRepository;
import com.pikolinc.meliecommerce.repository.OrderRepository;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service responsible for handling operations related to orders belonging to specific clients.
 * <p>
 * Provides methods to retrieve, create, update, and delete orders, enforcing that
 * each order is properly associated with the correct client.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class ClientOrderService {

    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    /**
     * Retrieves a paginated list of orders associated with a given client.
     *
     * @param clientId the ID of the client whose orders are being fetched.
     * @param pageable pagination information.
     * @return a list of {@link OrderResponseDTO} representing the client's orders.
     * @throws NotFoundException if no client exists with the provided ID.
     */
    public List<OrderResponseDTO> getOrdersByClientId(Long clientId, Pageable pageable) {
        if (!clientRepository.existsById(clientId)) {
            throw new NotFoundException("Client not found with ID: " + clientId);
        }

        Page<Order> ordersPage = orderRepository.findByClientId(clientId, pageable);
        return ordersPage.stream()
                .map(OrderService::toResponseDTO)
                .toList();
    }

    /**
     * Retrieves a specific order for a client by its ID, validating ownership.
     *
     * @param clientId the ID of the client.
     * @param orderId  the ID of the order to retrieve.
     * @return an {@link OrderResponseDTO} representing the requested order.
     * @throws NotFoundException   if the order does not exist.
     * @throws ForbiddenException  if the order does not belong to the specified client.
     */
    public OrderResponseDTO getOrderByClientAndId(Long clientId, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found with ID: " + orderId));

        if (!order.getClient().getId().equals(clientId))
            throw new ForbiddenException("Order " + orderId + " does not belong to client " + clientId);

        return OrderService.toResponseDTO(order);
    }

    /**
     * Creates a new order for the specified client.
     *
     * @param clientId   the ID of the client placing the order.
     * @param requestDTO the data transfer object containing order creation details.
     * @return the created order as an {@link OrderResponseDTO}.
     * @throws NotFoundException if either the client or the item does not exist.
     */
    public OrderResponseDTO createOrderForClient(Long clientId, OrderCreateForClientDTO requestDTO) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Client not found with ID: " + clientId));

        Item item = itemRepository.findById(requestDTO.itemId())
                .orElseThrow(() -> new NotFoundException("Item not found with ID: " + requestDTO.itemId()));

        Order order = Order.builder()
                .client(client)
                .item(item)
                .purchaseDate(requestDTO.purchaseDate())
                .deliveryDate(requestDTO.deliveryDate())
                .build();

        Order savedOrder = orderRepository.save(order);
        return OrderService.toResponseDTO(savedOrder);
    }

    /**
     * Updates an existing order for a given client.
     *
     * @param clientId   the ID of the client who owns the order.
     * @param orderId    the ID of the order to update.
     * @param requestDTO the data transfer object containing updated order details.
     * @return the updated order as an {@link OrderResponseDTO}.
     * @throws NotFoundException  if the order or new item does not exist.
     * @throws ForbiddenException if the order does not belong to the specified client.
     */
    public OrderResponseDTO updateOrder(Long clientId, Long orderId, OrderCreateForClientDTO requestDTO) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found with ID: " + orderId));

        if (!order.getClient().getId().equals(clientId))
            throw new ForbiddenException("Order " + orderId + " does not belong to client " + clientId);

        Item newItem = itemRepository.findById(requestDTO.itemId())
                .orElseThrow(() -> new NotFoundException("Item not found with ID: " + requestDTO.itemId()));

        order.setItem(newItem);
        order.setPurchaseDate(requestDTO.purchaseDate());
        order.setDeliveryDate(requestDTO.deliveryDate());

        orderRepository.save(order);
        return OrderService.toResponseDTO(order);
    }

    /**
     * Deletes an order belonging to a specific client.
     *
     * @param clientId the ID of the client.
     * @param orderId  the ID of the order to delete.
     * @throws NotFoundException  if the order does not exist.
     * @throws ForbiddenException if the order does not belong to the specified client.
     */
    public void deleteOrder(@Min(1) Long clientId, @Min(1) Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found with ID: " + orderId));

        if (!order.getClient().getId().equals(clientId))
            throw new ForbiddenException("Order " + orderId + " does not belong to client " + clientId);

        orderRepository.delete(order);
    }
}
