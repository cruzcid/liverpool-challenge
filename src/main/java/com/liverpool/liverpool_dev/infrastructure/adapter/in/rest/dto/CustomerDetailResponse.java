package com.liverpool.liverpool_dev.infrastructure.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Response DTO for a customer with fully enriched order and item details.
 * Returned by GET /api/v1/customers/{userId}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Customer with enriched order and product details")
public class CustomerDetailResponse {

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

    @Schema(description = "Orders associated with this customer, enriched with product items")
    private List<OrderResponse> orders;
}
