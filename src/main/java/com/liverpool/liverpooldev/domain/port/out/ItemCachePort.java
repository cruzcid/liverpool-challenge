package com.liverpool.liverpooldev.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.liverpool.liverpooldev.domain.model.Item;

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
