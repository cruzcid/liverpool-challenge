package com.liverpool.liverpooldev.infrastructure.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Request body for updating an existing customer's personal data.
 */
@Data
@Schema(description = "Request body for updating a customer")
public class UpdateCustomerRequest {

    @NotBlank(message = "First name is required")
    @Schema(description = "Customer's first name", example = "María")
    private String firstName;

    @NotBlank(message = "Paternal last name is required")
    @Schema(description = "Customer's paternal last name", example = "Pérez")
    private String lastNamePaternal;

    @Schema(description = "Customer's maternal last name", example = "Ramírez")
    private String lastNameMaternal;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Schema(description = "Customer's email address", example = "maria.perez@example.com")
    private String email;
}
