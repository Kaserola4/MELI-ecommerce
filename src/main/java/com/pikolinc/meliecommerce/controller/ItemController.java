package com.pikolinc.meliecommerce.controller;

import com.pikolinc.meliecommerce.domain.dto.item.ItemCreateDTO;
import com.pikolinc.meliecommerce.domain.entity.Item;
import com.pikolinc.meliecommerce.exception.NotFoundException;
import com.pikolinc.meliecommerce.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/items")
@RequiredArgsConstructor
class ItemController {
    private final ItemService itemService;

    @GetMapping({"", "/"})
    public ResponseEntity<List<Item>> getAllItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        List<Item> items = this.itemService.getAllItems(pageable); // Check

        if (items.isEmpty()) {
            throw new NotFoundException("No items found");
        }

        return ResponseEntity.ok(items);
    }

    @PostMapping({"", "/"})
    public ResponseEntity<Item> addItem(@RequestBody ItemCreateDTO itemCreateDTO) {

    }
}
