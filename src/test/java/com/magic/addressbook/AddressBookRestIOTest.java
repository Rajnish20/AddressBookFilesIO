package com.magic.addressbook;

import com.magic.addressbook.entity.Contact;
import com.magic.addressbook.service.AddressBookServiceDB;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

public class AddressBookRestIOTest {

    @Test
    public void givenPersonInDB_WhenRetrieved_ShouldMatchEmployeeCount() throws SQLException {
        AddressBookServiceDB addressBookServiceDB = new AddressBookServiceDB();
        List<Contact> employList = addressBookServiceDB.readContactData();
        Assertions.assertEquals(1, employList.size());
    }

    @Test
    public void givenNewCityForPerson_WhenUpdated_ShouldSyncWithDB() throws SQLException {
        AddressBookServiceDB addressBookServiceDB = new AddressBookServiceDB();
        addressBookServiceDB.readContactData();
        addressBookServiceDB.updatePersonCity("Kristen", "Chicago");
        boolean result = addressBookServiceDB.checkPersonInSyncWithDB("Kristen");
        Assertions.assertTrue(result);
    }

    @Test
    public void givenCityName_WhenRetrievedNumberOfRecordFromDB_ShouldMatchCount() throws SQLException {
        AddressBookServiceDB addressBookServiceDB = new AddressBookServiceDB();
        int entry = addressBookServiceDB.getNumberOfPersonByCity("Chicago");
        Assertions.assertEquals(1, entry);
    }

    @Test
    public void givenPersonData_WhenAddedToDB_ShouldAlsoAddedInContactList() throws SQLException {
        AddressBookServiceDB addressBookServiceDB = new AddressBookServiceDB();
        addressBookServiceDB.readContactData();
        addressBookServiceDB.addContactToDB("Danny", "Sophie", "Sputnik", "Russia",
                "1109", "8765323", "sophie@gmail.com");
        int listSize = addressBookServiceDB.getSize();
        Assertions.assertEquals(3, listSize);

        boolean result = addressBookServiceDB.checkPersonInSyncWithDB("Danny");
        Assertions.assertTrue(result);
    }

    @Test
    public void given4Person_WhenAddedToDBUsingThreads_ShouldMatchEmployeeEntries() throws SQLException {
        AddressBookServiceDB addressBookServiceDB = new AddressBookServiceDB();
        Contact[] contacts = {
                new Contact(0, "Samuel", "Wayne", "New York", "USA", "1109",
                        "98776", "wayne@gmail.com"),
                new Contact(0, "Belie", "Shane", "Beijing", "Shanghai", "876",
                        "9876754", "belie@gmail.com"),
                new Contact(0, "Rock", "Johnson", "Bishkek", "Uzbekistan", "9876",
                        "987665", "rock@gmail.com"),
                new Contact(0, "Sana", "Williams", "Gotham", "USA", "876",
                        "989876", "sana@gmail.com")
        };
        addressBookServiceDB.readContactData();
        Instant start = Instant.now();
        int size = addressBookServiceDB.addMultiplePersonToDBUsingThreads(Arrays.asList(contacts));
        Instant end = Instant.now();
        System.out.println("Duration With Threads " + Duration.between(start, end));
        Assertions.assertEquals(7, size);
    }
}
