package com.pikolinc.meliecommerce.domain.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Data Transfer Object (DTO) used to update an existing {@link com.pikolinc.meliecommerce.domain.entity.Client}.
 * <p>
 * This DTO contains the fields that can be updated for a client, including
 * name, address, and age.
 * </p>
 *
 * Validation constraints ensure updated values are valid:
 * <ul>
 *     <li>{@code name} – must not be blank</li>
 *     <li>{@code age} – must not be null and must be positive</li>
 * </ul>
 *
 * @param name    the updated full name of the client
 * @param address the updated address of the client
 * @param age     the updated age of the client
 */
@Schema(description = "Client data transfer object used to update an existing client")
public record ClientUpdateDTO(
        @Schema(description = "Client name", example = "Daniel Vargas")
        @NotBlank(message = "Name is required")
        String name,

        @Schema(description = "Client address", example = "Street 5")
        String address,

        @Schema(description = "Client age", example = "23")
        @NotNull @Positive Integer age
) {
}
