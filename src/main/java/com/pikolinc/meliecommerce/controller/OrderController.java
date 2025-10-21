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

/**
 * REST controller for managing {@link com.pikolinc.meliecommerce.domain.entity.Order} entities.
 * <p>
 * Provides endpoints for creating, reading, updating, and deleting orders.
 * </p>
 */
@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Operations related to Orders")
public class OrderController {

    private final OrderService orderService;

    /**
     * Retrieves a paginated list of orders.
     *
     * @param page the page number to retrieve (default is 0)
     * @param size the number of items per page (default is 10)
     * @return a {@link ResponseEntity} containing a list of {@link OrderResponseDTO}
     */
    @GetMapping({"", "/"})
    @Operation(summary = "Get a page of orders")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(this.orderService.getAllOrders(pageable));
    }

    /**
     * Retrieves a single order by its unique identifier.
     *
     * @param id the unique identifier of the order
     * @return a {@link ResponseEntity} containing the {@link OrderResponseDTO}
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get a single order with the given id")
    public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(this.orderService.getOrderById(id));
    }

    /**
     * Creates a new order.
     *
     * @param orderCreateDTO the data transfer object containing order creation details
     * @return a {@link ResponseEntity} containing the created {@link OrderResponseDTO} with HTTP status 201
     */
    @PostMapping({"", "/"})
    @Operation(summary = "Create a new order")
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderCreateDTO orderCreateDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.orderService.createOrder(orderCreateDTO));
    }

    /**
     * Updates an existing order identified by its unique identifier.
     *
     * @param id the unique identifier of the order to update
     * @param orderCreateDTO the data transfer object containing updated order details
     * @return a {@link ResponseEntity} containing the updated {@link OrderResponseDTO}
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update an order with the given id")
    public ResponseEntity<OrderResponseDTO> updateOrder(
            @PathVariable Long id,
            @Valid @RequestBody OrderCreateDTO orderCreateDTO
    ) {
        return ResponseEntity.ok(this.orderService.updateOrder(id, orderCreateDTO));
    }

    /**
     * Deletes an order identified by its unique identifier.
     *
     * @param id the unique identifier of the order to delete
     * @return a {@link ResponseEntity} with HTTP status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an order with the given id")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        this.orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
