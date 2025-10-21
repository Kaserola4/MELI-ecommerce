package com.pikolinc.meliecommerce.domain.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object (DTO) used to represent a {@link com.pikolinc.meliecommerce.domain.entity.Client}
 * in server responses.
 * <p>
 * This DTO provides a summary of the client, including its unique identifier,
 * name, address, and age.
 * </p>
 *
 * @param id      the unique identifier of the client
 * @param name    the full name of the client
 * @param address the address of the client
 * @param age     the age of the client
 */
@Schema(description = "Client Response data transfer object")
public record ClientResponseDTO(
        @Schema(description = "Client unique ID", example = "1")
        Long id,

        @Schema(description = "Client name", example = "Daniel Vargas")
        String name,

        @Schema(description = "Client address", example = "Address 5")
        String address,

        @Schema(description = "Client age", example = "23")
        Integer age
) {
}
