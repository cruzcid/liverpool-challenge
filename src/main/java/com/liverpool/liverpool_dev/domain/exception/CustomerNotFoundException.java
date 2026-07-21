package com.liverpool.liverpool_dev.domain.exception;

public class CustomerNotFoundException extends DomainException {

    public CustomerNotFoundException(final String userId) {
        super("Customer not found with userId: " + userId);
    }
}
