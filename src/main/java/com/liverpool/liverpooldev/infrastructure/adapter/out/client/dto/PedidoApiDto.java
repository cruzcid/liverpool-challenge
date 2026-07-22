package com.liverpool.liverpooldev.infrastructure.adapter.out.client.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PedidoApiDto {
    private String orderRef;

    private String userId;

    private String canal;

    private String orderStatus;

    private boolean marketPlace;

    private boolean giftRegistry;

    private List<String> items;

    private String storeName;

    private String id;
}
