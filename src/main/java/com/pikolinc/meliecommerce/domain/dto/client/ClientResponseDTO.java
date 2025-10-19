package com.pikolinc.meliecommerce.domain.dto.client;

public record ClientResponseDTO(
        Long id,
        String name,
        String address,
        Integer age
) {
}
