package com.pikolinc.meliecommerce.domain.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) used to create a new {@link com.pikolinc.meliecommerce.domain.entity.Order}.
 * <p>
 * This object contains all required information to create an order, including
 * the client, item, purchase date, and delivery date.
 * </p>
 *
 * Validation constraints ensure all fields are provided:
 * <ul>
 *     <li>{@code clientId} – must not be null</li>
 *     <li>{@code itemId} – must not be null</li>
 *     <li>{@code purchaseDate} – must not be null</li>
 *     <li>{@code deliveryDate} – must not be null</li>
 * </ul>
 *
 * @param clientId     Unique identifier of the client placing the order
 * @param itemId       Unique identifier of the item being ordered
 * @param purchaseDate The date the order is placed
 * @param deliveryDate The expected delivery date for the order
 */
@Schema(description = "Order data transfer object used in creation requests")
public record OrderCreateDTO(
        @Schema(description = "Client unique Identifier", example = "1")
        @NotNull(message = "Client ID is required") Long clientId,

        @Schema(description = "Item unique Identifier", example = "4")
        @NotNull(message = "Item ID is required") Long itemId,

        @Schema(description = "Order purchase date", example = "2025-10-04")
        @NotNull(message = "Purchase date is required") LocalDate purchaseDate,

        @Schema(description = "Order delivery date", example = "2025-09-01")
        @NotNull(message = "Delivery date is required") LocalDate deliveryDate
) {
}
