package com.pikolinc.meliecommerce.repository;

import com.pikolinc.meliecommerce.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
