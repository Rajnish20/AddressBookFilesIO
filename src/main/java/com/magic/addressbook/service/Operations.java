package com.magic.addressbook.service;

import com.magic.addressbook.entity.Contact;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Operations implements IOperations {
    @Override
    public void addContacts(List<Contact> contacts, Contact contact) {
        AtomicInteger existed = new AtomicInteger();
        contacts.forEach(contact1 -> {
            if (contact1.getFirstName().equalsIgnoreCase(contact.getFirstName()) && contact1.getLastName().equalsIgnoreCase(contact.getLastName()))
                existed.set(1);
        });
        if (existed.get() == 1)
            System.out.println("Person already existed");
        else {
            contacts.add(contact);
            System.out.println("Person Added Successfully");
        }
    }

    @Override
    public void displayContacts(List<Contact> contacts) {
        contacts.forEach(System.out::println);
    }

    @Override
    public void deleteContacts(List<Contact> contacts, String firstName, String lastName) {
        int flag = 0;
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getFirstName().equalsIgnoreCase(firstName) && contacts.get(i).getLastName().equalsIgnoreCase(lastName)) {
                flag = 1;
                contacts.remove(contacts.get(i));
                break;
            }
        }
        if (flag == 1)
            System.out.println("Contact deleted successfully");
        else
            System.out.println("Contact Not found");
    }

    @Override
    public void updateContact(List<Contact> contacts, String firstName, String lastName, Contact contact) {
        int flag = 0;
        for (Contact value : contacts) {
            if (value.getFirstName().equalsIgnoreCase(firstName) && value.getLastName().equalsIgnoreCase(lastName)) {
                flag = 1;
                value.setFirstName(contact.getFirstName());
                value.setLastName(contact.getLastName());
                value.setCity(contact.getCity());
                value.setState(contact.getState());
                value.setPinCode(contact.getPinCode());
                value.setMobileNo(contact.getMobileNo());
                value.setEmail(contact.getEmail());
                break;
            }
        }
        if (flag == 1)
            System.out.println("Contact updated successfully");
        else
            System.out.println("Contact Not found");
    }

    @Override
    public void getCountInState(List<Contact> contacts, String state) {
        System.out.println(contacts.stream().filter(n -> n.getState().equalsIgnoreCase(state)).count() + " Persons are from " + state);
    }

    @Override
    public void getCountInCity(List<Contact> contacts, String city) {
        System.out.println(contacts.stream().filter(n -> n.getCity().equalsIgnoreCase(city)).count() + " Persons are from " + city);
    }

    @Override
    public void sortUsingName(List<Contact> contacts) {
        contacts.stream().sorted(Comparator.comparing(Contact::getFirstName).thenComparing(Contact::getLastName)).forEach(System.out::println);
    }

    @Override
    public void sortUsingCity(List<Contact> contacts) {
        contacts.stream().sorted(Comparator.comparing(Contact::getCity)).forEach(System.out::println);
    }

    @Override
    public void sortUsingState(List<Contact> contacts) {
        contacts.stream().sorted(Comparator.comparing(Contact::getState)).forEach(System.out::println);
    }
}
