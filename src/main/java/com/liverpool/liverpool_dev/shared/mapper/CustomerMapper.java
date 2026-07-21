package com.liverpool.liverpool_dev.shared.mapper;

import com.liverpool.liverpool_dev.domain.model.Customer;
import com.liverpool.liverpool_dev.domain.model.DeliveryAddress;
import com.liverpool.liverpool_dev.infrastructure.adapter.out.persistance.document.CustomerDocument;
import com.liverpool.liverpool_dev.infrastructure.adapter.out.persistance.document.DeliveryAddressDocument;

public final class CustomerMapper {

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
}
