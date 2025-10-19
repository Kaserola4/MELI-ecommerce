package com.pikolinc.meliecommerce.domain.dto.order;

import com.pikolinc.meliecommerce.domain.dto.client.ClientResponseDTO;
import com.pikolinc.meliecommerce.domain.dto.item.ItemResponseDTO;

import java.time.LocalDate;

public record OrderResponseDTO(
        Long id,
        ClientResponseDTO clientResponseDTO,
        ItemResponseDTO itemResponseDTO,
        LocalDate purchaseDate,
        LocalDate deliveryDate
) { }