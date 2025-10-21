package com.pikolinc.meliecommerce.service;

import com.pikolinc.meliecommerce.domain.dto.item.ItemCreateDTO;
import com.pikolinc.meliecommerce.domain.dto.item.ItemResponseDTO;
import com.pikolinc.meliecommerce.domain.dto.item.ItemUpdateDTO;
import com.pikolinc.meliecommerce.domain.entity.Item;
import com.pikolinc.meliecommerce.exception.NotFoundException;
import com.pikolinc.meliecommerce.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class responsible for managing {@link Item} entities and related operations.
 * <p>
 * Provides methods for creating, retrieving, updating, and deleting items, as well as
 * utility methods for converting between entity and DTO representations.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    /**
     * Retrieves all items with pagination support.
     *
     * @param pageable pagination information.
     * @return a list of {@link ItemResponseDTO} representing the items.
     */
    public List<ItemResponseDTO> getAllItems(Pageable pageable) {
        List<Item> items = this.itemRepository.findAll(pageable).getContent();
        return items.stream().map(ItemService::toResponseDTO).toList();
    }

    /**
     * Retrieves a single item by its unique identifier.
     *
     * @param id the ID of the item to retrieve.
     * @return an {@link ItemResponseDTO} representing the requested item.
     * @throws NotFoundException if no item exists with the specified ID.
     */
    public ItemResponseDTO getItemById(Long id) {
        Item item = itemRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Item not found with id " + id));

        return toResponseDTO(item);
    }

    /**
     * Creates a new item.
     *
     * @param itemCreateDTO the data transfer object containing the new item details.
     * @return an {@link ItemResponseDTO} representing the created item.
     */
    public ItemResponseDTO addItem(ItemCreateDTO itemCreateDTO) {
        Item item = toEntity(itemCreateDTO);
        Item savedItem = this.itemRepository.save(item);
        return toResponseDTO(savedItem);
    }

    /**
     * Updates an existing item.
     *
     * @param id             the ID of the item to update.
     * @param itemUpdateDTO  the data transfer object containing updated item details.
     * @return an {@link ItemResponseDTO} representing the updated item.
     * @throws NotFoundException if no item exists with the specified ID.
     */
    public ItemResponseDTO updateItem(Long id, ItemUpdateDTO itemUpdateDTO) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item not found with id " + id));

        item.setName(itemUpdateDTO.name());
        item.setDescription(itemUpdateDTO.description());
        item.setPrice(itemUpdateDTO.price());

        Item updated = itemRepository.save(item);
        return toResponseDTO(updated);
    }

    /**
     * Deletes an item by its ID.
     *
     * @param id the ID of the item to delete.
     * @throws NotFoundException if no item exists with the specified ID.
     */
    public void deleteItem(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item not found with id " + id));

        itemRepository.delete(item);
    }

    /**
     * Converts an {@link Item} entity to an {@link ItemResponseDTO}.
     *
     * @param item the item entity to convert.
     * @return an {@link ItemResponseDTO} containing item information.
     */
    public static ItemResponseDTO toResponseDTO(Item item) {
        return new ItemResponseDTO(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getPrice()
        );
    }

    /**
     * Converts an {@link ItemCreateDTO} to an {@link Item} entity.
     *
     * @param itemCreateDTO the DTO containing item creation data.
     * @return a new {@link Item} entity.
     */
    private Item toEntity(ItemCreateDTO itemCreateDTO) {
        return Item.builder()
                .name(itemCreateDTO.name())
                .description(itemCreateDTO.description())
                .price(itemCreateDTO.price())
                .build();
    }
}
