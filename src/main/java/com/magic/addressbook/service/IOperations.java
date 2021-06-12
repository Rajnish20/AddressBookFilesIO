package com.magic.addressbook.service;

import com.magic.addressbook.entity.Contact;

import java.util.List;

public interface IOperations {
    void addContacts(List<Contact> contacts, Contact contact);

    void displayContacts(List<Contact> contacts);

    void deleteContacts(List<Contact> contacts, String firstName, String lastName);

    void updateContact(List<Contact> contacts, String firstName, String lastName, Contact contact);

    void getCountInState(List<Contact> contacts, String state);

    void getCountInCity(List<Contact> contacts, String city);

    void sortUsingName(List<Contact> contacts);

    void sortUsingCity(List<Contact> contacts);

    void sortUsingState(List<Contact> contacts);
}
