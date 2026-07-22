package com.liverpool.liverpooldev.domain.port.in;

import java.util.List;

import com.liverpool.liverpooldev.domain.model.Customer;
import com.liverpool.liverpooldev.domain.model.OrderWithItems;

public interface FindCustomerUseCase {
    Customer findByUserId(String userId);

    List<OrderWithItems> findOrdersByUserId(String userId);
}
