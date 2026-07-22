package com.liverpool.liverpooldev.application.service;

import com.liverpool.liverpooldev.domain.model.Item;
import com.liverpool.liverpooldev.domain.model.Order;
import com.liverpool.liverpooldev.domain.port.out.ItemCachePort;
import com.liverpool.liverpooldev.domain.port.out.ItemsClientPort;
import com.liverpool.liverpooldev.domain.port.out.OrderCachePort;
import com.liverpool.liverpooldev.domain.port.out.PedidosClientPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for DataSyncService covering sync and error handling scenarios.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("DataSyncService Unit Tests")
class DataSyncServiceTest {

    @Mock
    private PedidosClientPort pedidosClientPort;

    @Mock
    private ItemsClientPort itemsClientPort;

    @Mock
    private OrderCachePort orderCachePort;

    @Mock
    private ItemCachePort itemCachePort;

    @InjectMocks
    private DataSyncService dataSyncService;

    @Test
    @DisplayName("syncAll() should fetch and save both orders and items")
    void syncAll_shouldFetchAndSaveBoth() {
        List<Order> orders = List.of(Order.builder().orderRef("O-001").build());
        List<Item> items = List.of(Item.builder().itemId("O-001-sku1").displayName("Test Item").build());

        when(pedidosClientPort.fetchAll()).thenReturn(orders);
        when(itemsClientPort.fetchAll()).thenReturn(items);

        dataSyncService.syncAll();

        verify(pedidosClientPort).fetchAll();
        verify(itemsClientPort).fetchAll();
        verify(orderCachePort).saveAll(orders);
        verify(itemCachePort).saveAll(items);
    }

    @Test
    @DisplayName("syncAll() should handle orders API failure gracefully without affecting items sync")
    void syncAll_ordersApiFails_shouldStillSyncItems() {
        when(pedidosClientPort.fetchAll()).thenThrow(new RuntimeException("Pedidos API unavailable"));
        when(itemsClientPort.fetchAll()).thenReturn(List.of());

        dataSyncService.syncAll();

        verify(orderCachePort, never()).saveAll(anyList());
        verify(itemCachePort).saveAll(anyList());
    }

    @Test
    @DisplayName("syncAll() should handle items API failure gracefully without affecting orders sync")
    void syncAll_itemsApiFails_shouldStillSyncOrders() {
        when(pedidosClientPort.fetchAll()).thenReturn(List.of());
        when(itemsClientPort.fetchAll()).thenThrow(new RuntimeException("Items API unavailable"));

        dataSyncService.syncAll();

        verify(orderCachePort).saveAll(anyList());
        verify(itemCachePort, never()).saveAll(anyList());
    }
}

