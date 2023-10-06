package com.mohan.ContactManagementSystem.repository;

import com.mohan.ContactManagementSystem.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact,String> {
    public Contact findByFirstName(String firstName);
    public List<Contact> findAllByFirstName(String firstName);
    public Contact findByLastName(String lastName);
    public List<Contact> findAllByLastName(String lastName);
    public Contact findByEmail(String email);
    public List<Contact> findAllByEmail(String email);
}
