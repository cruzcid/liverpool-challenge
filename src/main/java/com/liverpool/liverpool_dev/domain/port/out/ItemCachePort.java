package com.liverpool.liverpool_dev.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.liverpool.liverpool_dev.domain.model.Item;

/**
 * Output port for the local item cache (synced from external /items API).
 * Implemented by the MongoDB infrastructure adapter.
 */
public interface ItemCachePort {
    void saveAll(List<Item> orders);

    List<Item> findAll();

    List<Item> findByItemIdIn(List<String> itemIds);

    List<Item> searchByDisplayName(String query);

    Optional<Item> findByItemId(String itemId);
}
