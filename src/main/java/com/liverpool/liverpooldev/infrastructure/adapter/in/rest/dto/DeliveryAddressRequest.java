package com.liverpool.liverpooldev.infrastructure.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Request body for creating or updating a delivery address.
 */
@Data
@Schema(description = "Delivery address data (dirección de envío)")
public class DeliveryAddressRequest {

    @NotBlank(message = "Street is required")
    @Schema(description = "Street name and number", example = "Av. Insurgentes Sur 1234")
    private String street;

    @NotBlank(message = "City is required")
    @Schema(description = "City", example = "Ciudad de México")
    private String city;

    @NotBlank(message = "State is required")
    @Schema(description = "State", example = "CDMX")
    private String state;

    @NotBlank(message = "Zip code is required")
    @Schema(description = "Postal / zip code", example = "03100")
    private String zipCode;

    @Schema(description = "Country", example = "México")
    private String country;
}
