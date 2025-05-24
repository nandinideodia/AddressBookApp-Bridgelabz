package com.example.addressbook.service;

import com.example.addressbook.dto.ContactDto;
import com.example.addressbook.model.Contact;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ContactService {
    private final List<Contact> contacts = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public Contact createContact(ContactDto contactDto) {
        Contact contact = new Contact(
        	idCounter.getAndIncrement(),
            contactDto.getName(),
            contactDto.getPhone(),
            contactDto.getEmail()
        );
        contacts.add(contact);
        return contact;
    }

    public List<Contact> getAllContacts() {
        return new ArrayList<>(contacts);
    }

    public Contact getContactById(Long id) {
        return contacts.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Contact updateContact(Long id, ContactDto contactDto) {
        Contact existingContact = getContactById(id);
        if (existingContact != null) {
            existingContact.setName(contactDto.getName());
            existingContact.setPhone(contactDto.getPhone());
            existingContact.setEmail(contactDto.getEmail());
            return existingContact;
        }
        return null;
    }

    public boolean deleteContact(Long id) {
        return contacts.removeIf(c -> c.getId().equals(id));
    }
}