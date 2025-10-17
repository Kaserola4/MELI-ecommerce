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

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ItemRepository itemRepository;

    public List<OrderResponseDTO> getAllOrders(Pageable pageable) {
        List<Order> orders = this.orderRepository.findAll(pageable).getContent();

        if (orders.isEmpty()) throw new NotFoundException("No orders found");

        return orderRepository.findAll(pageable)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public OrderResponseDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found with id " + id));
        return toResponseDTO(order);
    }

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

    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found with id " + id));
        orderRepository.delete(order);
    }

    private OrderResponseDTO toResponseDTO(Order order) {
        return new OrderResponseDTO(
                order.getId(),
                order.getClient().getId(),
                order.getItem().getId(),
                order.getPurchaseDate(),
                order.getDeliveryDate()
        );
    }
}
