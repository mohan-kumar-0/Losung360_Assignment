package com.mohan.ContactManagementSystem.controller;

import com.mohan.ContactManagementSystem.entity.Contact;
import com.mohan.ContactManagementSystem.exception.ContactException;
import com.mohan.ContactManagementSystem.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @Operation(summary = "Get all the contacts", description = "Returns a list of all the saved contacts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all contact records")
    })
    @GetMapping
    public List<Contact> getAllContacts() {
        List<Contact> contactList = contactService.getAllContacts();
        return contactList;
    }

    @Operation(summary = "Save a contact", description = "Saves a contact if it is not already saved.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully saved the contact"),
            @ApiResponse(responseCode = "400", description = "Contact already saved"),
            @ApiResponse(responseCode = "500", description = "Contact could not be saved due to server issues(possibly db related issues)")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Contact createContact(@RequestBody Contact contact) {
        Contact existingContact = contactService.getContactByPhoneNumber(contact.getPhoneNumber());
        if(existingContact!=null)
            throw new ContactException("Contact already exists");
        else if(!contactService.createContact(contact))
            throw new RuntimeException("Failed to save contact");
        return contact;
    }

    @Operation(summary = "Update an existing contact", description = "Updates a contact if it exists")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully saved the updated contact details"),
            @ApiResponse(responseCode = "400", description = "Contact does not exist hence can not be updated"),
            @ApiResponse(responseCode = "500", description = "Contact could not be updated due to server issues(possibly db related issues)")
    })
    @PutMapping(value = "/{phoneNumber}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Contact updateContact(@RequestBody Contact updatedContact) {
        Contact existingContact = contactService.getContactByPhoneNumber(updatedContact.getPhoneNumber());
        if(existingContact==null)
            throw new ContactException("Contact does not exist");
        else if(!contactService.updateContact(updatedContact))
            throw new RuntimeException("Failed to update the contact details");
        return updatedContact;
    }

    @Operation(summary = "Delete an existing contact", description = "Deletes a contact if it exists")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the updated contact details"),
            @ApiResponse(responseCode = "400", description = "Contact does not exist hence can not be deleted"),
            @ApiResponse(responseCode = "500", description = "Contact could not be deleted due to server issues(possibly db related issues)")
    })
    @DeleteMapping("/{phoneNumber}")
    public void deleteContact(@PathVariable String phoneNumber) {
        Contact existingContact = contactService.getContactByPhoneNumber(phoneNumber);
        if(existingContact==null)
            throw new ContactException("Contact does not exist");
        else if(!contactService.deleteContact(phoneNumber))
            throw new RuntimeException("Failed to delete contact");
    }

    @Operation(summary = "Search contact with a given phone number", description = "Returns a contact with the given phone number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the contact"),
            @ApiResponse(responseCode = "400", description = "Contact does not exist")
    })
    @GetMapping(value = "/search/phonenumber")
    public Contact getContactById(@RequestParam String phoneNumber) {
        Contact contact = contactService.getContactByPhoneNumber(phoneNumber);
        if(contact==null)
            throw new ContactException("No records found for contact with phone number "+phoneNumber);
        return contact;
    }

    @Operation(summary = "Search contacts with a given first name", description = "Returns a list of contacts with the given first name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the contact list(Could be an empty list)")
    })
    @GetMapping(value = "/search/firstname")
    public List<Contact> searchContactByFirstName(@RequestParam String firstName){
        return contactService.searchByFirstName(firstName);
    }

    @Operation(summary = "Search contacts with a given last name", description = "Returns a list of contacts with the given last name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the contact list(Could be an empty list)")
    })
    @GetMapping(value = "/search/lastname")
    public List<Contact> searchContactByLastName(@RequestParam String lastname){
        return contactService.searchByLastName(lastname);
    }

    @Operation(summary = "Search contacts with a given email", description = "Returns a list of contacts with the given email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the contact list(Could be an empty list)")
    })
    @GetMapping(value = "/search/email")
    public List<Contact> searchContactByEmail(@RequestParam String email){
        return contactService.searchByEmail(email);
    }

}
