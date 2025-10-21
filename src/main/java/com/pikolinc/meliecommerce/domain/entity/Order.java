package com.pikolinc.meliecommerce.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Represents an order placed by a client for a specific item.
 * <p>
 * This entity maps to the {@code orders} table in the database and includes
 * relationships to both {@link Client} and {@link Item} entities.
 * </p>
 *
 * <h2>Relationships:</h2>
 * <ul>
 *     <li>{@link Client} – the client who placed the order (Many orders to One client)</li>
 *     <li>{@link Item} – the item being ordered (Many orders to One item)</li>
 * </ul>
 *
 * <h2>Fields:</h2>
 * <ul>
 *     <li>{@code id} – primary key of the order</li>
 *     <li>{@code client} – the client associated with this order</li>
 *     <li>{@code item} – the item included in this order</li>
 *     <li>{@code purchaseDate} – the date the order was placed</li>
 *     <li>{@code deliveryDate} – the expected delivery date for the order</li>
 * </ul>
 *
 * <p>
 * Lombok annotations (@Data, @NoArgsConstructor, @AllArgsConstructor, @Builder)
 * are used to generate boilerplate code such as getters, setters, constructors, and builder methods.
 * </p>
 *
 * @see Client
 * @see Item
 */
@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    /** Primary key of the order. Auto-generated. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The client who placed this order. */
    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

    /** The item included in this order. */
    @ManyToOne
    @JoinColumn(name = "id_item")
    private Item item;

    /** The date when the order was placed. */
    private LocalDate purchaseDate;

    /** The expected or actual delivery date of the order. */
    private LocalDate deliveryDate;
}
