package com.liverpool.liverpool_dev.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeliveryAddress {
    private String street;

    private String city;

    private String state;

    private String zipCode;

    private String country;
}
