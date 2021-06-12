package com.magic.addressbook.service;

import com.magic.addressbook.controller.ContactMenu;
import com.magic.addressbook.entity.Contact;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressBookOperations implements IAddressBookOperations {
    HashMap<String, List<Contact>> addressBook = new HashMap<>();

    @Override
    public void createAddressBook(String name) {
        addressBook.put(name, new ArrayList<>());
    }

    @Override
    public void openAddressBook(String name) {
        ContactMenu menu = new ContactMenu();
        menu.operations(addressBook.get(name));
    }

    @Override
    public void showAddressBooks() {
        for (Map.Entry mapElement : addressBook.entrySet()) {
            System.out.println((String) mapElement.getKey());
        }

    }

    @Override
    public void searchPersonInCity(String city) {
        for (Map.Entry mapElement : addressBook.entrySet()) {
            List<Contact> contacts = addressBook.get((String) mapElement.getKey());
            contacts.stream().filter(n -> n.getCity().equalsIgnoreCase(city)).forEach(System.out::println);
        }
    }

    @Override
    public void searchPersonInState(String state) {
        for (Map.Entry mapElement : addressBook.entrySet()) {
            List<Contact> contacts = addressBook.get((String) mapElement.getKey());
            contacts.stream().filter(n -> n.getState().equalsIgnoreCase(state)).forEach(System.out::println);
        }
    }

    @Override
    public void readToCSV(String file) {
        ReadWriteToCSV readWriteToCSV = new ReadWriteToCSV();
        readWriteToCSV.readFromCSV(file);
    }

    @Override
    public void writeToCSV(String name, String fileName) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        ReadWriteToCSV readWriteToCSV = new ReadWriteToCSV();
        readWriteToCSV.writeToCSV(addressBook.get(name),fileName);
    }
}
