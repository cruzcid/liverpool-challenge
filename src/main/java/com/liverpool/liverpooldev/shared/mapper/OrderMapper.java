package com.liverpool.liverpooldev.shared.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.liverpool.liverpooldev.domain.model.Order;
import com.liverpool.liverpooldev.domain.model.OrderWithItems;
import com.liverpool.liverpooldev.infrastructure.adapter.in.rest.dto.ItemResponse;
import com.liverpool.liverpooldev.infrastructure.adapter.in.rest.dto.OrderResponse;
import com.liverpool.liverpooldev.infrastructure.adapter.out.persistance.document.OrderDocument;

public final class OrderMapper {
    public static Order toDomain(final OrderDocument document) {
        if (document == null) {
            return null;
        }
        return Order.builder()
                .orderRef(document.getOrderRef())
                .userId(document.getUserId())
                .canal(document.getCanal())
                .orderStatus(document.getOrderStatus())
                .marketPlace(document.isMarketPlace())
                .giftRegistry(document.isGiftRegistry())
                .itemIds(document.getItems())
                .storeName(document.getStoreName())
                .build();
    }

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
                .items(order.getItemIds())
                .storeName(order.getStoreName())
                .syncedAt(LocalDateTime.now())
                .build();
    }

    public static OrderResponse toResponse(final OrderWithItems order) {
        if (order == null) {
            return null;
        }
        List<ItemResponse> itemResponses = order.getItems() == null
                ? List.of()
                : order.getItems().stream()
                        .map(ItemMapper::toResponse)
                        .collect(Collectors.toList());
        return OrderResponse.builder()
                .orderRef(order.getOrderRef())
                .canal(order.getCanal())
                .orderStatus(order.getOrderStatus())
                .storeName(order.getStoreName())
                .marketPlace(order.isMarketPlace())
                .giftRegistry(order.isGiftRegistry())
                .items(itemResponses)
                .build();
    }
}
