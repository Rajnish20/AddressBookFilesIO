package com.magic.addressbook.service;

import com.google.gson.Gson;
import com.magic.addressbook.entity.Contact;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class ReadWriteToJson {
    public static String FILE_PATH = "C:\\Users\\rajni\\Desktop\\";

    public void writeToJson(List<Contact> contactList, String fileName) throws IOException {
        Path filePath = Paths.get(FILE_PATH + fileName + ".json");
        Gson gson = new Gson();
        String json = gson.toJson(contactList);
        FileWriter writer = new FileWriter(String.valueOf(filePath));
        writer.write(json);
        writer.close();
    }

    public void readFromJson(String fileName) throws FileNotFoundException {
        Gson gson = new Gson();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName + ".json"));
        Contact[] contacts = gson.fromJson(bufferedReader, Contact[].class);
        List<Contact> contactList = Arrays.asList(contacts);
        for (Contact contact : contactList) {
            System.out.println(contact.firstName);
            System.out.println(contact.lastName);
            System.out.println(contact.city);
            System.out.println(contact.state);
            System.out.println(contact.pinCode);
            System.out.println(contact.mobileNo);
            System.out.println(contact.email);
            System.out.println("******************");
        }
    }
}
