package com.pikolinc.meliecommerce.domain.dto.item;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Item Response data transfer object")
public record ItemResponseDTO(
        @Schema(description = "Item unique Identifier", example = "20")
        Long id,
        @Schema(description = "The name of the item", example = "iPhone 15")
        String name,
        @Schema(description = "Description of the item", example = "The latest iPhone and the best...")
        String description,
        @Schema(description = "The price of the item", example = "3000")
        Double price
) {
}
