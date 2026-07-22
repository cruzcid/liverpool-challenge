package com.liverpool.liverpooldev.application.service;

import com.liverpool.liverpooldev.domain.model.Item;
import com.liverpool.liverpooldev.domain.model.Order;
import com.liverpool.liverpooldev.domain.model.OrderWithItems;
import com.liverpool.liverpooldev.domain.port.out.ItemCachePort;
import com.liverpool.liverpooldev.domain.port.out.OrderCachePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

/**
 * Unit tests for OrderSearchService covering search scenarios.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("OrderSearchService Unit Tests")
class OrderSearchServiceTest {

    @Mock
    private OrderCachePort orderCachePort;

    @Mock
    private ItemCachePort itemCachePort;

    @InjectMocks
    private OrderSearchService orderSearchService;

    @Test
    @DisplayName("search() with no filters should return all orders")
    void search_noFilters_shouldReturnAllOrders() {
        List<Order> allOrders = List.of(
                Order.builder().orderRef("O-001").itemIds(List.of()).build(),
                Order.builder().orderRef("O-002").itemIds(List.of()).build()
        );
        when(orderCachePort.findAll()).thenReturn(allOrders);
        when(itemCachePort.findByItemIdIn(anyList())).thenReturn(List.of());

        List<OrderWithItems> result = orderSearchService.search(null, null, null, null, null);

        assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("search() by displayName should return orders containing matching items")
    void search_byDisplayName_shouldReturnOrdersWithMatchingItems() {
        Item item = Item.builder().itemId("O-001-sku1").displayName("Pantalón Levi's").build();
        Order order = Order.builder()
                .orderRef("O-001")
                .itemIds(List.of("O-001-sku1"))
                .build();

        when(itemCachePort.searchByDisplayName("pantalon")).thenReturn(List.of(item));
        when(orderCachePort.findAll()).thenReturn(List.of(order));
        when(itemCachePort.findByItemIdIn(anyList())).thenReturn(List.of(item));

        List<OrderWithItems> result = orderSearchService.search(null, null, null, null, "pantalon");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getOrderRef()).isEqualTo("O-001");
        assertThat(result.get(0).getItems()).hasSize(1);
        assertThat(result.get(0).getItems().get(0).getDisplayName()).isEqualTo("Pantalón Levi's");
    }

    @Test
    @DisplayName("search() by storeName should return matching orders")
    void search_byStoreName_shouldReturnMatchingOrders() {
        Order order = Order.builder()
                .orderRef("O-002")
                .storeName("Liverpool Galerías Toluca")
                .itemIds(List.of())
                .build();

        when(orderCachePort.searchByFields(null, null, null, "Toluca"))
                .thenReturn(List.of(order));
        when(itemCachePort.findByItemIdIn(anyList())).thenReturn(List.of());

        List<OrderWithItems> result = orderSearchService.search(null, null, null, "Toluca", null);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStoreName()).contains("Toluca");
    }

    @Test
    @DisplayName("search() by displayName with no matching items should return empty list")
    void search_byDisplayName_noMatch_shouldReturnEmptyList() {
        when(itemCachePort.searchByDisplayName("xyz123")).thenReturn(List.of());
        when(orderCachePort.findAll()).thenReturn(List.of());

        List<OrderWithItems> result = orderSearchService.search(null, null, null, null, "xyz123");

        assertThat(result).isEmpty();
    }
}
