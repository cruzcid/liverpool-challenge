package com.liverpool.liverpooldev.infrastructure.adapter.in.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liverpool.liverpooldev.domain.model.Customer;
import com.liverpool.liverpooldev.domain.model.DeliveryAddress;
import com.liverpool.liverpooldev.domain.model.OrderWithItems;
import com.liverpool.liverpooldev.domain.port.in.CreateCustomerUseCase;
import com.liverpool.liverpooldev.domain.port.in.DeleteCustomerUseCase;
import com.liverpool.liverpooldev.domain.port.in.FindCustomerUseCase;
import com.liverpool.liverpooldev.domain.port.in.UpdateCustomerUseCase;
import com.liverpool.liverpooldev.infrastructure.adapter.in.rest.dto.CreateCustomerRequest;
import com.liverpool.liverpooldev.infrastructure.adapter.in.rest.dto.CustomerDetailResponse;
import com.liverpool.liverpooldev.infrastructure.adapter.in.rest.dto.CustomerResponse;
import com.liverpool.liverpooldev.infrastructure.adapter.in.rest.dto.DeliveryAddressRequest;
import com.liverpool.liverpooldev.infrastructure.adapter.in.rest.dto.UpdateCustomerRequest;
import com.liverpool.liverpooldev.shared.mapper.CustomerMapper;

import java.util.List;

/**
 * REST input adapter for customer (datos del cliente) CRUD operations.
 *
 * <p>
 * Endpoints:
 * <ul>
 * <li>POST /api/v1/customers - create customer</li>
 * <li>GET /api/v1/customers/{userId} - get customer with enriched orders</li>
 * <li>PUT /api/v1/customers/{userId} - update customer personal data</li>
 * <li>PUT /api/v1/customers/{userId}/delivery-address - update delivery
 * address</li>
 * <li>PUT /api/v1/customers/{userId}/sync-orders - sync orders from cache</li>
 * <li>DELETE /api/v1/customers/{userId} - delete customer</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Tag(name = "Customers", description = "Customer (datos del cliente) management API")
public class CustomerController {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final FindCustomerUseCase findCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;

    @PostMapping
    @Operation(summary = "Create a new customer", responses = {
            @ApiResponse(responseCode = "201", description = "Customer created") })
    public ResponseEntity<CustomerResponse> create(@Valid @RequestBody final CreateCustomerRequest request) {
        Customer customer = CustomerMapper.toDomain(request);
        Customer created = createCustomerUseCase.create(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(CustomerMapper.toResponse(created));
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get customer with enriched orders and items")
    public ResponseEntity<CustomerDetailResponse> findByUserId(
            @Parameter(description = "Customer's userId") @PathVariable final String userId) {
        Customer customer = findCustomerUseCase.findByUserId(userId);
        List<OrderWithItems> orders = findCustomerUseCase.findOrdersByUserId(userId);
        return ResponseEntity.ok(CustomerMapper.toDetailResponse(customer, orders));
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update customer personal data")
    public ResponseEntity<CustomerResponse> update(
            @PathVariable final String userId,
            @Valid @RequestBody final UpdateCustomerRequest request) {
        Customer customer = CustomerMapper.toDomain(request);
        Customer updated = updateCustomerUseCase.update(userId, customer);
        return ResponseEntity.ok(CustomerMapper.toResponse(updated));
    }

    @PutMapping("/{userId}/delivery-address")
    @Operation(summary = "Update customer delivery address (datos de entrega)")
    public ResponseEntity<CustomerResponse> updateDeliveryAddress(
            @PathVariable final String userId,
            @Valid @RequestBody final DeliveryAddressRequest request) {
        DeliveryAddress address = CustomerMapper.toDomain(request);
        Customer updated = updateCustomerUseCase.updateDeliveryAddress(userId, address);
        return ResponseEntity.ok(CustomerMapper.toResponse(updated));
    }

    @PutMapping("/{userId}/sync-orders")
    @Operation(summary = "Sync customer's orders from the external API cache")
    public ResponseEntity<CustomerResponse> syncOrders(@PathVariable final String userId) {
        Customer updated = updateCustomerUseCase.syncOrders(userId);
        return ResponseEntity.ok(CustomerMapper.toResponse(updated));
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete a customer")
    public ResponseEntity<Void> delete(@PathVariable final String userId) {
        deleteCustomerUseCase.delete(userId);
        return ResponseEntity.noContent().build();
    }
}
