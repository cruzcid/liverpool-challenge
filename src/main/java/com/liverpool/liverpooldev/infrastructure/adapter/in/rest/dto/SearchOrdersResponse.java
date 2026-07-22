package com.liverpool.liverpooldev.infrastructure.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Response wrapper for order search results.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Paginated search results for orders")
public class SearchOrdersResponse {

    @Schema(description = "Total number of matching orders")
    private int total;

    @Schema(description = "List of matching orders with enriched item details")
    private List<OrderResponse> orders;
}
