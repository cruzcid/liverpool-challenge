package com.liverpool.liverpool_dev.domain.port.out;

import java.util.Optional;

import com.liverpool.liverpool_dev.domain.model.Customer;

public interface CustomerRepositoryPort {
    Customer save(Customer save);

    Optional<Customer> findByUserId(String userId);

    boolean existsByUserId(String userId);

    boolean existsByEmail(String email);

    void deleteByUserId(String userId);
}
