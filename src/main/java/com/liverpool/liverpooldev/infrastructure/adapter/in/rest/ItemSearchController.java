package com.liverpool.liverpooldev.infrastructure.adapter.in.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.liverpool.liverpooldev.domain.port.in.SearchItemsUseCase;
import com.liverpool.liverpooldev.infrastructure.adapter.in.rest.dto.ItemResponse;
import com.liverpool.liverpooldev.shared.mapper.ItemMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * <ul>
 *   <li>GET /api/v1/items/search - type-ahead search by product display name</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
@Tag(name = "Items", description = "Product item search API")
public class ItemSearchController {

    private final SearchItemsUseCase searchItemsUseCase;

    @GetMapping("/search")
    @Operation(
            summary = "Search items by product name (type-ahead)",
            description = "Flexible, accent-insensitive, case-insensitive and typo-tolerant "
                    + "search on product display names. Returns all items when query is omitted.")
    public ResponseEntity<List<ItemResponse>> search(
            @Parameter(description = "Partial or full product name, e.g. 'celular', 'iphone', 'celula'")
            @RequestParam(required = false) final String q) {

        List<ItemResponse> results = searchItemsUseCase.searchByDisplayName(q)
                .stream()
                .map(ItemMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(results);
    }
}
