package com.liverpool.liverpool_dev.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.liverpool.liverpool_dev.domain.model.Customer;
import com.liverpool.liverpool_dev.domain.port.in.CreateCustomerUseCase;
import com.liverpool.liverpool_dev.domain.port.out.CustomerRepositoryPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService implements CreateCustomerUseCase{
    final CustomerRepositoryPort customerRepositoryPort;
    
    @Override
    public Customer create(Customer customer) {
        if (customer.getUserId() == null || customer.getUserId().isBlank()) {
            customer.setUserId(UUID.randomUUID().toString());
        }
        log.info("Creating customer with userId: {}", customer.getUserId());
        return customerRepositoryPort.save(customer);
    }
  
}
