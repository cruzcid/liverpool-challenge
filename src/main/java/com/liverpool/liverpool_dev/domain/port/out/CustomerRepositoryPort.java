package com.liverpool.liverpool_dev.domain.port.out;

import com.liverpool.liverpool_dev.domain.model.Customer;

public interface CustomerRepositoryPort {
    Customer save(Customer save);
}
