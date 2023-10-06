package com.mohan.ContactManagementSystem.service;

import com.mohan.ContactManagementSystem.entity.Contact;

import java.util.List;

public interface ContactService {
    public boolean createContact(Contact contact);
    public boolean deleteContact(String phoneNumber);
    public Contact getContactByPhoneNumber(String phoneNumber);
    public boolean updateContact(Contact contact);
    public List<Contact> getAllContacts();
    public List<Contact> searchByFirstName(String firstName);

    public List<Contact> searchByLastName(String lastName);

    public List<Contact> searchByEmail(String email);
}
