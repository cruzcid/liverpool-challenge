package com.liverpool.liverpool_dev.domain.port.in;

import java.util.List;

import com.liverpool.liverpool_dev.domain.model.Customer;
import com.liverpool.liverpool_dev.domain.model.OrderWithItems;

public interface FindCustomerUseCase {
    Customer findByUserId(String userId);

    List<OrderWithItems> findOrdersByUserId(String userId);
}
