package com.pikolinc.meliecommerce.domain.dto.client;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ClientUpdateDTO(
        @NotBlank(message = "Name is required")
        String name,
        String address,
        @Positive Integer age
) {
}
