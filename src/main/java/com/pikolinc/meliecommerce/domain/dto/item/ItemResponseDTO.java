package com.pikolinc.meliecommerce.domain.dto.item;

public record ItemResponseDTO(
        Long id,
        String name,
        String description,
        Float price
) {
}
