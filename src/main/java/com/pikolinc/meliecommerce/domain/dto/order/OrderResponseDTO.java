package com.pikolinc.meliecommerce.domain.dto.order;

import java.time.LocalDate;

public record OrderResponseDTO(
        Long id,
        Long clientId,
        Long itemId,
        LocalDate purchaseDate,
        LocalDate deliveryDate
) { }