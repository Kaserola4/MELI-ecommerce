package com.pikolinc.meliecommerce.domain.dto.item;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Data Transfer Object (DTO) used to update an existing {@link com.pikolinc.meliecommerce.domain.entity.Item}.
 * <p>
 * This DTO contains the fields that can be updated for an item, including its
 * name, description, and price.
 * </p>
 *
 * Validation constraints ensure that updated values are valid:
 * <ul>
 *     <li>{@code name} – must not be blank</li>
 *     <li>{@code price} – must not be null and must be positive</li>
 * </ul>
 *
 * @param name        the updated name of the item
 * @param description the updated description of the item
 * @param price       the updated price of the item
 */
@Schema(description = "Item data transfer object used to update an existing item")
public record ItemUpdateDTO(
        @Schema(description = "The name of the item", example = "iPhone 15 Pro")
        @NotBlank(message = "Name is required")
        String name,

        @Schema(description = "Description of the item", example = "The latest iPhone with improved features")
        String description,

        @Schema(description = "The price of the item", example = "3500")
        @NotNull @Positive(message = "Price must be positive")
        Double price
) {
}
