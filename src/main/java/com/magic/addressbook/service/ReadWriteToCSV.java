package com.magic.addressbook.service;

import com.magic.addressbook.entity.Contact;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReadWriteToCSV {
    public static String ADDRESS_BOOK_FILE_NAME = "C:\\Users\\rajni\\Desktop\\";

    public void writeToCSV(List<Contact> personList, String fileName) throws IOException {
        Path filePath = Paths.get(ADDRESS_BOOK_FILE_NAME + fileName + ".csv");
        if (Files.notExists(filePath))
            Files.createFile(filePath);
        File file = new File(String.valueOf(filePath));
        try {
            FileWriter fw = new FileWriter(file, true);
            CSVWriter writer = new CSVWriter(fw);
            List<String[]> data = new ArrayList<>();
            for (Contact details : personList) {
                data.add(new String[]{"Person: " + "\n1. FirstName: " + details.firstName + "\n2. LastName: "
                        + details.lastName + "\n4. City: " + details.city + "\n5. State: "
                        + details.state + "\n6. Zip: " + details.pinCode + "\n7. PhoneNumber: " + details.mobileNo + "\n8. Email: " + details.email + "\n"
                });
            }
            writer.writeAll(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
