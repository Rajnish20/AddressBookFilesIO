package com.magic.addressbook.service;

import com.magic.addressbook.entity.Contact;
import com.magic.addressbook.utility.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressBookService {
    DBConnection dbConnection = new DBConnection();
    private final Connection connection = dbConnection.getConnection();
    private PreparedStatement addressBookStatement;
    private static AddressBookService addressBookService;

    private AddressBookService() throws SQLException {
    }

    public static AddressBookService getInstance() throws SQLException {
        if (addressBookService == null)
            addressBookService = new AddressBookService();
        return addressBookService;
    }

    public List<Contact> readData() {
        String sql = "Select * from person;";
        List<Contact> contactList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            contactList = this.getPersonData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactList;
    }

    private List<Contact> getPersonData(ResultSet resultSet) {
        List<Contact> contactList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String city = resultSet.getString("city");
                String state = resultSet.getString("state");
                String pinCode = resultSet.getString("pinCode");
                String mobileNo = resultSet.getString("mobileNo");
                String email = resultSet.getString("email");
                contactList.add(new Contact(id, firstName, lastName, city, state, pinCode, mobileNo, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactList;
    }

    public int updatePersonCity(String firstName, String newCity) {
        try {
            String query = String.format("update person set City = '%s' where FirstName = '%s';", newCity, firstName);
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            return prepareStatement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Contact> getPersonData(String firstName) {
        List<Contact> contactList = null;
        if (this.addressBookStatement == null)
            this.preparedStatementForPerson();
        try {
            addressBookStatement.setString(1, firstName);
            ResultSet resultSet = addressBookStatement.executeQuery();
            contactList = this.getPersonData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactList;
    }

    private void preparedStatementForPerson() {
        try {
            String sql = "SELECT * FROM person WHERE firstName = ?";
            addressBookStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getNumberOfRecordByCity(String city) {
        int count = 0;
        String sql = String.format("Select count(city) as Count from person WHERE city = '%s';", city);
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                count = resultSet.getInt("Count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int addNewContactToDB(String firstName, String lastName, String city, String state, String pinCode,
                                 String mobileNo, String email) {
        int id = -1;
        String sql = String.format("INSERT INTO person(firstName, lastName, city, state, pinCode, mobileNo, email) "
                        + "VALUES( '%s', '%s', '%s', '%s','%s','%s','%s')", firstName, lastName, city, state,
                pinCode, mobileNo, email);
        try {
            Statement statement = connection.createStatement();
            int rowsAffected = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
            if (rowsAffected == 1) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next())
                    id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public int addNewContactToDBUsingTransaction(String firstName, String lastName, String city, String state, String pinCode,
                                                 String mobileNo, String email) {
        int id = -1;
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Statement statement = connection.createStatement()) {
            String sql = String.format("INSERT INTO person(firstName, lastName, city, state, pinCode, mobileNo, email) "
                            + "VALUES( '%s', '%s', '%s', '%s','%s','%s','%s')", firstName, lastName, city, state,
                    pinCode, mobileNo, email);
            int rowsAffected = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
            if (rowsAffected == 1) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next())
                    id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }


}
