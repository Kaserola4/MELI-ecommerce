package com.pikolinc.meliecommerce.domain.dto.item;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object (DTO) used to represent an {@link com.pikolinc.meliecommerce.domain.entity.Item}
 * in server responses.
 * <p>
 * This DTO provides a summary of the item, including its unique identifier, name,
 * description, and price.
 * </p>
 *
 * @param id          the unique identifier of the item
 * @param name        the name of the item
 * @param description a textual description of the item
 * @param price       the price of the item
 */
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
