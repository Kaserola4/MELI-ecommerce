package com.pikolinc.meliecommerce.domain.dto.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemCreateDTO(
        @NotBlank(message = "Name is required") String name,
        String description,
        @NotNull @Positive(message = "Price must be positive") Double price
) {
}
