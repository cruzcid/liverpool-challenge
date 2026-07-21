package com.liverpool.liverpool_dev.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * MongoDB configuration that enables:
 * - Auditing: auto-populates @CreatedDate and @LastModifiedDate fields
 * - Repository scanning scoped to the persistence adapter package
 */
@Configuration
@EnableMongoAuditing
@EnableMongoRepositories(basePackages = "com.liverpool.liverpool_dev.infrastructure.adapter.out.persistance.repository")
public class MongoConfig {

}
