package com.magic.addressbook.controller;

import com.magic.addressbook.service.AddressBookOperations;
import com.magic.addressbook.service.IAddressBookOperations;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        Scanner scanner = new Scanner(System.in);
        IAddressBookOperations addressBookOperations = new AddressBookOperations();
        int option;
        do {
            System.out.println("Enter 1 to create new Address Book");
            System.out.println("Enter 2 to open an Address Book");
            System.out.println("Enter 3 to show All Address Book");
            System.out.println("Enter 4 to search Persons in city");
            System.out.println("Enter 5 to search Persons in state");
            System.out.println("Enter 6 to Write To CSV File");
            System.out.println("Enter 7 Read from CSV File");
            System.out.println("Enter 8 to write to json file");
            System.out.println("Enter 9 to exit");
            option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    System.out.println("Enter Address Book Name");
                    String addressBookName = scanner.nextLine();
                    addressBookOperations.createAddressBook(addressBookName);
                    break;
                case 2:
                    System.out.println("Below mentioned Address Books are available");
                    addressBookOperations.showAddressBooks();
                    System.out.println("Enter name of Address Book");
                    String name = scanner.nextLine();
                    addressBookOperations.openAddressBook(name);
                    break;
                case 3:
                    addressBookOperations.showAddressBooks();
                    break;
                case 4:
                    System.out.println("Enter City");
                    String city = scanner.nextLine();
                    addressBookOperations.searchPersonInCity(city);
                    break;
                case 5:
                    System.out.println("Enter State");
                    String state = scanner.nextLine();
                    addressBookOperations.searchPersonInState(state);
                    break;
                case 6:
                    System.out.println("Enter Address Book Name");
                    String addressBookToCSV = scanner.next();
                    System.out.println("Enter Name of CSV File in Which You Want to Write");
                    String fileName = scanner.next();
                    addressBookOperations.writeToCSV(addressBookToCSV, fileName);
                    break;
                case 7:
                    System.out.println("Enter Name of CSV File from Which You Want to Read");
                    String csvFile = scanner.next();
                    addressBookOperations.readToCSV(csvFile);
                    break;
                case 8:
                    System.out.println("Enter Address Book Name");
                    String addressBookToJson = scanner.next();
                    System.out.println("Enter Name of json file in which you want to Write");
                    String file = scanner.next();
                    addressBookOperations.writeToJson(addressBookToJson,file);
                    break;
                default:
                    break;
            }
        } while (option != 9);
    }
}
