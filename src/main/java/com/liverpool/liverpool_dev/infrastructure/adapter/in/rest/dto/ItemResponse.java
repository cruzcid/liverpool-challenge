package com.liverpool.liverpool_dev.infrastructure.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * Response DTO for a single product item (producto).
 */
@Data
@Builder
@Schema(description = "Product item")
public class ItemResponse {

    @Schema(description = "Composite item identifier (orderRef-skuId)", example = "3010091676-1132351437")
    private String itemId;

    @Schema(description = "Product SKU identifier", example = "1132351437")
    private String skuId;

    @Schema(description = "Quantity ordered", example = "3")
    private int quantity;

    @Schema(description = "Human-readable product name", example = "Pantalón Levi's")
    private String displayName;

    @Schema(description = "Delivery status", example = "Pedido entregado")
    private String deliveryStatus;
}
