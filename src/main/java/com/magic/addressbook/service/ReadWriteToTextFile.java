package com.magic.addressbook.service;

import com.magic.addressbook.entity.Contact;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class ReadWriteToTextFile {

    public static String ADDRESS_BOOK_FILE_NAME = "C:\\Users\\rajni\\Desktop\\";

    public void writeToTextFile(List<Contact> personList, String fileName) {
        StringBuffer buffer = new StringBuffer();
        personList.forEach(emp -> {
            String contactDataString = emp.toString().concat("\n");
            buffer.append(contactDataString);
        });
        try {
            Files.write(Paths.get(ADDRESS_BOOK_FILE_NAME + fileName + ".txt"), buffer.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFromTextFile(String fileName) {
        List<String> lines = Collections.emptyList();
        try {
            lines =
                    Files.readAllLines(Paths.get(ADDRESS_BOOK_FILE_NAME + fileName + ".txt"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        lines.forEach(System.out::println);
    }

}
