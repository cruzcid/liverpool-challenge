package com.liverpool.liverpooldev.infrastructure.adapter.in.rest.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Customer response")
public class CustomerResponse {

    @Schema(description = "Customer's unique user identifier")
    private String userId;

    @Schema(description = "Customer's first name")
    private String firstName;

    @Schema(description = "Customer's paternal last name")
    private String lastNamePaternal;

    @Schema(description = "Customer's maternal last name")
    private String lastNameMaternal;

    @Schema(description = "Customer's email address")
    private String email;

    @Schema(description = "Customer's delivery address")
    private DeliveryAddressResponse deliveryAddress;

    @Schema(description = "List of order reference numbers associated with this customer")
    private List<String> orders;
}