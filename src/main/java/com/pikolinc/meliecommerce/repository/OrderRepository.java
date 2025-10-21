package com.pikolinc.meliecommerce.repository;

import com.pikolinc.meliecommerce.domain.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link Order} entities in the database.
 * <p>
 * Extends {@link JpaRepository}, providing standard CRUD and pagination capabilities for orders.
 * </p>
 *
 * <p><strong>Available operations:</strong></p>
 * <ul>
 *     <li>{@code save(Order entity)} – persist or update an order</li>
 *     <li>{@code findById(Long id)} – retrieve a specific order</li>
 *     <li>{@code findAll(Pageable pageable)} – list orders with pagination</li>
 *     <li>{@code delete(Order entity)} – delete an order from the database</li>
 * </ul>
 *
 * <p><strong>Custom query methods:</strong></p>
 * <ul>
 *     <li>{@link #findByClientId(Long, Pageable)} – fetches all orders that belong to a given client</li>
 * </ul>
 *
 * @see Order
 * @see org.springframework.data.jpa.repository.JpaRepository
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Finds all {@link Order} entities associated with a specific client, using pagination.
     *
     * @param clientId the ID of the client whose orders should be retrieved
     * @param pageable the pagination information (page number, size, sorting)
     * @return a page of orders belonging to the specified client
     */
    Page<Order> findByClientId(Long clientId, Pageable pageable);
}
