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

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public List<ItemResponseDTO> getAllItems(Pageable pageable) {
        List<Item> items = this.itemRepository.findAll(pageable).getContent();

        if (items.isEmpty()) throw new NotFoundException("No items found");

        return items.stream().map(this::toResponseDTO).toList();
    }

    public ItemResponseDTO getItemById(Long id) {
        Item item = itemRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Item not found with id " + id));

        return toResponseDTO(item);
    }

    public ItemResponseDTO addItem(ItemCreateDTO itemCreateDTO) {
        Item item = toEntity(itemCreateDTO);

        Item savedItem = this.itemRepository.save(item);

        return toResponseDTO(savedItem);
    }

    public ItemResponseDTO updateItem(Long id, ItemUpdateDTO itemUpdateDTO) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item not found with id " + id));

        item.setName(itemUpdateDTO.name());
        item.setDescription(itemUpdateDTO.description());
        item.setPrice(itemUpdateDTO.price());

        Item updated = itemRepository.save(item);
        return toResponseDTO(updated);
    }

    public void deleteItem(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item not found with id " + id));

        itemRepository.delete(item);
    }


    private ItemResponseDTO toResponseDTO(Item item) {
        return new ItemResponseDTO(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getPrice()
        );
    }

    private Item toEntity(ItemCreateDTO itemCreateDTO) {
        return Item.builder()
                .name(itemCreateDTO.name())
                .description(itemCreateDTO.description())
                .price(itemCreateDTO.price())
                .build();
    }
}
