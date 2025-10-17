    package com.pikolinc.meliecommerce.service;

    import com.pikolinc.meliecommerce.domain.entity.Item;
    import com.pikolinc.meliecommerce.repository.ItemRepository;
    import lombok.RequiredArgsConstructor;
    import org.springframework.data.domain.Pageable;
    import org.springframework.stereotype.Service;

    import java.util.List;

    @Service
    @RequiredArgsConstructor
    public class ItemService {
        private final ItemRepository itemRepository;

        public List<Item> getAllItems(Pageable pageable) {
            return this.itemRepository.findAll(pageable).getContent();
        }
    }
