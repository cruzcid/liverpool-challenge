package com.liverpool.liverpooldev.infrastructure.adapter.in.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.liverpool.liverpooldev.domain.model.OrderWithItems;
import com.liverpool.liverpooldev.domain.port.in.SearchOrdersUseCase;
import com.liverpool.liverpooldev.domain.port.in.SyncDataUseCase;
import com.liverpool.liverpooldev.infrastructure.adapter.in.rest.dto.OrderResponse;
import com.liverpool.liverpooldev.infrastructure.adapter.in.rest.dto.SearchOrdersResponse;
import com.liverpool.liverpooldev.shared.mapper.OrderMapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST input adapter for order search and data synchronization operations.
 *
 * <ul>
 *   <li>GET  /api/v1/orders/search - flexible order search with type-ahead</li>
 *   <li>POST /api/v1/orders/sync   - manually trigger external API data sync</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Order search and data synchronization API")
public class OrderSearchController {

    private final SearchOrdersUseCase searchOrdersUseCase;
    private final SyncDataUseCase syncDataUseCase;

    @GetMapping("/search")
    @Operation(summary = "Search orders with flexible type-ahead filtering",
            description = "Supports accent-insensitive, case-insensitive, and "
                    + "typo-tolerant search across orders and their product items.")
    public ResponseEntity<SearchOrdersResponse> search(
            @Parameter(description = "General text across all order fields and item names")
            @RequestParam(required = false) final String q,
            @Parameter(description = "Filter by order reference number (type-ahead)")
            @RequestParam(required = false) final String orderRef,
            @Parameter(description = "Filter by estimated delivery date")
            @RequestParam(required = false) final String orderStatus,
            @Parameter(description = "Filter by store name (accent-insensitive)")
            @RequestParam(required = false) final String storeName,
            @Parameter(description = "Filter by product display name (accent-insensitive)")
            @RequestParam(required = false) final String displayName) {

        List<OrderWithItems> results = searchOrdersUseCase.search(q, orderRef, orderStatus, storeName, displayName);
        List<OrderResponse> orderResponses = results.stream()
                .map(OrderMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(SearchOrdersResponse.builder()
                .total(orderResponses.size())
                .orders(orderResponses)
                .build());
    }

    @PostMapping("/sync")
    @Operation(summary = "Manually trigger data sync from external Liverpool APIs")
    public ResponseEntity<Void> sync() {
        syncDataUseCase.syncAll();
        return ResponseEntity.accepted().build();
    }
}
