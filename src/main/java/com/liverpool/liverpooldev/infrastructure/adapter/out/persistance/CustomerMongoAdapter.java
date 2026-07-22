package com.liverpool.liverpooldev.infrastructure.adapter.out.persistance;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.liverpool.liverpooldev.domain.model.Customer;
import com.liverpool.liverpooldev.domain.port.out.CustomerRepositoryPort;
import com.liverpool.liverpooldev.infrastructure.adapter.out.persistance.repository.CustomerMongoRepository;
import com.liverpool.liverpooldev.shared.mapper.CustomerMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerMongoAdapter implements CustomerRepositoryPort{
    private final CustomerMongoRepository repository;

    @Override
    public Customer save(final Customer customer) {
        return CustomerMapper.toDomain(repository.save(CustomerMapper.toDocument(customer)));
    }

    @Override
    public Optional<Customer> findByUserId(final String userId) {
        return repository.findByUserId(userId).map(CustomerMapper::toDomain);
    }

    @Override
    public void deleteByUserId(final String userId) {
        repository.deleteByUserId(userId);
    }

    @Override
    public boolean existsByUserId(final String userId) {
        return repository.existsByUserId(userId);
    }

    @Override
    public boolean existsByEmail(final String email) {
        return repository.existsByEmail(email);
    }
}
