package com.liverpool.liverpool_dev.domain.port.out;

import java.util.List;

import com.liverpool.liverpool_dev.domain.model.Order;

public interface PedidosClientPort {
    /**
     * pedido (order) from external Liverpool /pedidos 
     * Implemented by the HTTP client adapters
     */
    List<Order> fetchAll();
} 
