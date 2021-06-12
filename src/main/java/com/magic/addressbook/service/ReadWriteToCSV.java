package com.magic.addressbook.service;

import com.magic.addressbook.entity.Contact;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReadWriteToCSV {
    public static String ADDRESS_BOOK_FILE_NAME = "C:\\Users\\rajni\\Desktop\\";

    public void writeToCSV(List<Contact> personList,String fileName) throws IOException, CsvDataTypeMismatchException,CsvRequiredFieldEmptyException {
        try
                (
                        Writer writer = Files.newBufferedWriter(Paths.get(ADDRESS_BOOK_FILE_NAME +fileName+".csv"));
                ){
            StatefulBeanToCsv<Contact> beanToCsv = new StatefulBeanToCsvBuilder<Contact>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
            beanToCsv.write(personList);
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
