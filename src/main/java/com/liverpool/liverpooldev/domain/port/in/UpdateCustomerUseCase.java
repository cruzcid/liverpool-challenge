package com.liverpool.liverpooldev.domain.port.in;

import com.liverpool.liverpooldev.domain.model.Customer;
import com.liverpool.liverpooldev.domain.model.DeliveryAddress;

public interface UpdateCustomerUseCase {
    Customer update(String userId, Customer updatedCustomer);

    Customer updateDeliveryAddress(String userId, DeliveryAddress deliveryAddress);

    Customer syncOrders(String userId);
}
