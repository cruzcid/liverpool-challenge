package com.liverpool.liverpool_dev.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.liverpool.liverpool_dev.domain.model.Item;
import com.liverpool.liverpool_dev.domain.model.Order;
import com.liverpool.liverpool_dev.domain.port.in.SyncDataUseCase;
import com.liverpool.liverpool_dev.domain.port.out.ItemCachePort;
import com.liverpool.liverpool_dev.domain.port.out.ItemsClientPort;
import com.liverpool.liverpool_dev.domain.port.out.OrderCachePort;
import com.liverpool.liverpool_dev.domain.port.out.PedidosClientPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DataSyncService implements SyncDataUseCase {

    private final PedidosClientPort pedidosClientPort;

    private final ItemsClientPort itemsClientPort;

    private final OrderCachePort orderCachePort;

    private final ItemCachePort itemCachePort;
    @Override
    public void syncAll() {
        log.info("Syncronizzing info from Liverpool APIs");
        syncItems();
        syncOrders();
        log.info("Finished syncronization info from Liverpool APIs");
    }

    private void syncItems() {
        try {
            List<Item> itemList = itemsClientPort.fetchAll();
            itemCachePort.saveAll(itemList);
            log.info("Synchronized {} items from external pedidos API", itemList.size());
            
        } catch (Exception e) {
            log.info("Failed to fetch external items {}", e.getMessage());
        }

    }

    private void syncOrders() {
        try {
            List<Order> orderList = pedidosClientPort.fetchAll();
            orderCachePort.saveAll(orderList);
            log.info("Synchronized {} orders from external pedidos API", orderList.size());
        } catch (Exception e) {
            log.info("Failed to fetch external order pedidos {}", e.getMessage());
        }
    }
}
