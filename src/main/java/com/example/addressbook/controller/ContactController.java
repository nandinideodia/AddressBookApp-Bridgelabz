package com.example.addressbook.controller;

import com.example.addressbook.dto.ContactDto;
import com.example.addressbook.dto.ResponseDto;
import com.example.addressbook.model.Contact;
import com.example.addressbook.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @PostMapping
    public ResponseEntity<ResponseDto> createContact(@RequestBody ContactDto contactDto) {
        Contact contact = new Contact();
        contact.setName(contactDto.getName());
        contact.setPhone(contactDto.getPhone());
        contact.setEmail(contactDto.getEmail());
        
        Contact savedContact = contactRepository.save(contact);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto("success", "Contact created successfully", savedContact));
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getAllContacts() {
        List<Contact> contacts = contactRepository.findAll();
        return ResponseEntity.ok()
                .body(new ResponseDto("success", "Contacts retrieved successfully", contacts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getContactById(@PathVariable Long id) {
        Contact contact = contactRepository.findById(id).orElse(null);
        if (contact != null) {
            return ResponseEntity.ok()
                    .body(new ResponseDto("success", "Contact retrieved successfully", contact));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDto("error", "Contact not found", null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> updateContact(
            @PathVariable Long id, @RequestBody ContactDto contactDto) {
        Contact existingContact = contactRepository.findById(id).orElse(null);
        if (existingContact != null) {
            existingContact.setName(contactDto.getName());
            existingContact.setPhone(contactDto.getPhone());
            existingContact.setEmail(contactDto.getEmail());
            Contact updatedContact = contactRepository.save(existingContact);
            return ResponseEntity.ok()
                    .body(new ResponseDto("success", "Contact updated successfully", updatedContact));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDto("error", "Contact not found", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteContact(@PathVariable Long id) {
        if (contactRepository.existsById(id)) {
            contactRepository.deleteById(id);
            return ResponseEntity.ok()
                    .body(new ResponseDto("success", "Contact deleted successfully", null));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDto("error", "Contact not found", null));
    }
}