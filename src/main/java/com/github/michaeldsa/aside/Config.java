package com.github.michaeldsa.aside;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import static java.nio.file.StandardOpenOption.*;

public class Config {
//    private final Path home = Paths.get(System.getProperty("user.home"));  // This will be the real user home:
    private final Path home = Paths.get("MOCK", "user", "home");  // This is for testing purposes
    private final Path configDir = home.resolve(Paths.get(".config", "aside"));
    private final Path configFile = configDir.resolve("config");
    private final Properties properties = new Properties();

    // hardcoded paths found in config:
    private final Path asideHomeFilename = Paths.get("Aside_home");


    public void initialize() {

        // If not exists, create the directory for config file:
        if(Files.notExists(configDir)){
            try {
                Files.createDirectories(configDir);
            } catch (IOException e) {
                System.err.printf("failed to create config directory: %s%n%s%n", configDir, e);
            }
        } else {
            System.out.println(configDir + " exists");
        }

        // If config file does not exist, or has no data
        // 1) use UIConfig to get the data from the user
        // 2) declare a Properties var, and setProperty() of the user data
        // 3) create the file using an OutputStream
        // 4) .store() the data
        if (Files.notExists(configFile) || !validateFileSize(configFile)) {

            // get user data: 'aside.path'= ?...
            UIConfig uiconfig = new UIConfig();
            uiconfig.ui();

            // set the user data
            properties.setProperty("aside_home", uiconfig.getAsideHome().toString());

            // store user data
            try (OutputStream out = Files.newOutputStream(configFile, CREATE, WRITE)) {
                properties.store(out, "--important directories:--");
            } catch (IOException e) {
                System.err.println("failed to write config file: " + e.getMessage());
            }

        // if config file exists, load it:
        } else {
            System.out.println(configFile + " exists...");
            try {
                properties.load(Files.newInputStream(configFile, READ));
            } catch (IOException e) {
                System.out.printf("failed to load config file: %s%n%s%n", configFile, e);
            }
        }
        // if the Asid_home directory does not exist in
        // the properties configured parent folder, create it:
        if (Files.notExists(getAsideHome())) {
            try {
                Files.createDirectories(getAsideHome());
            } catch (IOException e) {
                System.err.printf("failed to create aside_home directory: %s%n%s%n", getAsideHome(), e);
            }
        }
    }

    public Path getAsideHome(){
        return Paths.get(properties.getProperty("aside_home")).toAbsolutePath().resolve(asideHomeFilename);
    }

    private boolean validateFileSize(Path file) {
        try {
            return Files.size(file) > 0;
        } catch (IOException e) {
            return false;
        }
    }

}
