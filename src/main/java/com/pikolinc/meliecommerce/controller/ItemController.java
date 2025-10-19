package com.pikolinc.meliecommerce.controller;

import com.pikolinc.meliecommerce.domain.dto.item.ItemCreateDTO;
import com.pikolinc.meliecommerce.domain.dto.item.ItemResponseDTO;
import com.pikolinc.meliecommerce.domain.dto.item.ItemUpdateDTO;
import com.pikolinc.meliecommerce.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Items", description = "Operations related to Items")
class ItemController {
    private final ItemService itemService;

    @GetMapping({"", "/"})
    @Operation(summary = "Get a page of Items")
    public ResponseEntity<List<ItemResponseDTO>> getAllItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(this.itemService.getAllItems(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a single item with the given id")
    public ResponseEntity<ItemResponseDTO> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(this.itemService.getItemById(id));
    }

    @PostMapping({"", "/"})
    @Operation(summary = "Create an item")
    public ResponseEntity<ItemResponseDTO> addItem(@Valid @RequestBody ItemCreateDTO itemCreateDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.itemService.addItem(itemCreateDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an item of the given id")
    public ResponseEntity<ItemResponseDTO> updateItem(
            @PathVariable Long id,
            @Valid @RequestBody ItemUpdateDTO dto
    ) {
        return ResponseEntity.ok(this.itemService.updateItem(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete the item of the given id")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        this.itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
