package com.pikolinc.meliecommerce.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pikolinc.meliecommerce.domain.dto.item.ItemCreateDTO;
import com.pikolinc.meliecommerce.domain.dto.item.ItemUpdateDTO;
import com.pikolinc.meliecommerce.domain.entity.Item;
import com.pikolinc.meliecommerce.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        itemRepository.deleteAll();
    }

    // --------------------------------------------------
    // GET ALL ITEMS
    // --------------------------------------------------
    @Test
    void testGetAllItems_emptyPage() throws Exception {
        mockMvc.perform(get("/api/v1/items")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void testGetAllItems_success() throws Exception {
        itemRepository.saveAll(List.of(
                Item.builder().name("Laptop").description("Gaming Laptop").price(1200.0).build(),
                Item.builder().name("Mouse").description("Wireless Mouse").price(25.0).build()
        ));

        mockMvc.perform(get("/api/v1/items")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Laptop")))
                .andExpect(jsonPath("$[1].name", is("Mouse")));
    }

    // --------------------------------------------------
    // GET SINGLE ITEM
    // --------------------------------------------------
    @Test
    void testGetItemById_success() throws Exception {
        Item savedItem = itemRepository.save(
                Item.builder().name("Tablet").description("Android Tablet").price(300.0).build()
        );

        mockMvc.perform(get("/api/v1/items/" + savedItem.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(savedItem.getId().intValue())))
                .andExpect(jsonPath("$.name", is("Tablet")))
                .andExpect(jsonPath("$.price", is(300.0)));
    }

    @Test
    void testGetItemById_notFound() throws Exception {
        mockMvc.perform(get("/api/v1/items/999"))
                .andExpect(status().isNotFound());
    }

    // --------------------------------------------------
    // CREATE ITEM
    // --------------------------------------------------
    @Test
    void testCreateItem_success() throws Exception {
        ItemCreateDTO dto = new ItemCreateDTO("Monitor", "4K UHD Monitor", 500.0);

        mockMvc.perform(post("/api/v1/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is("Monitor")))
                .andExpect(jsonPath("$.price", is(500.0)));
    }

    @Test
    void testCreateItem_validationError() throws Exception {
        ItemCreateDTO invalidDto = new ItemCreateDTO("", "Invalid", -10.0);

        mockMvc.perform(post("/api/v1/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    // --------------------------------------------------
    // UPDATE ITEM
    // --------------------------------------------------
    @Test
    void testUpdateItem_success() throws Exception {
        Item existing = itemRepository.save(
                Item.builder().name("Keyboard").description("Mechanical Keyboard").price(100.0).build()
        );

        ItemUpdateDTO updateDTO = new ItemUpdateDTO("Keyboard Pro", "RGB Keyboard", 120.0);

        mockMvc.perform(put("/api/v1/items/" + existing.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(existing.getId().intValue())))
                .andExpect(jsonPath("$.name", is("Keyboard Pro")))
                .andExpect(jsonPath("$.price", is(120.0)));
    }

    @Test
    void testUpdateItem_notFound() throws Exception {
        ItemUpdateDTO updateDTO = new ItemUpdateDTO("Nonexistent", "Missing", 200.0);

        mockMvc.perform(put("/api/v1/items/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isNotFound());
    }

    // --------------------------------------------------
    // DELETE ITEM
    // --------------------------------------------------
    @Test
    void testDeleteItem_success() throws Exception {
        Item existing = itemRepository.save(
                Item.builder().name("Headphones").description("Noise Cancelling").price(250.0).build()
        );

        mockMvc.perform(delete("/api/v1/items/" + existing.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteItem_notFound() throws Exception {
        mockMvc.perform(delete("/api/v1/items/999"))
                .andExpect(status().isNotFound());
    }
}
