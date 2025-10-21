package com.pikolinc.meliecommerce.repository;

import com.pikolinc.meliecommerce.domain.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link Item} entities in the database.
 * <p>
 * This interface extends {@link JpaRepository}, providing built-in CRUD operations such as:
 * </p>
 * <ul>
 *     <li>{@code save(Item entity)} – create or update an item</li>
 *     <li>{@code findById(Long id)} – retrieve an item by its ID</li>
 *     <li>{@code findAll()} – list all available items</li>
 *     <li>{@code delete(Item entity)} – remove an existing item</li>
 * </ul>
 *
 * <p><strong>Custom queries:</strong></p>
 * <p>You can define additional query methods following Spring Data JPA conventions,
 * for example {@code findByName(String name)}.</p>
 *
 * @see Item
 * @see org.springframework.data.jpa.repository.JpaRepository
 */
public interface ItemRepository extends JpaRepository<Item, Long> { }
