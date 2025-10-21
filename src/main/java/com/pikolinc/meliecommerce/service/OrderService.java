package com.pikolinc.meliecommerce.service;

import com.pikolinc.meliecommerce.domain.dto.order.OrderCreateDTO;
import com.pikolinc.meliecommerce.domain.dto.order.OrderResponseDTO;
import com.pikolinc.meliecommerce.domain.entity.Client;
import com.pikolinc.meliecommerce.domain.entity.Item;
import com.pikolinc.meliecommerce.domain.entity.Order;
import com.pikolinc.meliecommerce.exception.NotFoundException;
import com.pikolinc.meliecommerce.repository.ClientRepository;
import com.pikolinc.meliecommerce.repository.ItemRepository;
import com.pikolinc.meliecommerce.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class responsible for managing {@link Order} entities and related operations.
 * <p>
 * Provides methods for creating, retrieving, updating, and deleting orders,
 * as well as utility methods for mapping entities to DTOs.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ItemRepository itemRepository;

    /**
     * Retrieves all orders with pagination support.
     *
     * @param pageable pagination information.
     * @return a list of {@link OrderResponseDTO} representing the retrieved orders.
     */
    public List<OrderResponseDTO> getAllOrders(Pageable pageable) {
        List<Order> orders = this.orderRepository.findAll(pageable).getContent();
        return orders.stream()
                .map(OrderService::toResponseDTO)
                .toList();
    }

    /**
     * Retrieves a specific order by its unique identifier.
     *
     * @param id the ID of the order to retrieve.
     * @return an {@link OrderResponseDTO} representing the requested order.
     * @throws NotFoundException if no order exists with the specified ID.
     */
    public OrderResponseDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found with id " + id));
        return toResponseDTO(order);
    }

    /**
     * Creates a new order using the provided data.
     *
     * @param orderCreateDTO the data transfer object containing the order details.
     * @return an {@link OrderResponseDTO} representing the created order.
     * @throws NotFoundException if the client or item specified in the DTO cannot be found.
     */
    public OrderResponseDTO createOrder(OrderCreateDTO orderCreateDTO) {
        Client client = clientRepository.findById(orderCreateDTO.clientId())
                .orElseThrow(() -> new NotFoundException("Client not found with id " + orderCreateDTO.clientId()));

        Item item = itemRepository.findById(orderCreateDTO.itemId())
                .orElseThrow(() -> new NotFoundException("Item not found with id " + orderCreateDTO.itemId()));

        Order order = Order.builder()
                .client(client)
                .item(item)
                .purchaseDate(orderCreateDTO.purchaseDate())
                .deliveryDate(orderCreateDTO.deliveryDate())
                .build();

        Order savedOrder = orderRepository.save(order);
        return toResponseDTO(savedOrder);
    }

    /**
     * Updates an existing order with new data.
     *
     * @param id  the ID of the order to update.
     * @param dto the data transfer object containing the updated order details.
     * @return an {@link OrderResponseDTO} representing the updated order.
     * @throws NotFoundException if the order, client, or item specified cannot be found.
     */
    public OrderResponseDTO updateOrder(Long id, OrderCreateDTO dto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found with id " + id));

        Client client = clientRepository.findById(dto.clientId())
                .orElseThrow(() -> new NotFoundException("Client not found with id " + dto.clientId()));

        Item item = itemRepository.findById(dto.itemId())
                .orElseThrow(() -> new NotFoundException("Item not found with id " + dto.itemId()));

        order.setClient(client);
        order.setItem(item);
        order.setPurchaseDate(dto.purchaseDate());
        order.setDeliveryDate(dto.deliveryDate());

        Order updated = orderRepository.save(order);
        return toResponseDTO(updated);
    }

    /**
     * Deletes an order by its ID.
     *
     * @param id the ID of the order to delete.
     * @throws NotFoundException if no order exists with the specified ID.
     */
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found with id " + id));
        orderRepository.delete(order);
    }

    /**
     * Converts an {@link Order} entity to an {@link OrderResponseDTO}.
     *
     * @param order the order entity to convert.
     * @return an {@link OrderResponseDTO} containing detailed order information.
     */
    public static OrderResponseDTO toResponseDTO(Order order) {
        return new OrderResponseDTO(
                order.getId(),
                ClientService.toResponseDTO(order.getClient()),
                ItemService.toResponseDTO(order.getItem()),
                order.getPurchaseDate(),
                order.getDeliveryDate()
        );
    }
}
