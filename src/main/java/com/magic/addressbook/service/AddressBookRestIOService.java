package com.magic.addressbook.service;

import com.magic.addressbook.entity.Contact;

import java.util.ArrayList;
import java.util.List;

public class AddressBookRestIOService {

    private List<Contact> contactList;

    public AddressBookRestIOService(List<Contact> contactList) {
        this.contactList = new ArrayList<>(contactList);
    }

    public void addEmployeeToList(Contact contact) {
        this.contactList.add(contact);
    }

    public void updateStateInContactList(String firstName, String newState) {
        Contact contact = this.getContact(firstName);
        System.out.println(contact);
        if (contact != null)
            contact.setState(newState);
    }

    public long countEntries() {
        return this.contactList.size();
    }

    public void deleteEmployee(String firstName) {
        this.contactList.remove(this.getContact(firstName));
    }

    public Contact getContact(String firstName) {
        return this.contactList.stream()
                .filter(contact -> contact.firstName.equalsIgnoreCase(firstName))
                .findFirst()
                .orElse(null);
    }

    public void printList()
    {
        System.out.println(this.contactList);
    }
}
