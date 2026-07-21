package com.liverpool.liverpool_dev.infrastructure.adapter.out.persistance.document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeliveryAddressDocument {
    private String street;

    private String city;

    private String state;

    private String zipCode;

    private String country;
}
