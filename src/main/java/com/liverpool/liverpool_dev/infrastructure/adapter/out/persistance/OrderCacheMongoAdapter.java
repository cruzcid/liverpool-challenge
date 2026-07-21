package com.liverpool.liverpool_dev.infrastructure.adapter.out.persistance;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Collation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.liverpool.liverpool_dev.domain.model.Order;
import com.liverpool.liverpool_dev.domain.port.out.OrderCachePort;
import com.liverpool.liverpool_dev.infrastructure.adapter.out.persistance.document.OrderDocument;
import com.liverpool.liverpool_dev.infrastructure.adapter.out.persistance.repository.OrderMongoRepository;
import com.liverpool.liverpool_dev.shared.mapper.OrderMapper;
import com.liverpool.liverpool_dev.shared.util.TextNormalizer;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderCacheMongoAdapter implements OrderCachePort{
    private final OrderMongoRepository repository;
    private final MongoTemplate mongoTemplate;

    @Override
    public void saveAll(List<Order> orders) {
        List<OrderDocument> orderDocuments = orders
            .stream()
            .map(o -> {
                OrderDocument od = OrderMapper.toDocument(o);
                od.setSyncedAt(LocalDateTime.now());
                return od; })
            .collect(Collectors.toList());

        repository.saveAll(orderDocuments);
    }
    
    @Override
    public List<Order> findAll() {
        return repository.findAll().stream()
                .map(OrderMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findByUserId(final String userId) {
        return repository.findByUserId(userId).stream()
                .map(OrderMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> searchByFields(final String query, final String orderRef,
                                       final String orderStatus, final String storeName) {
        List<Criteria> conditions = new ArrayList<>();

        if (orderRef != null && !orderRef.isBlank()) {
            conditions.add(Criteria.where("orderRef").regex(TextNormalizer.normalize(orderRef), "i"));
        }
        if (orderStatus != null && !orderStatus.isBlank()) {
            conditions.add(Criteria.where("orderStatus").regex(orderStatus, "i"));
        }
        if (storeName != null && !storeName.isBlank()) {
            conditions.add(Criteria.where("storeName").regex(TextNormalizer.normalize(storeName), "i"));
        }
        if (query != null && !query.isBlank()) {
            String norm = TextNormalizer.normalize(query);
            conditions.add(new Criteria().orOperator(
                    Criteria.where("orderRef").regex(norm, "i"),
                    Criteria.where("orderStatus").regex(norm, "i"),
                    Criteria.where("storeName").regex(norm, "i")
            ));
        }

        if (conditions.isEmpty()) {
            return findAll();
        }

        Criteria combined = new Criteria().andOperator(conditions.toArray(new Criteria[0]));
        Query mongoQuery = new Query(combined);
        mongoQuery.collation(Collation.of("es").strength(Collation.ComparisonLevel.secondary()));

        return mongoTemplate.find(mongoQuery, OrderDocument.class).stream()
                .map(OrderMapper::toDomain)
                .collect(Collectors.toList());
    }
}
