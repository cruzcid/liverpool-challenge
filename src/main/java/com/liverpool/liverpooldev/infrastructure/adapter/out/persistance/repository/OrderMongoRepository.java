package com.liverpool.liverpooldev.infrastructure.adapter.out.persistance.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.liverpool.liverpooldev.infrastructure.adapter.out.persistance.document.OrderDocument;

@Repository
public interface OrderMongoRepository extends MongoRepository<OrderDocument, String>{
    List<OrderDocument> findByUserId(String userId);

    Optional<OrderDocument> findByOrderRef(String orderRef);
}
