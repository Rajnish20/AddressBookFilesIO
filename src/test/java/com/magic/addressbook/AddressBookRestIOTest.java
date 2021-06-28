package com.magic.addressbook;

import com.google.gson.Gson;
import com.magic.addressbook.entity.Contact;
import com.magic.addressbook.service.AddressBookRestIOService;
import com.magic.addressbook.service.AddressBookServiceDB;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

public class AddressBookRestIOTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3000;
    }

    public Contact[] getContactList() {
        Response response = RestAssured.get("/person");
        System.out.println(response.asString());
        return new Gson().fromJson(response.asString(), Contact[].class);
    }

    public Response addContactToJsonServer(Contact contact) {
        String empJson = new Gson().toJson(contact);
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(empJson);
        return request.post("/person");
    }

    @Test
    public void givenContactDataInJsonServer_WhenRetrieved_ShouldMatchTheCount() {
        Contact[] contacts = getContactList();
        AddressBookRestIOService addressBookRestIOService;
        addressBookRestIOService = new AddressBookRestIOService(Arrays.asList(contacts));
        addressBookRestIOService.printList();
        long entries = addressBookRestIOService.countEntries();
        Assertions.assertEquals(2, entries);
    }

    @Test
    public void givenNewContact_WhenAdded_ShouldMatch201ResponseAndCount() {
        Contact[] contacts = getContactList();
        AddressBookRestIOService addressBookRestIOService;
        addressBookRestIOService = new AddressBookRestIOService(Arrays.asList(contacts));
        Contact contact = new Contact(0, "Edward", "Cullan", "New York", "USA", "11091",
                "7838288097", "edward@gmail.com");
        Response response = addContactToJsonServer(contact);
        int statusCode = response.statusCode();
        Assertions.assertEquals(201, statusCode);

        contact = new Gson().fromJson(response.asString(), Contact.class);
        addressBookRestIOService.addEmployeeToList(contact);
        long entries = addressBookRestIOService.countEntries();
        Assertions.assertEquals(2, entries);

    }

    @Test
    public void givenNewState_WhenUpdated_ShouldMatch200Response() {
        Contact[] contacts = getContactList();
        AddressBookRestIOService addressBookRestIOService;
        addressBookRestIOService = new AddressBookRestIOService(Arrays.asList(contacts));
        addressBookRestIOService.updateStateInContactList("Mike", "Canada");
        Contact contact = addressBookRestIOService.getContact("Mike");

        String empJson = new Gson().toJson(contact);
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(empJson);
        Response response = request.put("/person/" + contact.id);
        int statusCode = response.getStatusCode();
        Assertions.assertEquals(200, statusCode);
    }

    @Test
    public void givenMultipleEmployee_WhenAdded_ShouldMatch201ResponseAndCount() {
        Contact[] contacts = getContactList();
        AddressBookRestIOService addressBookRestIOService;
        addressBookRestIOService = new AddressBookRestIOService(Arrays.asList(contacts));
        Contact[] contacts1 = {
                new Contact(0, "Mike", "Smith", "NEw Jersey", "USA", "1109", "9891982098", "mike@gmail.com"),
                new Contact(0, "Bella", "Cavil", "Los Angelos", "USA", "11009", "878984", "bella@gmail.com")
        };
        Arrays.stream(contacts1).forEach(contact -> {
            Response response = addContactToJsonServer(contact);
            int statusCode = response.getStatusCode();
            Assertions.assertEquals(201, statusCode);

            contact = new Gson().fromJson(response.asString(), Contact.class);
            addressBookRestIOService.addEmployeeToList(contact);
        });
        long entries = addressBookRestIOService.countEntries();
        Assertions.assertEquals(5, entries);
    }

    @Test
    public void givenEmployeeToDelete_WhenDeleted_ShouldMatch200ResponseAndCount() {
        Contact[] contacts = getContactList();
        AddressBookRestIOService addressBookRestIOService;
        addressBookRestIOService = new AddressBookRestIOService(Arrays.asList(contacts));

        Contact contact = addressBookRestIOService.getContact("Mike");
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        Response response = request.delete("/person/" + contact.id);
        int statusCode = response.getStatusCode();
        Assertions.assertEquals(200, statusCode);

        addressBookRestIOService.deleteEmployee(contact.firstName);
        long entries = addressBookRestIOService.countEntries();
        Assertions.assertEquals(1, entries);
    }

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
