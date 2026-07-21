package com.liverpool.liverpool_dev.infrastructure.adapter.out.persistance;

import org.springframework.stereotype.Component;

import com.liverpool.liverpool_dev.domain.model.Customer;
import com.liverpool.liverpool_dev.domain.port.out.CustomerRepositoryPort;
import com.liverpool.liverpool_dev.infrastructure.adapter.out.persistance.repository.CustomerMongoRepository;
import com.liverpool.liverpool_dev.shared.mapper.CustomerMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerMongoAdapter implements CustomerRepositoryPort{
    private final CustomerMongoRepository repository;

    @Override
    public Customer save(final Customer customer) {
        return CustomerMapper.toDomain(repository.save(CustomerMapper.toDocument(customer)));
    }
}
