package com.pikolinc.meliecommerce.domain.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Schema(description = "Client data transfer object to request the update of a certain client")
public record ClientUpdateDTO(
        @NotBlank(message = "Name is required")
        @Schema(description = "Client name", example = "Daniel Vargas")
        String name,
        @Schema(description = "Client address", example = "Address 5")
        String address,
        @Positive
        @Schema(description = "Client age", example = "23")
        Integer age
) {
}
