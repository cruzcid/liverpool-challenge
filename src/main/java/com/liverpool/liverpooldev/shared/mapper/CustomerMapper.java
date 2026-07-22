package com.liverpool.liverpooldev.shared.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.liverpool.liverpooldev.domain.model.Customer;
import com.liverpool.liverpooldev.domain.model.DeliveryAddress;
import com.liverpool.liverpooldev.domain.model.OrderWithItems;
import com.liverpool.liverpooldev.infrastructure.adapter.in.rest.dto.CreateCustomerRequest;
import com.liverpool.liverpooldev.infrastructure.adapter.in.rest.dto.CustomerDetailResponse;
import com.liverpool.liverpooldev.infrastructure.adapter.in.rest.dto.CustomerResponse;
import com.liverpool.liverpooldev.infrastructure.adapter.in.rest.dto.DeliveryAddressRequest;
import com.liverpool.liverpooldev.infrastructure.adapter.in.rest.dto.DeliveryAddressResponse;
import com.liverpool.liverpooldev.infrastructure.adapter.in.rest.dto.OrderResponse;
import com.liverpool.liverpooldev.infrastructure.adapter.in.rest.dto.UpdateCustomerRequest;
import com.liverpool.liverpooldev.infrastructure.adapter.out.persistance.document.CustomerDocument;
import com.liverpool.liverpooldev.infrastructure.adapter.out.persistance.document.DeliveryAddressDocument;

public final class CustomerMapper {
     private CustomerMapper() {
        throw new UnsupportedOperationException("Utility class");
    }
    
    /**
     * Maps a CreateCustomerRequest REST DTO to the Customer domain model.
     *
     * @param request the incoming HTTP request body
     * @return the corresponding domain object
     */
    public static Customer toDomain(final CreateCustomerRequest request) {
        return Customer.builder()
                .userId(request.getUserId())
                .firstName(request.getFirstName())
                .lastNamePaternal(request.getLastNamePaternal())
                .lastNameMaternal(request.getLastNameMaternal())
                .email(request.getEmail())
                .deliveryAddress(toDomain(request.getDeliveryAddress()))
                .build();
    }

    /**
     * Maps an UpdateCustomerRequest REST DTO to the Customer domain model.
     *
     * @param request the incoming HTTP request body
     * @return the corresponding domain object
     */
    public static Customer toDomain(final UpdateCustomerRequest request) {
        return Customer.builder()
                .firstName(request.getFirstName())
                .lastNamePaternal(request.getLastNamePaternal())
                .lastNameMaternal(request.getLastNameMaternal())
                .email(request.getEmail())
                .build();
    }

    /**
     * Maps a DeliveryAddressRequest to the DeliveryAddress domain value object.
     *
     * @param request the address request; returns null if request is null
     * @return the domain DeliveryAddress or null
     */
    public static DeliveryAddress toDomain(final DeliveryAddressRequest request) {
        if (request == null) {
            return null;
        }
        return DeliveryAddress.builder()
                .street(request.getStreet())
                .city(request.getCity())
                .state(request.getState())
                .zipCode(request.getZipCode())
                .country(request.getCountry())
                .build();
    }

    /**
     * Maps a CustomerDocument MongoDB document to the Customer domain model.
     *
     * @param document the persisted document; returns null if document is null
     * @return the domain object
     */
    public static Customer toDomain(final CustomerDocument document) {
        if (document == null) {
            return null;
        }
        return Customer.builder()
                .userId(document.getUserId())
                .firstName(document.getFirstName())
                .lastNamePaternal(document.getLastNamePaternal())
                .lastNameMaternal(document.getLastNameMaternal())
                .email(document.getEmail())
                .deliveryAddress(toDomain(document.getDeliveryAddress()))
                .orders(document.getOrders())
                .build();
    }

    /**
     * Maps a DeliveryAddressDocument to the DeliveryAddress domain value object.
     *
     * @param document the embedded document; returns null if null
     * @return the domain value object
     */
    public static DeliveryAddress toDomain(final DeliveryAddressDocument document) {
        if (document == null) {
            return null;
        }
        return DeliveryAddress.builder()
                .street(document.getStreet())
                .city(document.getCity())
                .state(document.getState())
                .zipCode(document.getZipCode())
                .country(document.getCountry())
                .build();
    }

    /**
     * Maps a Customer domain model to a CustomerDocument for MongoDB persistence.
     *
     * @param customer the domain object; returns null if null
     * @return the MongoDB document
     */
    public static CustomerDocument toDocument(final Customer customer) {
        if (customer == null) {
            return null;
        }
        return CustomerDocument.builder()
                .userId(customer.getUserId())
                .firstName(customer.getFirstName())
                .lastNamePaternal(customer.getLastNamePaternal())
                .lastNameMaternal(customer.getLastNameMaternal())
                .email(customer.getEmail())
                .deliveryAddress(toDocument(customer.getDeliveryAddress()))
                .orders(customer.getOrders())
                .build();
    }

    /**
     * Maps a DeliveryAddress domain value object to a DeliveryAddressDocument.
     *
     * @param address the domain value object; returns null if null
     * @return the embedded document
     */
    public static DeliveryAddressDocument toDocument(final DeliveryAddress address) {
        if (address == null) {
            return null;
        }
        return DeliveryAddressDocument.builder()
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(address.getZipCode())
                .country(address.getCountry())
                .build();
    }

    public static CustomerResponse toResponse(final Customer customer) {
        if (customer == null) {
            return null;
        }
        return CustomerResponse.builder()
                .userId(customer.getUserId())
                .firstName(customer.getFirstName())
                .lastNamePaternal(customer.getLastNamePaternal())
                .lastNameMaternal(customer.getLastNameMaternal())
                .email(customer.getEmail())
                .deliveryAddress(toResponse(customer.getDeliveryAddress()))
                .orders(customer.getOrders())
                .build();
    }


    public static DeliveryAddressResponse toResponse(final DeliveryAddress address) {
        if (address == null) {
            return null;
        }
        return DeliveryAddressResponse.builder()
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(address.getZipCode())
                .country(address.getCountry())
                .build();
    }

    public static CustomerDetailResponse toDetailResponse(final Customer customer,
                                                           final List<OrderWithItems> orders) {
        List<OrderResponse> orderResponses = orders.stream()
                .map(OrderMapper::toResponse)
                .collect(Collectors.toList());
        return CustomerDetailResponse.builder()
                .userId(customer.getUserId())
                .firstName(customer.getFirstName())
                .lastNamePaternal(customer.getLastNamePaternal())
                .lastNameMaternal(customer.getLastNameMaternal())
                .email(customer.getEmail())
                .deliveryAddress(toResponse(customer.getDeliveryAddress()))
                .orders(orderResponses)
                .build();
    }
}
