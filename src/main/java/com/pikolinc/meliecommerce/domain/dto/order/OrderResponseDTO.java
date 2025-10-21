package com.pikolinc.meliecommerce.domain.dto.order;

import com.pikolinc.meliecommerce.domain.dto.client.ClientResponseDTO;
import com.pikolinc.meliecommerce.domain.dto.item.ItemResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) used to represent an {@link com.pikolinc.meliecommerce.domain.entity.Order}
 * in server responses.
 * <p>
 * This DTO contains summary information about the order, including the associated client,
 * the ordered item, and relevant dates.
 * </p>
 *
 * @param id               Unique identifier of the order
 * @param clientResponseDTO Summary information about the client who placed the order
 * @param itemResponseDTO   Summary information about the item included in the order
 * @param purchaseDate      The date when the order was placed
 * @param deliveryDate      The expected delivery date for the order
 */
@Schema(description = "Order data transfer object that the server sends back given a request")
public record OrderResponseDTO(
        @Schema(description = "Order unique Identifier", example = "2")
        Long id,

        @Schema(description = "Client summary of order", implementation = ClientResponseDTO.class)
        ClientResponseDTO clientResponseDTO,

        @Schema(description = "Item summary of order", implementation = ItemResponseDTO.class)
        ItemResponseDTO itemResponseDTO,

        @Schema(description = "Order purchase date", example = "2022-02-04")
        LocalDate purchaseDate,

        @Schema(description = "Order delivery date", example = "2022-03-04")
        LocalDate deliveryDate
) { }
