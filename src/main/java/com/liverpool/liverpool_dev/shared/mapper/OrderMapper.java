package com.liverpool.liverpool_dev.shared.mapper;

import java.time.LocalDateTime;

import com.liverpool.liverpool_dev.domain.model.Order;
import com.liverpool.liverpool_dev.infrastructure.adapter.out.persistance.document.OrderDocument;

public final class OrderMapper {
    public static OrderDocument toDocument(final Order order) {
        if (order == null) {
            return null;
        }
        return OrderDocument.builder()
                .orderRef(order.getOrderRef())
                .userId(order.getUserId())
                .canal(order.getCanal())
                .orderStatus(order.getOrderStatus())
                .marketPlace(order.isMarketPlace())
                .giftRegistry(order.isGiftRegistry())
                .itemIds(order.getItemIds())
                .storeName(order.getStoreName())
                .syncedAt(LocalDateTime.now())
                .build();
    }
}
