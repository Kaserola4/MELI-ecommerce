package com.pikolinc.meliecommerce.controller;

import com.pikolinc.meliecommerce.domain.dto.order.OrderCreateDTO;
import com.pikolinc.meliecommerce.domain.dto.order.OrderResponseDTO;
import com.pikolinc.meliecommerce.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Operations related to Orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping({"", "/"})
    @Operation(summary = "Get a page of orders")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(this.orderService.getAllOrders(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a single order with the given id")
    public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(this.orderService.getOrderById(id));
    }

    @PostMapping({"", "/"})
    @Operation(summary = "Create a new order")
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderCreateDTO orderCreateDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.orderService.createOrder(orderCreateDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an order with the given id")
    public ResponseEntity<OrderResponseDTO> updateOrder(
            @PathVariable Long id,
            @Valid @RequestBody OrderCreateDTO orderCreateDTO
    ) {
        return ResponseEntity.ok(this.orderService.updateOrder(id, orderCreateDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an order with the given id")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        this.orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
