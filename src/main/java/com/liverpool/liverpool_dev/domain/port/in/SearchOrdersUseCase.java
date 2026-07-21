package com.liverpool.liverpool_dev.domain.port.in;

import java.util.List;

import com.liverpool.liverpool_dev.domain.model.OrderWithItems;

public interface SearchOrdersUseCase {
    List<OrderWithItems> search(String query, String orderRef, String orderStatus,
            String storeName, String displayName);
}
