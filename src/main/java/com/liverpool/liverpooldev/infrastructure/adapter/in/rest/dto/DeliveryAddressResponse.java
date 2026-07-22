package com.liverpool.liverpooldev.infrastructure.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * Response DTO for delivery address data.
 */
@Data
@Builder
@Schema(description = "Delivery address")
public class DeliveryAddressResponse {

    @Schema(description = "Street name and number")
    private String street;

    @Schema(description = "City")
    private String city;

    @Schema(description = "State")
    private String state;

    @Schema(description = "Postal / zip code")
    private String zipCode;

    @Schema(description = "Country")
    private String country;
}
