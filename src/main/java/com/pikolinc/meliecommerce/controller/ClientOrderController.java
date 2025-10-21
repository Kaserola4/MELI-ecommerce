package com.pikolinc.meliecommerce.controller;

import com.pikolinc.meliecommerce.domain.dto.order.OrderCreateForClientDTO;
import com.pikolinc.meliecommerce.domain.dto.order.OrderResponseDTO;
import com.pikolinc.meliecommerce.exception.NotFoundException;
import com.pikolinc.meliecommerce.service.ClientOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller that manages orders scoped to a specific client.
 * <p>
 * Provides endpoints to retrieve, create, update, and delete orders
 * that belong to a particular client.
 * </p>
 */
@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
@Validated
@Tag(name = "Clients and Orders scope", description = "Operations of orders scoped to clients")
public class ClientOrderController {

    public final ClientOrderService clientOrderService;

    /**
     * Retrieves a paginated list of orders for a given client.
     *
     * @param id   the unique identifier of the client
     * @param page the page number to retrieve (default is 0)
     * @param size the number of orders per page (default is 10)
     * @return a {@link ResponseEntity} containing a list of {@link OrderResponseDTO}
     * @throws NotFoundException if no orders are found for the given client
     */
    @GetMapping({"/{id}/orders"})
    @Operation(summary = "Get a page of orders of a certain client")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByClientId(
            @PathVariable @Min(1) Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        final Pageable pageable = PageRequest.of(page, size);

        List<OrderResponseDTO> orders = clientOrderService.getOrdersByClientId(id, pageable);

        if (orders.isEmpty())
            throw new NotFoundException("Orders not found for client Id: " + id);

        return ResponseEntity.ok(orders);
    }

    /**
     * Retrieves a single order for a specific client.
     *
     * @param clientId the unique identifier of the client
     * @param orderId  the unique identifier of the order
     * @return a {@link ResponseEntity} containing the {@link OrderResponseDTO}
     * @throws NotFoundException if the order is not found for the given client
     */
    @GetMapping({"/{clientId}/orders/{orderId}"})
    @Operation(summary = "Get a single order of a certain client")
    public ResponseEntity<OrderResponseDTO> getOrderByClientAndId(
            @PathVariable @Min(1) Long clientId,
            @PathVariable @Min(1) Long orderId
    ) {
        OrderResponseDTO order = clientOrderService.getOrderByClientAndId(clientId, orderId);

        if (order == null)
            throw new NotFoundException("Order not found for client Id: " + clientId);

        return ResponseEntity.ok(order);
    }

    /**
     * Creates a new order for a specific client.
     *
     * @param clientId   the unique identifier of the client
     * @param requestDTO the data transfer object containing order creation details
     * @return a {@link ResponseEntity} containing the created {@link OrderResponseDTO} with HTTP status 201
     */
    @PostMapping("/{clientId}/orders")
    @Operation(summary = "Create an order of a certain client")
    public ResponseEntity<OrderResponseDTO> createOrder(
            @PathVariable @Min(1) Long clientId,
            @Valid @RequestBody OrderCreateForClientDTO requestDTO
    ) {
        OrderResponseDTO createdOrder = clientOrderService.createOrderForClient(clientId, requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    /**
     * Updates an existing order for a specific client.
     *
     * @param clientId   the unique identifier of the client
     * @param orderId    the unique identifier of the order
     * @param requestDTO the data transfer object containing updated order details
     * @return a {@link ResponseEntity} containing the updated {@link OrderResponseDTO}
     */
    @PutMapping("/{clientId}/orders/{orderId}")
    @Operation(summary = "Update an order of a certain client")
    public ResponseEntity<OrderResponseDTO> updateOrder(
            @PathVariable @Min(1) Long clientId,
            @PathVariable @Min(1) Long orderId,
            @Valid @RequestBody OrderCreateForClientDTO requestDTO
    ) {
        OrderResponseDTO updatedOrder = clientOrderService.updateOrder(clientId, orderId, requestDTO);
        return ResponseEntity.ok(updatedOrder);
    }

    /**
     * Deletes an order for a specific client.
     *
     * @param clientId the unique identifier of the client
     * @param orderId  the unique identifier of the order
     * @return a {@link ResponseEntity} with HTTP status 204 (No Content)
     */
    @DeleteMapping("/{clientId}/orders/{orderId}")
    @Operation(summary = "Delete an order of a certain client")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable @Min(1) Long clientId,
            @PathVariable @Min(1) Long orderId
    ) {
        clientOrderService.deleteOrder(clientId, orderId);
        return ResponseEntity.noContent().build();
    }
}
