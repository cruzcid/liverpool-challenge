package com.liverpool.liverpooldev.domain.port.out;

import java.util.List;

import com.liverpool.liverpooldev.domain.model.Order;

public interface PedidosClientPort {
    /**
     * pedido (order) from external Liverpool /pedidos 
     * Implemented by the HTTP client adapters
     */
    List<Order> fetchAll();
} 
