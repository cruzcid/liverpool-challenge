package com.liverpool.liverpooldev.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.liverpool.liverpooldev.domain.model.Item;
import com.liverpool.liverpooldev.domain.port.in.SearchItemsUseCase;
import com.liverpool.liverpooldev.domain.port.out.ItemCachePort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemSearchService implements SearchItemsUseCase {

    private final ItemCachePort itemCachePort;

    @Override
    public List<Item> searchByDisplayName(final String query) {
        log.info("Item search - displayName query: '{}'", query);
        return itemCachePort.searchByDisplayName(query);
    }
}
