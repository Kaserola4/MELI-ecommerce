package com.pikolinc.meliecommerce.controller;

import com.pikolinc.meliecommerce.domain.dto.item.ItemCreateDTO;
import com.pikolinc.meliecommerce.domain.dto.item.ItemResponseDTO;
import com.pikolinc.meliecommerce.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/items")
@RequiredArgsConstructor
class ItemController {
    private final ItemService itemService;

    @GetMapping({"", "/"})
    public ResponseEntity<List<ItemResponseDTO>> getAllItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(this.itemService.getAllItems(pageable));
    }

    @PostMapping({"", "/"})
    public ResponseEntity<ItemResponseDTO> addItem(@Valid @RequestBody ItemCreateDTO itemCreateDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.itemService.addItem(itemCreateDTO));
    }
}
