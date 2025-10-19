package com.pikolinc.meliecommerce.domain.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Client Response data transfer object")
public record ClientResponseDTO(
        @Schema(description = "Client unique ID", example = "1")
        Long id,
        @Schema(description = "Client name", example = "Daniel Vargas")
        String name,
        @Schema(description = "Client address", example = "Address 5" )
        String address,
        @Schema(description = "Client age", example = "23")
        Integer age
) {
}
