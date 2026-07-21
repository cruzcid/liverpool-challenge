package com.liverpool.liverpool_dev.domain.port.out;

import java.util.List;

import com.liverpool.liverpool_dev.domain.model.Order;

/**
 * Output port for the local order cache (synced from external /pedidos API).
 * Implemented by the MongoDB infrastructure adapter.
 */
public interface OrderCachePort {
    void saveAll(List<Order> orders);

    List<Order> findAll();

    List<Order> findByUserId(String userId);

    List<Order> searchByFields(String query, String orderRef, String orderStatus, String storeName);
}
