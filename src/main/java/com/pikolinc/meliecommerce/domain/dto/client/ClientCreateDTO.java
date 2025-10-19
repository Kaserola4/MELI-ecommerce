package com.pikolinc.meliecommerce.domain.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Client data transfer object to request the creation of a new client")
public record ClientCreateDTO(
        @Schema(description = "Client name", example = "Daniel Vargas" )
        @NotBlank(message = "Name is required")
        String name,
        @Schema(description = "Client address", example = "Street 5")
        String address,
        @Schema(description = "Client age", example = "23")
        @NotNull @Positive Integer age
) {
}
