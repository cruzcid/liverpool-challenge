package com.liverpool.liverpool_dev.domain.port.in;

import com.liverpool.liverpool_dev.domain.model.Customer;
import com.liverpool.liverpool_dev.domain.model.DeliveryAddress;

public interface UpdateCustomerUseCase {
    Customer update(String userId, Customer updatedCustomer);

    Customer updateDeliveryAddress(String userId, DeliveryAddress deliveryAddress);

    Customer syncOrders(String userId);
}
