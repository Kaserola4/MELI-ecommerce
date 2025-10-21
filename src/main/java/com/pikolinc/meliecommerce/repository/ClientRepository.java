package com.pikolinc.meliecommerce.repository;

import com.pikolinc.meliecommerce.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for performing CRUD operations on {@link Client} entities.
 * <p>
 * This interface extends {@link JpaRepository}, which provides standard methods
 * for entity persistence such as:
 * </p>
 * <ul>
 *     <li>{@code save(Client entity)} – create or update a client</li>
 *     <li>{@code findById(Long id)} – retrieve a client by ID</li>
 *     <li>{@code findAll()} – list all clients</li>
 *     <li>{@code delete(Client entity)} – remove a client</li>
 * </ul>
 *
 * <p><strong>Custom queries:</strong></p>
 * <p>Additional query methods can be defined following Spring Data JPA conventions,
 * for example {@code findByEmail(String email)}.</p>
 *
 * @see Client
 * @see org.springframework.data.jpa.repository.JpaRepository
 */
public interface ClientRepository extends JpaRepository<Client, Long> { }
