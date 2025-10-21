package com.pikolinc.meliecommerce.controller;

import com.pikolinc.meliecommerce.domain.dto.item.ItemCreateDTO;
import com.pikolinc.meliecommerce.domain.dto.item.ItemResponseDTO;
import com.pikolinc.meliecommerce.domain.dto.item.ItemUpdateDTO;
import com.pikolinc.meliecommerce.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing {@link com.pikolinc.meliecommerce.domain.entity.Item} entities.
 * <p>
 * Provides endpoints for creating, reading, updating, and deleting items.
 * </p>
 */
@RestController
@RequestMapping("api/v1/items")
@RequiredArgsConstructor
@Tag(name = "Items", description = "Operations related to Items")
public class ItemController {

    private final ItemService itemService;

    /**
     * Retrieves a paginated list of items.
     *
     * @param page the page number to retrieve (default is 0)
     * @param size the number of items per page (default is 10)
     * @return a {@link ResponseEntity} containing a list of {@link ItemResponseDTO}
     */
    @GetMapping({"", "/"})
    @Operation(summary = "Get a page of Items")
    public ResponseEntity<List<ItemResponseDTO>> getAllItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(this.itemService.getAllItems(pageable));
    }

    /**
     * Retrieves a single item by its unique identifier.
     *
     * @param id the unique identifier of the item
     * @return a {@link ResponseEntity} containing the {@link ItemResponseDTO}
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get a single item with the given id")
    public ResponseEntity<ItemResponseDTO> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(this.itemService.getItemById(id));
    }

    /**
     * Creates a new item.
     *
     * @param itemCreateDTO the data transfer object containing item creation details
     * @return a {@link ResponseEntity} containing the created {@link ItemResponseDTO} with HTTP status 201
     */
    @PostMapping({"", "/"})
    @Operation(summary = "Create an item")
    public ResponseEntity<ItemResponseDTO> addItem(@Valid @RequestBody ItemCreateDTO itemCreateDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.itemService.addItem(itemCreateDTO));
    }

    /**
     * Updates an existing item identified by its unique identifier.
     *
     * @param id  the unique identifier of the item to update
     * @param dto the data transfer object containing updated item details
     * @return a {@link ResponseEntity} containing the updated {@link ItemResponseDTO}
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update an item of the given id")
    public ResponseEntity<ItemResponseDTO> updateItem(
            @PathVariable Long id,
            @Valid @RequestBody ItemUpdateDTO dto
    ) {
        return ResponseEntity.ok(this.itemService.updateItem(id, dto));
    }

    /**
     * Deletes an item identified by its unique identifier.
     *
     * @param id the unique identifier of the item to delete
     * @return a {@link ResponseEntity} with HTTP status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete the item of the given id")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        this.itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
