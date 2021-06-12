package com.magic.addressbook.entity;

import java.util.ArrayList;
import java.util.List;

public class AddressBook {
    List<Contact> contacts = new ArrayList<>();

    public List<Contact> getContacts() {
        return contacts;
    }

    @Override
    public String toString() {
        return "AddressBook{" +
                "contacts=" + contacts +
                '}';
    }
}
