package com.liverpool.liverpooldev.infrastructure.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Response DTO for a single order with its associated product items.
 */
@Data
@Builder
@Schema(description = "Order with associated product items")
public class OrderResponse {

    @Schema(description = "Order reference number", example = "3010091676")
    private String orderRef;

    @Schema(description = "Sales channel", example = "online", allowableValues = {"online", "physical"})
    private String canal;

    @Schema(description = "Estimated delivery date (orderStatus)", example = "2025-12-06")
    private String orderStatus;

    @Schema(description = "Store name", example = "Liverpool Galerías Toluca")
    private String storeName;

    @Schema(description = "Whether this is a marketplace order")
    private boolean marketPlace;

    @Schema(description = "Whether this is a gift registry order")
    private boolean giftRegistry;

    @Schema(description = "Product items included in this order")
    private List<ItemResponse> items;
}
