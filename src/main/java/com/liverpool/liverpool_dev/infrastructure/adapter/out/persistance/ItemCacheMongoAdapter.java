package com.liverpool.liverpool_dev.infrastructure.adapter.out.persistance;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.liverpool.liverpool_dev.domain.model.Item;
import com.liverpool.liverpool_dev.domain.port.out.ItemCachePort;
import com.liverpool.liverpool_dev.infrastructure.adapter.out.persistance.document.ItemDocument;
import com.liverpool.liverpool_dev.infrastructure.adapter.out.persistance.repository.ItemMongoRepository;
import com.liverpool.liverpool_dev.shared.mapper.ItemMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ItemCacheMongoAdapter implements ItemCachePort {

    private final ItemMongoRepository repository;

    @Override
    public void saveAll(final List<Item> items) {
        List<ItemDocument> documents = items.stream()
                .map(item -> {
                    ItemDocument doc = ItemMapper.toDocument(item);
                    doc.setSyncedAt(LocalDateTime.now());
                    return doc;
                })
                .collect(Collectors.toList());
        repository.deleteAll();
        repository.saveAll(documents);
    }

}
