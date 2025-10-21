package com.pikolinc.meliecommerce.domain.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Data Transfer Object (DTO) used to create a new {@link com.pikolinc.meliecommerce.domain.entity.Client}.
 * <p>
 * This DTO contains the required information to add a client to the system,
 * including name, address, and age.
 * </p>
 *
 * Validation constraints ensure required fields are provided and valid:
 * <ul>
 *     <li>{@code name} – must not be blank</li>
 *     <li>{@code age} – must not be null and must be positive</li>
 * </ul>
 *
 * @param name    the full name of the client
 * @param address the address of the client
 * @param age     the age of the client; must be positive
 */
@Schema(description = "Client data transfer object to request the creation of a new client")
public record ClientCreateDTO(
        @Schema(description = "Client name", example = "Daniel Vargas")
        @NotBlank(message = "Name is required")
        String name,

        @Schema(description = "Client address", example = "Street 5")
        String address,

        @Schema(description = "Client age", example = "23")
        @NotNull @Positive Integer age
) {
}

