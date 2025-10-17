package com.pikolinc.meliecommerce.domain.dto.client;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ClientCreateDTO(
        @NotBlank(message = "Name is required")
        String name,
        String address,
        @NotNull @Positive Integer age
) {

}
