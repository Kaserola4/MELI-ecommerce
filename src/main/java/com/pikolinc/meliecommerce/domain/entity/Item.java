package com.pikolinc.meliecommerce.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an item available for purchase in the system.
 * <p>
 * This entity maps to the {@code items} table in the database and can be
 * associated with multiple {@link Order} entities, representing orders that include this item.
 * </p>
 *
 * <h2>Relationships:</h2>
 * <ul>
 *     <li>{@link Order} – a list of orders containing this item (One item to Many orders)</li>
 * </ul>
 *
 * <h2>Fields:</h2>
 * <ul>
 *     <li>{@code id} – primary key of the item</li>
 *     <li>{@code name} – the name of the item</li>
 *     <li>{@code description} – a textual description of the item</li>
 *     <li>{@code price} – the price of the item (cannot be null)</li>
 *     <li>{@code orders} – the list of orders that include this item</li>
 * </ul>
 *
 * <p>
 * Lombok annotations (@Data, @NoArgsConstructor, @AllArgsConstructor, @Builder)
 * are used to generate getters, setters, constructors, and builder methods automatically.
 * </p>
 *
 * @see Order
 */
@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {

    /** Primary key of the item. Auto-generated. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The name of the item. Stored as TEXT in the database. */
    @Column(columnDefinition = "TEXT")
    private String name;

    /** The description of the item. Stored as TEXT in the database. */
    @Column(columnDefinition = "TEXT")
    private String description;

    /** The price of the item. Cannot be null. */
    @Column(nullable = false)
    private Double price;

    /** The list of orders that include this item. */
    @OneToMany(mappedBy = "item")
    private List<Order> orders = new ArrayList<>();
}
