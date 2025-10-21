package com.pikolinc.meliecommerce.domain.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) used to create a new {@link com.pikolinc.meliecommerce.domain.entity.Order}
 * for a specific client.
 * <p>
 * This DTO contains the necessary information to place an order for a client,
 * including the item to order, the purchase date, and the delivery date.
 * </p>
 *
 * Validation constraints ensure all fields are provided:
 * <ul>
 *     <li>{@code itemId} – must not be null</li>
 *     <li>{@code purchaseDate} – must not be null</li>
 *     <li>{@code deliveryDate} – must not be null</li>
 * </ul>
 *
 * @param itemId       Unique identifier of the item being ordered
 * @param purchaseDate The date when the order is placed
 * @param deliveryDate The expected delivery date for the order
 */
@Schema(description = "Data transfer object that represents the request of making a new order for a certain client")
public record OrderCreateForClientDTO(
        @Schema(description = "Item unique Identifier", example = "3")
        @NotNull(message = "Item ID is required") Long itemId,

        @Schema(description = "Order purchase date", example = "2025-02-02")
        @NotNull(message = "Purchase date is required") LocalDate purchaseDate,

        @Schema(description = "Order delivery date", example = "2025-02-02")
        @NotNull(message = "Delivery date is required") LocalDate deliveryDate
) { }
