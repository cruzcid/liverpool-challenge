package com.liverpool.liverpooldev.infrastructure.adapter.out.persistance.document;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * MongoDB document representing a cached item from the external /items API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "items")
public class ItemDocument {

    @Id
    private String id;

    @Indexed(unique = true)
    private String itemId;

    private String skuId;

    private int quantity;

    @TextIndexed
    private String displayName;

    private String deliveryStatus;

    private LocalDateTime syncedAt;
}
