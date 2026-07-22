package com.liverpool.liverpooldev.shared.mapper;

import java.time.LocalDateTime;

import com.liverpool.liverpooldev.domain.model.Item;
import com.liverpool.liverpooldev.infrastructure.adapter.in.rest.dto.ItemResponse;
import com.liverpool.liverpooldev.infrastructure.adapter.out.persistance.document.ItemDocument;

public class ItemMapper {

    private ItemMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

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

    public static Item toDomain(final ItemDocument document) {
        if (document == null) {
            return null;
        }
        return Item.builder()
                .itemId(document.getItemId())
                .skuId(document.getSkuId())
                .quantity(document.getQuantity())
                .displayName(document.getDisplayName())
                .deliveryStatus(document.getDeliveryStatus())
                .build();
    }

    
    public static ItemResponse toResponse(final Item item) {
        if (item == null) {
            return null;
        }
        return ItemResponse.builder()
                .itemId(item.getItemId())
                .skuId(item.getSkuId())
                .quantity(item.getQuantity())
                .displayName(item.getDisplayName())
                .deliveryStatus(item.getDeliveryStatus())
                .build();
    }
    
}
