package com.github.michaeldsa.aside;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

public class PropertiesTest {
    Properties prop;
    public PropertiesTest(){
        prop = new Properties();

        // set empty values:
        prop.setProperty("title", "");
        prop.setProperty("to", "");
        prop.setProperty("from", "");
        prop.setProperty("tags", "");
        prop.setProperty("content", "");

        try (OutputStream out = Files.newOutputStream(Paths.get("/home/michael/projects/Java/cli_applications/Aside/MOCK/prop.txt"), CREATE, WRITE)){
            prop.store(out, null);
        } catch (IOException e) {
            System.err.printf("prop unsuccessful. %s%n", e);
        }


    }


}
