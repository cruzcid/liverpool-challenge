package com.liverpool.liverpooldev.domain.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Customer {
   
    private String userId;

    private String firstName;

    private String lastNamePaternal;

    private String lastNameMaternal;

    private String email;

    private DeliveryAddress deliveryAddress;

    @Builder.Default
    private List<String> orders = new ArrayList<>();
}
