package com.magic.addressbook.controller;

import com.magic.addressbook.entity.Contact;
import com.magic.addressbook.service.IOperations;
import com.magic.addressbook.service.Operations;

import java.util.List;
import java.util.Scanner;

public class ContactMenu {
    public void operations(List<Contact> contacts) {
        IOperations operations = new Operations();
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            System.out.println("Choose Options : ");
            System.out.println("Enter 1 to add contact in Address Book");
            System.out.println("Enter 2 to delete contact from Address Book");
            System.out.println("Enter 3 o edit/update contact from Address Book");
            System.out.println("Enter 4 to display contacts");
            System.out.println("Enter 5 to see number of persons in a particular state");
            System.out.println("Enter 6 to see number of persons in a particular city");
            System.out.println("Enter 7 to see person list in sorted manner by names");
            System.out.println("Enter 8 to see person list in sorted manner by cities");
            System.out.println("Enter 9 to see person list in sorted manner by states");
            System.out.println("Enter 10 to close");
            option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    System.out.println("Enter First Name");
                    String firstName = scanner.nextLine();
                    System.out.println("Enter Last Name");
                    String lastName = scanner.nextLine();
                    System.out.println("Enter City");
                    String city = scanner.nextLine();
                    System.out.println("Enter State");
                    String state = scanner.nextLine();
                    System.out.println("Enter Pin Code");
                    String pinCode = scanner.nextLine();
                    System.out.println("Enter Phone Number");
                    String mobileNo = scanner.nextLine();
                    System.out.println("Enter Email");
                    String email = scanner.nextLine();
                    operations.addContacts(contacts, new Contact(firstName, lastName, city, state, pinCode, mobileNo, email));
                    break;
                case 2:
                    System.out.println("Enter first Name ");
                    String fName = scanner.nextLine();
                    System.out.println("Enter Last Name");
                    String lName = scanner.nextLine();
                    operations.deleteContacts(contacts, fName, lName);
                    break;
                case 3:
                    System.out.println("Enter first name");
                    String name1 = scanner.nextLine();
                    System.out.println("Enter last name");
                    String name2 = scanner.nextLine();
                    System.out.println("Enter New First Name");
                    String newFirstName = scanner.nextLine();
                    System.out.println("Enter New Last Name");
                    String newLastName = scanner.nextLine();
                    System.out.println("Enter New City");
                    String newCity = scanner.nextLine();
                    System.out.println("Enter New State");
                    String newState = scanner.nextLine();
                    System.out.println("Enter New Pin Code");
                    String newPinCode = scanner.nextLine();
                    System.out.println("Enter New Phone Number");
                    String newMobileNo = scanner.nextLine();
                    System.out.println("Enter New Email");
                    String newEmail = scanner.nextLine();
                    operations.updateContact(contacts, name1, name2, new Contact(newFirstName, newLastName, newCity, newState, newPinCode, newMobileNo, newEmail));
                    break;
                case 4:
                    operations.displayContacts(contacts);
                    break;
                case 5:
                    System.out.println("Enter state");
                    String State = scanner.nextLine();
                    operations.getCountInState(contacts, State);
                    break;
                case 6:
                    System.out.println("Enter city");
                    String City = scanner.nextLine();
                    operations.getCountInCity(contacts, City);
                    break;
                case 7:
                    operations.sortUsingName(contacts);
                    break;
                case 8:
                    operations.sortUsingCity(contacts);
                    break;
                case 9:
                    operations.sortUsingState(contacts);
                    break;
                default:
                    break;
            }

        } while (option != 10);
    }
}
