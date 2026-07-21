package com.liverpool.liverpool_dev.domain.port.out;

import java.util.List;

import com.liverpool.liverpool_dev.domain.model.Item;

/**
 * Output port for the local item cache (synced from external /items API).
 * Implemented by the MongoDB infrastructure adapter.
 */
public interface ItemCachePort {
    void saveAll(List<Item> orders);
}
