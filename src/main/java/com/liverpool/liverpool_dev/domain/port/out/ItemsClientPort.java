package com.liverpool.liverpool_dev.domain.port.out;

import java.util.List;

import com.liverpool.liverpool_dev.domain.model.Item;
/**
 * items (products) from external Liverpool /items 
 * Implemented by the HTTP client adapters
 */
public interface ItemsClientPort {
     /**
     * @return list of all items;
     */
    List<Item> fetchAll();
}
