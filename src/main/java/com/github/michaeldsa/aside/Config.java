package com.github.michaeldsa.aside;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

public class Config {
//    private final Path home = Paths.get(System.getProperty("user.home"));  // This will be the real user home:
    private final Path home = Paths.get(".");  // This is for testing purposes
    private final Path configDir = home.resolve(Paths.get(".config", "aside"));
    private final Path configFile = configDir.resolve("config");

    public void initialize() {
        // If not exists, create the directory for config file:
        if(Files.notExists(configDir)){
            try {
                Files.createDirectories(configDir);
            } catch (IOException e) {
                System.err.printf("failed to create config directory: %s%n%s%n", configDir, e);
            }
        }
        // If the config file does not exist, first get the information we
        // need from the user, then create the file and set up a properties
        // object, and user the properties object to write the data to the file.
        if (Files.notExists(configFile)) {
            // If the directory exists, but the config file does not,
            // we need to create the Properties and the config file.
            UIConfig uiconfig = new UIConfig();
            uiconfig.ui();
            // uiconfig.getAsideHome();

            try (OutputStream is = Files.newOutputStream(configFile, CREATE, WRITE)) {
                Properties properties = new Properties();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }





    }

}
