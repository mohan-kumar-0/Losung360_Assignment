package com.mohan.ContactManagementSystem.service;

import com.mohan.ContactManagementSystem.entity.Contact;
import com.mohan.ContactManagementSystem.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    ContactRepository contactRepository;
    @Override
    public boolean createContact(Contact contact) {
        contactRepository.save(contact);
        return true;
    }

    @Override
    public boolean deleteContact(String phoneNumber) {
        contactRepository.deleteById(phoneNumber);
        return true;
    }

    @Override
    public Contact getContactByPhoneNumber(String phoneNumber) {
        Contact contact = (Contact) contactRepository.findById(phoneNumber).orElse(null);
        return contact;
    }

    @Override
    public boolean updateContact(Contact contact) {
        boolean validContact = true;
        Contact updatedContact = getContactByPhoneNumber(contact.getPhoneNumber());
        if(updatedContact==null)
            validContact = false;
        if(validContact){
            if(contact.getFirstName()!=null)
                updatedContact.setFirstName(contact.getFirstName());
            if(contact.getLastName()!=null)
                updatedContact.setLastName(contact.getLastName());
            if(contact.getEmail()!=null)
                updatedContact.setEmail(contact.getEmail());
            if(contact.getPhoneNumber()!=null)
                updatedContact.setPhoneNumber(contact.getPhoneNumber());
            contactRepository.save(updatedContact);
        }
        return validContact;
    }

    @Override
    public List<Contact> getAllContacts() {
        List<Contact> contactList = (List<Contact>) contactRepository.findAll();
        return contactList;
    }

    @Override
    public List<Contact> searchByFirstName(String firstName) {
        List<Contact> contactList = (List<Contact>) contactRepository.findAllByFirstName(firstName);
        return contactList;
    }

    @Override
    public List<Contact> searchByLastName(String lastName) {
        List<Contact> contactList = (List<Contact>) contactRepository.findAllByLastName(lastName);
        return contactList;
    }

    @Override
    public List<Contact> searchByEmail(String email) {
        List<Contact> contactList = (List<Contact>) contactRepository.findAllByEmail(email);
        return contactList;
    }
}
