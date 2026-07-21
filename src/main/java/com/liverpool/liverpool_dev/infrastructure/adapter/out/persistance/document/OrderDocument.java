package com.liverpool.liverpool_dev.infrastructure.adapter.out.persistance.document;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * MongoDB document representing a cached order (pedido) from the external /pedidos API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
public class OrderDocument {
    @Id
    private String id;

    @Indexed(unique = true)
    private String orderRef;

    private String userId;

    private String canal;

    private String orderStatus;

    private boolean marketPlace;

    private boolean giftRegistry;

    private List<String> itemIds;

    private String storeName;

    private LocalDateTime syncedAt;
}
