package com.pikolinc.meliecommerce.domain.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(description = "Data transfer object that represents the request of making a new order for a certain client")
public record OrderCreateForClientDTO(
        @Schema(description = "Item unique Identifier", example = "3")
        @NotNull(message = "Item ID is required") Long itemId,
        @Schema(description = "Order purchase date", example = "2025-02-02")
        @NotNull(message = "Purchase date is required") LocalDate purchaseDate,
        @Schema(description = "Order delivery date", example = "2025-02-02")
        @NotNull(message = "Delivery date is required") LocalDate deliveryDate
) { }
