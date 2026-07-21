package com.liverpool.liverpool_dev.infrastructure.adapter.out.persistance.document;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "customers")
public class CustomerDocument {

    @Id
    private String id;

    @Indexed(unique = true)
    private String userId;

    private String firstName;

    private String lastNamePaternal;

    private String lastNameMaternal;

    @Indexed(unique = true)
    private String email;

    private DeliveryAddressDocument deliveryAddress;

    /** Cached list of orderRef values linked to this customer. */
    private List<String> orders;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
