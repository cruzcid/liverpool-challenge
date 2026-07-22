package com.liverpool.liverpooldev.domain.port.in;

import java.util.List;

import com.liverpool.liverpooldev.domain.model.Item;

/**
 * Use case for flexible, type-ahead item search by display name.
 * Search is accent-insensitive, case-insensitive and typo-tolerant.
 */
public interface SearchItemsUseCase {

    /**
     * Search items whose displayName matches the given query.
     *
     * @param query partial or full product name (accent/case/typo tolerant)
     * @return matching items, or all items if query is blank
     */
    List<Item> searchByDisplayName(String query);
}
