package com.customer.exception;

public class CustomerAlreadyExistsException extends RuntimeException {
    public CustomerAlreadyExistsException(String email) {
        super(String.format("Customer with email %s already exists", email));
    }
}
