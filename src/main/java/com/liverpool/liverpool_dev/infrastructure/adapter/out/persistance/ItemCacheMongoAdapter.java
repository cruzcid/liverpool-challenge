package com.liverpool.liverpool_dev.infrastructure.adapter.out.persistance;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Collation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.liverpool.liverpool_dev.domain.model.Item;
import com.liverpool.liverpool_dev.domain.port.out.ItemCachePort;
import com.liverpool.liverpool_dev.infrastructure.adapter.out.persistance.document.ItemDocument;
import com.liverpool.liverpool_dev.infrastructure.adapter.out.persistance.repository.ItemMongoRepository;
import com.liverpool.liverpool_dev.shared.mapper.ItemMapper;
import com.liverpool.liverpool_dev.shared.util.TextNormalizer;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ItemCacheMongoAdapter implements ItemCachePort {

    private final ItemMongoRepository repository;
    private final MongoTemplate mongoTemplate;

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

    @Override
    public List<Item> findAll() {
        return repository.findAll().stream()
                .map(ItemMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> findByItemIdIn(final List<String> itemIds) {
        if (itemIds == null || itemIds.isEmpty()) {
            return List.of();
        }
        return repository.findByItemIdIn(itemIds).stream()
                .map(ItemMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> searchByDisplayName(final String query) {
        if (query == null || query.isBlank()) {
            return findAll();
        }
        
        String normalized = TextNormalizer.normalize(query);
        Query mongoQuery = new Query(Criteria.where("displayName").regex(normalized, "i"));
        mongoQuery.collation(Collation.of("es").strength(Collation.ComparisonLevel.secondary()));
        return mongoTemplate.find(mongoQuery, ItemDocument.class).stream()
                .map(ItemMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Item> findByItemId(final String itemId) {
        return repository.findByItemId(itemId).map(ItemMapper::toDomain);
    }

}
