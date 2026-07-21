package com.liverpool.liverpool_dev.domain.port.in;

import com.liverpool.liverpool_dev.domain.model.Customer;

public interface CreateCustomerUseCase {
    Customer create(Customer customer);
}

