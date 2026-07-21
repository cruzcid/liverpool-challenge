package com.liverpool.liverpool_dev.shared.mapper;

import java.time.LocalDateTime;

import com.liverpool.liverpool_dev.domain.model.Item;
import com.liverpool.liverpool_dev.infrastructure.adapter.out.persistance.document.ItemDocument;

public class ItemMapper {
    public static ItemDocument toDocument(final Item item) {
        if (item == null) {
            return null;
        }
        return ItemDocument.builder()
                .itemId(item.getItemId())
                .skuId(item.getSkuId())
                .quantity(item.getQuantity())
                .displayName(item.getDisplayName())
                .deliveryStatus(item.getDeliveryStatus())
                .syncedAt(LocalDateTime.now())
                .build();
    }
}
