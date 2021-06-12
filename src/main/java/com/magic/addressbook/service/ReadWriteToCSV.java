package com.magic.addressbook.service;

import com.magic.addressbook.entity.Contact;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.*;
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
                data.add(new String[]{details.firstName,details.lastName,details.city,details.state ,details.pinCode,details.mobileNo,details.email});
            }
            writer.writeAll(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFromCSV(String fileName)
    {
        try {
            CSVReader csvReader = new CSVReader(new FileReader(ADDRESS_BOOK_FILE_NAME + fileName + ".csv"));
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                System.out.println("FirstName : " + nextRecord[0]);
                System.out.println("LastName : " + nextRecord[1]);
                System.out.println("City : " + nextRecord[2]);
                System.out.println("State : " + nextRecord[3]);
                System.out.println("PinCode: " + nextRecord[4]);
                System.out.println("MobileNo : " + nextRecord[5]);
                System.out.println("Email : " + nextRecord[6]);
                System.out.println("*************");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
