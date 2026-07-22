package com.liverpool.liverpooldev.domain.model;

import lombok.Builder;
import lombok.Data;

/**
 * Represent product item (producto) from the Liverpool catalogue.
 * Items are fetched from the external items API and cached locally.
 */
@Data
@Builder
public class Item {
    private String itemId;

    private String skuId;

    private int quantity;

    private String displayName;

    private String deliveryStatus;
}


