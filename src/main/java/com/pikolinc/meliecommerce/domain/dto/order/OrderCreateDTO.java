package com.pikolinc.meliecommerce.domain.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(description = "Order data transfer object used in creation requests")
public record OrderCreateDTO(
        @Schema(description = "Client unique Identifier", example = "1")
        @NotNull(message = "Client ID is required") Long clientId,
        @Schema(description = "Item unique Identifier", example = "4")
        @NotNull(message = "Item ID is required") Long itemId,
        @Schema(description = "Order purchase date", example = "2025-10-4")
        @NotNull(message = "Purchase date is required") LocalDate purchaseDate,
        @Schema(description = "Order delivery date", example = "20215-09-01")
        @NotNull(message = "Delivery date is required") LocalDate deliveryDate
) {
}
