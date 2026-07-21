package com.liverpool.liverpool_dev.infrastructure.adapter.out.persistance.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.liverpool.liverpool_dev.infrastructure.adapter.out.persistance.document.CustomerDocument;

@Repository
public interface CustomerMongoRepository extends MongoRepository<CustomerDocument, String>{
  
}
