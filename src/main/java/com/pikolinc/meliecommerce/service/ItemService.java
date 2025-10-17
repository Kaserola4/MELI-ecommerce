    package com.pikolinc.meliecommerce.service;

    import com.pikolinc.meliecommerce.domain.dto.item.ItemCreateDTO;
    import com.pikolinc.meliecommerce.domain.dto.item.ItemResponseDTO;
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

        public ItemResponseDTO addItem(ItemCreateDTO itemCreateDTO) {
            Item item = toEntity(itemCreateDTO);

            Item savedItem = this.itemRepository.save(item);

            return toResponseDTO(savedItem);
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
            Item item = new Item();
            item.setName(itemCreateDTO.name());
            item.setPrice(itemCreateDTO.price());
            item.setDescription(itemCreateDTO.description());
            return item;
        }
    }
