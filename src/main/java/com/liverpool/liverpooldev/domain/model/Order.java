package com.liverpool.liverpooldev.domain.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Order {
    private String orderRef;

    private String userId;

    private String canal;

    private String orderStatus;

    private boolean marketPlace;

    private boolean giftRegistry;

    private List<String> itemIds;

    private String storeName;
}

