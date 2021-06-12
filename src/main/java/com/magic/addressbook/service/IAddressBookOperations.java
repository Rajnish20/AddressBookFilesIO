package com.magic.addressbook.service;

import java.io.IOException;

public interface IAddressBookOperations {
    void createAddressBook(String name);

    void openAddressBook(String name);

    void showAddressBooks();

    void searchPersonInCity(String city);

    void searchPersonInState(String state);

    void writeToCSV(String name, String fileName) throws IOException;

    void readToCSV(String file);
}
