package com.pikolinc.meliecommerce.repository;

import com.pikolinc.meliecommerce.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
