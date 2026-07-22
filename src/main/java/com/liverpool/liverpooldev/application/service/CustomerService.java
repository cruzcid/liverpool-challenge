package com.liverpool.liverpooldev.application.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.liverpool.liverpooldev.domain.exception.CustomerNotFoundException;
import com.liverpool.liverpooldev.domain.model.Customer;
import com.liverpool.liverpooldev.domain.model.DeliveryAddress;
import com.liverpool.liverpooldev.domain.model.Item;
import com.liverpool.liverpooldev.domain.model.Order;
import com.liverpool.liverpooldev.domain.model.OrderWithItems;
import com.liverpool.liverpooldev.domain.port.in.CreateCustomerUseCase;
import com.liverpool.liverpooldev.domain.port.in.DeleteCustomerUseCase;
import com.liverpool.liverpooldev.domain.port.in.FindCustomerUseCase;
import com.liverpool.liverpooldev.domain.port.in.UpdateCustomerUseCase;
import com.liverpool.liverpooldev.domain.port.out.CustomerRepositoryPort;
import com.liverpool.liverpooldev.domain.port.out.ItemCachePort;
import com.liverpool.liverpooldev.domain.port.out.OrderCachePort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService implements CreateCustomerUseCase, FindCustomerUseCase,
        UpdateCustomerUseCase, DeleteCustomerUseCase  {
    private final CustomerRepositoryPort customerRepositoryPort;
    private final OrderCachePort orderCachePort;
    private final ItemCachePort itemCachePort;

    @Override
    public Customer create(Customer customer) {
        if (customer.getUserId() == null || customer.getUserId().isBlank()) {
            customer.setUserId(UUID.randomUUID().toString());
        }
        log.info("Creating customer with userId: {}", customer.getUserId());
        return customerRepositoryPort.save(customer);
    }

    @Override
    public Customer findByUserId(final String userId) {
        return customerRepositoryPort.findByUserId(userId)
                .orElseThrow(() -> new CustomerNotFoundException(userId));
    }

    @Override
    public List<OrderWithItems> findOrdersByUserId(final String userId) {
        customerRepositoryPort.findByUserId(userId)
                .orElseThrow(() -> new CustomerNotFoundException(userId));
        List<Order> orders = orderCachePort.findByUserId(userId);
        return enrichOrdersWithItems(orders);
    }

    @Override
    public Customer update(final String userId, final Customer updatedCustomer) {
        Customer existing = customerRepositoryPort.findByUserId(userId)
                .orElseThrow(() -> new CustomerNotFoundException(userId));
        existing.setFirstName(updatedCustomer.getFirstName());
        existing.setLastNamePaternal(updatedCustomer.getLastNamePaternal());
        existing.setLastNameMaternal(updatedCustomer.getLastNameMaternal());
        existing.setEmail(updatedCustomer.getEmail());
        log.info("Updating customer with userId: {}", userId);
        return customerRepositoryPort.save(existing);
    }

    @Override
    public Customer updateDeliveryAddress(final String userId, final DeliveryAddress deliveryAddress) {
        Customer existing = customerRepositoryPort.findByUserId(userId)
                .orElseThrow(() -> new CustomerNotFoundException(userId));
        existing.setDeliveryAddress(deliveryAddress);
        log.info("Updating delivery address for userId: {}", userId);
        return customerRepositoryPort.save(existing);
    }

    @Override
    public Customer syncOrders(final String userId) {
        Customer customer = customerRepositoryPort.findByUserId(userId)
                .orElseThrow(() -> new CustomerNotFoundException(userId));
        List<Order> orders = orderCachePort.findByUserId(userId);
        List<String> orderRefs = orders.stream()
                .map(Order::getOrderRef)
                .collect(Collectors.toList());
        customer.setOrders(orderRefs);
        log.info("Synced {} orders for userId: {}", orderRefs.size(), userId);
        return customerRepositoryPort.save(customer);
    }

    @Override
    public void delete(final String userId) {
        if (!customerRepositoryPort.existsByUserId(userId)) {
            throw new CustomerNotFoundException(userId);
        }
        log.info("Deleting customer with userId: {}", userId);
        customerRepositoryPort.deleteByUserId(userId);
    }


    private List<OrderWithItems> enrichOrdersWithItems(final List<Order> orders) {
        return orders.stream()
                .map(order -> {
                    List<Item> items = itemCachePort.findByItemIdIn(order.getItemIds());
                    return OrderWithItems.builder()
                            .orderRef(order.getOrderRef())
                            .canal(order.getCanal())
                            .orderStatus(order.getOrderStatus())
                            .storeName(order.getStoreName())
                            .marketPlace(order.isMarketPlace())
                            .giftRegistry(order.isGiftRegistry())
                            .items(items)
                            .build();
                })
                .collect(Collectors.toList());
    }
}
