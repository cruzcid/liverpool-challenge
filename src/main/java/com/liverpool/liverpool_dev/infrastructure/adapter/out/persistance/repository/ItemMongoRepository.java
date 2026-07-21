package com.liverpool.liverpool_dev.infrastructure.adapter.out.persistance.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.liverpool.liverpool_dev.infrastructure.adapter.out.persistance.document.ItemDocument;

@Repository
public interface ItemMongoRepository extends MongoRepository<ItemDocument, String>{
    Optional<ItemDocument> findByItemId(String itemId);

    List<ItemDocument> findByItemIdIn(List<String> itemIds);
}
