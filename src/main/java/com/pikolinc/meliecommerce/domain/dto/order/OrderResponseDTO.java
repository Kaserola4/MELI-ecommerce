package com.pikolinc.meliecommerce.domain.dto.order;

import com.pikolinc.meliecommerce.domain.dto.client.ClientResponseDTO;
import com.pikolinc.meliecommerce.domain.dto.item.ItemResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

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