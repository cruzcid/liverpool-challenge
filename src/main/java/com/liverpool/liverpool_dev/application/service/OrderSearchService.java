package com.liverpool.liverpool_dev.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.liverpool.liverpool_dev.domain.model.Order;
import com.liverpool.liverpool_dev.domain.model.Item;
import com.liverpool.liverpool_dev.domain.model.OrderWithItems;
import com.liverpool.liverpool_dev.domain.port.in.SearchOrdersUseCase;
import com.liverpool.liverpool_dev.domain.port.out.ItemCachePort;
import com.liverpool.liverpool_dev.domain.port.out.OrderCachePort;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Application service implementing the flexible order search use case.
 *
 * <p>Search strategy:
 * <ol>
 *   <li>If {@code displayName} is provided: find matching items in cache,
 *       then find orders containing those items.</li>
 *   <li>If order-level fields (query/orderRef/orderStatus/storeName) are provided:
 *       run accent-insensitive MongoDB regex search on the orders collection.</li>
 *   <li>Merge results (deduplication) and enrich each order with its items.</li>
 * </ol>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderSearchService implements SearchOrdersUseCase {

    private final OrderCachePort orderCachePort;
    private final ItemCachePort itemCachePort;
    
    @Override
    public List<OrderWithItems> search(final String query, final String orderRef,
                                       final String orderStatus, final String storeName,
                                       final String displayName) {
        log.info("Order search - q:{} orderRef:{} orderStatus:{} storeName:{} displayName:{}",
                query, orderRef, orderStatus, storeName, displayName);

        boolean noFilters = isBlank(query) && isBlank(orderRef) && isBlank(orderStatus)
                && isBlank(storeName) && isBlank(displayName);

        if (noFilters) {
            return orderCachePort.findAll().stream()
                    .map(this::enrichWithItems)
                    .collect(Collectors.toList());
        }

        List<Order> result = new ArrayList<>();

        if (!isBlank(displayName)) {
            List<Item> matchingItems = itemCachePort.searchByDisplayName(displayName);
            Set<String> matchedItemIds = matchingItems.stream()
                    .map(Item::getItemId)
                    .collect(Collectors.toSet());
            orderCachePort.findAll().stream()
                    .filter(o -> o.getItemIds() != null
                            && o.getItemIds().stream().anyMatch(matchedItemIds::contains))
                    .forEach(result::add);
        }

        if (!isBlank(query) || !isBlank(orderRef) || !isBlank(orderStatus) || !isBlank(storeName)) {
            List<Order> byFields = orderCachePort.searchByFields(query, orderRef, orderStatus, storeName);
            byFields.stream()
                    .filter(o -> result.stream().noneMatch(r -> r.getOrderRef().equals(o.getOrderRef())))
                    .forEach(result::add);
        }

        return result.stream()
                .map(this::enrichWithItems)
                .collect(Collectors.toList());
    }

    /**
     * Loads item details for all itemIds referenced in the given order.
     *
     * @param order the order to enrich
     * @return enriched order with items populated
     */
    private OrderWithItems enrichWithItems(final Order order) {
        List<Item> items = itemCachePort.findByItemIdIn(
                order.getItemIds() != null ? order.getItemIds() : List.of());
        return OrderWithItems.builder()
                .orderRef(order.getOrderRef())
                .canal(order.getCanal())
                .orderStatus(order.getOrderStatus())
                .storeName(order.getStoreName())
                .marketPlace(order.isMarketPlace())
                .giftRegistry(order.isGiftRegistry())
                .items(items)
                .build();
    }

    /**
     * Returns true if the given string is null or blank.
     *
     * @param value the string to check
     * @return true if null or blank
     */
    private boolean isBlank(final String value) {
        return value == null || value.isBlank();
    }
}

