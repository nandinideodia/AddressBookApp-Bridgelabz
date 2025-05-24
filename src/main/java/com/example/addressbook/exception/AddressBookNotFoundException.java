package com.example.addressbook.exception;

public class AddressBookNotFoundException extends RuntimeException {
    public AddressBookNotFoundException(Long id) {
        super("Address book entry not found with id: " + id);
    }
}