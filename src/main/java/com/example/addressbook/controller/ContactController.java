package com.example.addressbook.controller;

import com.example.addressbook.dto.ContactDto;
import com.example.addressbook.dto.ResponseDto;
import com.example.addressbook.model.Contact;
import com.example.addressbook.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createContact(@Valid @RequestBody ContactDto contactDto) {
        Contact contact = contactService.createContact(contactDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto("success", "Contact created successfully", contact));
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getAllContacts() {
        return ResponseEntity.ok()
                .body(new ResponseDto("success", "Contacts retrieved successfully", 
                    contactService.getAllContacts()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getContactById(@PathVariable Long id) {
        Contact contact = contactService.getContactById(id);
        if (contact != null) {
            return ResponseEntity.ok()
                    .body(new ResponseDto("success", "Contact retrieved successfully", contact));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDto("error", "Contact not found", null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> updateContact(
            @PathVariable Long id, @Valid @RequestBody ContactDto contactDto) {
        Contact updatedContact = contactService.updateContact(id, contactDto);
        if (updatedContact != null) {
            return ResponseEntity.ok()
                    .body(new ResponseDto("success", "Contact updated successfully", updatedContact));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDto("error", "Contact not found", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteContact(@PathVariable Long id) {
        if (contactService.deleteContact(id)) {
            return ResponseEntity.ok()
                    .body(new ResponseDto("success", "Contact deleted successfully", null));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDto("error", "Contact not found", null));
    }
}