package com.liverpool.liverpooldev.infrastructure.adapter.out.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemApiDto {
    private String itemId;

    private String skuId;

    private int quantity;

    private String displayName;

    private String deliveryStatus;

    private String id;
}
