package com.liverpool.liverpooldev.domain.port.out;

import java.util.List;

import com.liverpool.liverpooldev.domain.model.Item;
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
