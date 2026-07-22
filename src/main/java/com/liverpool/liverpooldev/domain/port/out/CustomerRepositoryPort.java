package com.liverpool.liverpooldev.domain.port.out;

import java.util.Optional;

import com.liverpool.liverpooldev.domain.model.Customer;

public interface CustomerRepositoryPort {
    Customer save(Customer save);

    Optional<Customer> findByUserId(String userId);

    boolean existsByUserId(String userId);

    boolean existsByEmail(String email);

    void deleteByUserId(String userId);
}
