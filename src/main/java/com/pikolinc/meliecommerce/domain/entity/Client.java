package com.pikolinc.meliecommerce.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a client in the system.
 * <p>
 * This entity maps to the {@code clients} table in the database and can have
 * multiple {@link Order} entities associated with it, representing the orders placed by the client.
 * </p>
 *
 * <h2>Relationships:</h2>
 * <ul>
 *     <li>{@link Order} – a list of orders placed by this client (One client to Many orders)</li>
 * </ul>
 *
 * <h2>Fields:</h2>
 * <ul>
 *     <li>{@code id} – primary key of the client</li>
 *     <li>{@code name} – the full name of the client</li>
 *     <li>{@code address} – the address of the client</li>
 *     <li>{@code age} – the age of the client</li>
 *     <li>{@code orders} – the list of orders associated with this client</li>
 * </ul>
 *
 * <p>
 * Lombok annotations (@Data, @NoArgsConstructor, @AllArgsConstructor, @Builder)
 * are used to automatically generate getters, setters, constructors, and builder methods.
 * </p>
 *
 * @see Order
 */
@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    /** Primary key of the client. Auto-generated. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The full name of the client. Stored as TEXT in the database. */
    @Column(columnDefinition = "TEXT")
    private String name;

    /** The address of the client. Stored as TEXT in the database. */
    @Column(columnDefinition = "TEXT")
    private String address;

    /** The age of the client. */
    private Integer age;

    /** The list of orders associated with this client. */
    @OneToMany(mappedBy = "client")
    private List<Order> orders = new ArrayList<>();
}
