package com.pikolinc.meliecommerce.domain.dto.item;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Schema(description = "Item data transfer object for updating an Item")
public record ItemUpdateDTO(
        @Schema(description = "The name of the item", example = "iPhone 15")
        @NotBlank(message = "Name is required")
        String name,

        @Schema(description = "Description of the item", example = "The latest iPhone and the best...")
        String description,

        @Schema(description = "The price of the item", example = "3000")
        @Positive(message = "Price must be positive")
        Double price
) {
}
