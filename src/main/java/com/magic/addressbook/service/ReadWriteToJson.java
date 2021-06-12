package com.magic.addressbook.service;

import com.google.gson.Gson;
import com.magic.addressbook.entity.Contact;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
}
