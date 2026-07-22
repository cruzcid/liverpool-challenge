package com.liverpool.liverpooldev.infrastructure.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Request body for creating a customer")
public class CreateCustomerRequest {

    @Schema(description = "Optional external userId (UUID). Auto-generated if not provided.")
    private String userId;

    @NotBlank(message = "First name is required")
    @Schema(description = "Customer's first name (nombre)", example = "Juan")
    private String firstName;

    @NotBlank(message = "Paternal last name is required")
    @Schema(description = "Customer's paternal last name (apellido paterno)", example = "García")
    private String lastNamePaternal;

    @Schema(description = "Customer's maternal last name (apellido materno)", example = "López")
    private String lastNameMaternal;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Schema(description = "Customer's email address", example = "juan.garcia@example.com")
    private String email;

    @Valid
    @Schema(description = "Customer's delivery address")
    private DeliveryAddressRequest deliveryAddress;
}
