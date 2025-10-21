package com.pikolinc.meliecommerce.domain.dto.item;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Data Transfer Object (DTO) used to create a new {@link com.pikolinc.meliecommerce.domain.entity.Item}.
 * <p>
 * This DTO contains the information required to create an item in the system,
 * including its name, description, and price.
 * </p>
 *
 * Validation constraints ensure that required fields are provided and valid:
 * <ul>
 *     <li>{@code name} – must not be blank</li>
 *     <li>{@code price} – must not be null and must be positive</li>
 * </ul>
 *
 * @param name        the name of the item
 * @param description a textual description of the item
 * @param price       the price of the item
 */
@Schema(description = "Item data transfer object to request the creation of a new item")
public record ItemCreateDTO(
        @Schema(description = "The name of the item", example = "iPhone 15")
        @NotBlank(message = "Name is required")
        String name,

        @Schema(description = "Description of the item", example = "The latest iPhone and the best...")
        String description,

        @Schema(description = "The price of the item", example = "3000")
        @NotNull @Positive(message = "Price must be positive")
        Double price
) {
}
