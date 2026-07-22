package com.liverpool.liverpooldev.domain.port.in;

import java.util.List;

import com.liverpool.liverpooldev.domain.model.OrderWithItems;

public interface SearchOrdersUseCase {
    List<OrderWithItems> search(String query, String orderRef, String orderStatus,
            String storeName, String displayName);
}
