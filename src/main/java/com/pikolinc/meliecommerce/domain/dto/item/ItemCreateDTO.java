package com.pikolinc.meliecommerce.domain.dto.item;

public record ItemCreateDTO(
        String name,
        String description,
        Float price
) {
}
