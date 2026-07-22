package com.liverpool.liverpooldev.domain.port.in;

import com.liverpool.liverpooldev.domain.model.Customer;

public interface CreateCustomerUseCase {
    Customer create(Customer customer);
}

