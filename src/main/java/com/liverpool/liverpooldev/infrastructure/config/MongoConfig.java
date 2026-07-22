package com.liverpool.liverpooldev.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/**
 * MongoDB configuration that enables:
 * - Auditing: auto-populates @CreatedDate and @LastModifiedDate fields
 */
@Configuration
@EnableMongoAuditing
public class MongoConfig {

}
