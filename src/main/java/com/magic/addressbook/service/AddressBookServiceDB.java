package com.magic.addressbook.service;

import com.magic.addressbook.entity.Contact;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressBookServiceDB {

    private List<Contact> contactList;
    private final AddressBookService addressBookService;

    public AddressBookServiceDB() throws SQLException {
        addressBookService = AddressBookService.getInstance();
    }

    public List<Contact> readContactData() {
        this.contactList = addressBookService.readData();
        return contactList;
    }

    public void updatePersonCity(String firstName, String newCity) {
        int result = addressBookService.updatePersonCity(firstName, newCity);
        if (result == 0) return;
        Contact contact = this.getPersonData(firstName);
        if (contact != null) contact.city = newCity;
    }

    private Contact getPersonData(String firstName) {
        return this.contactList.stream()
                .filter(employeePayrollDataItem -> employeePayrollDataItem.firstName.equals(firstName))
                .findFirst()
                .orElse(null);
    }

    public boolean checkPersonInSyncWithDB(String firstName) {
        List<Contact> contactList = addressBookService.getPersonData(firstName);
        return contactList.get(0).equals(getPersonData(firstName));
    }

    public int getNumberOfPersonByCity(String city) {
        return addressBookService.getNumberOfRecordByCity(city);
    }

    public void addContactToDB(String firstName, String lastName, String city, String state, String pinCode, String mobileNo,
                               String email) {
        int id = addressBookService.addNewContactToDB(firstName, lastName, city, state, pinCode, mobileNo, email);
        this.contactList.add(new Contact(id, firstName, lastName, city, state, pinCode, mobileNo, email));
    }

    public void addContactToDBUsingTransaction(String firstName, String lastName, String city, String state, String pinCode, String mobileNo,
                                               String email) {
        int id = addressBookService.addNewContactToDBUsingTransaction(firstName, lastName, city, state, pinCode, mobileNo, email);
        this.contactList.add(new Contact(id, firstName, lastName, city, state, pinCode, mobileNo, email));
    }


    public int addMultiplePersonToDBUsingThreads(List<Contact> contacts) {
        Map<Integer, Boolean> personAdditionStatus = new HashMap<>();
        contacts.forEach(contact -> {
            Runnable task = () -> {
                personAdditionStatus.put(contact.hashCode(), false);
                this.addContactToDBUsingTransaction(contact.firstName, contact.lastName, contact.city, contact.state,
                        contact.pinCode, contact.mobileNo, contact.email);
                personAdditionStatus.put(contact.hashCode(), true);
            };
            Thread thread = new Thread(task);
            thread.start();
        });
        while (personAdditionStatus.containsValue(false)) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return contactList.size();
    }


    public int getSize() {
        return this.contactList.size();
    }
}
