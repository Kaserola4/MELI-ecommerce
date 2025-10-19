package com.pikolinc.meliecommerce.service;

import com.pikolinc.meliecommerce.domain.dto.order.OrderCreateDTO;
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

@Service
@RequiredArgsConstructor
public class ClientOrderService {
    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    public List<OrderResponseDTO> getOrdersByClientId(
            Long clientId,
            Pageable pageable
    ) {
        if (!clientRepository.existsById(clientId)) {
            throw new NotFoundException("Client not found with ID: " + clientId);
        }

        Page<Order> ordersPage = orderRepository.findByClientId(clientId, pageable);

        return ordersPage.stream()
                .map(OrderService::toResponseDTO)
                .toList();
    }

    public OrderResponseDTO getOrderByClientAndId(
            Long clientId,
            Long orderId
    ) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found with ID: " + orderId));

        if (!order.getClient().getId().equals(clientId))
            throw new ForbiddenException("Order " + orderId + " does not belong to client " + clientId);

        return OrderService.toResponseDTO(order);
    }

    public OrderResponseDTO createOrderForClient(
            Long clientId,
            OrderCreateForClientDTO requestDTO
    ) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Client not found with ID: " + clientId));

        Item item = itemRepository.findById(requestDTO.itemId())
                .orElseThrow(() -> new NotFoundException("Item not found with ID: " + requestDTO.itemId()));

        Order order = Order.builder()
                .client(client)
                .item(item)
                .purchaseDate(requestDTO.purchaseDate())
                .purchaseDate(requestDTO.deliveryDate())
                .build();

        Order savedOrder = orderRepository.save(order);

        return OrderService.toResponseDTO(savedOrder);
    }

    public OrderResponseDTO updateOrder(
            Long clientId,
            Long orderId,
            OrderCreateForClientDTO requestDTO) {

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

    public void deleteOrder(@Min(1) Long clientId, @Min(1) Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found with ID: " + orderId));

        if (!order.getClient().getId().equals(clientId))
            throw new ForbiddenException("Order " + orderId + " does not belong to client " + clientId);

        orderRepository.delete(order);
    }
}