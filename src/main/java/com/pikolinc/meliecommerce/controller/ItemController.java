package com.pikolinc.meliecommerce.controller;

import com.pikolinc.meliecommerce.domain.entity.Item;
import com.pikolinc.meliecommerce.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
class ItemController {
    private final ItemService itemService;

    @RequestMapping("/")
    public ResponseEntity<List<Item>> getAllItems(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
        ) {
        Pageable pageable = PageRequest.of(page, size);

        List<Item> items = this.itemService.getAllItems(pageable); // Check

        if (items.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(items);
    }
}
