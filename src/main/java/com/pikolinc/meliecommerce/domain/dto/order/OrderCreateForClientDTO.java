package com.pikolinc.meliecommerce.domain.dto.order;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record OrderCreateForClientDTO(
        @NotNull(message = "Item ID is required") Long itemId,
        @NotNull(message = "Purchase date is required") LocalDate purchaseDate,
        @NotNull(message = "Delivery date is required") LocalDate deliveryDate
) { }
