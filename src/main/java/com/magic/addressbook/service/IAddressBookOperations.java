package com.magic.addressbook.service;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IAddressBookOperations {
    void createAddressBook(String name);

    void openAddressBook(String name);

    void showAddressBooks();

    void searchPersonInCity(String city);

    void searchPersonInState(String state);

    void readToCSV(String file);

    void writeToCSV(String name, String fileName) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException;

    void writeToJson(String name, String fileName) throws IOException;

    void readFromJson(String fileName) throws FileNotFoundException;

    void writeToTextFile(String name, String fileName);

    void readFromTextFile(String fileName);
}
