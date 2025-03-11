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
    private final Path home = Paths.get("MOCK", "user", "home");  // This is for testing purposes
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
        } else {
            System.out.println(configDir + "exists");
        }

        // If config file does not exist,
        // 1) use UIConfig to get the data from the user
        // 2) declare a Properties var, and setProperty() of the user data
        // 3) create the file using an OutputStream
        // 4) .store() the data
       Properties properties = new Properties();
        if (Files.notExists(configFile)) {
            UIConfig uiconfig = new UIConfig();
            uiconfig.ui();
            Path asidePath = uiconfig.getAsidePath();
            try (OutputStream is = Files.newOutputStream(configFile, CREATE, WRITE)) {
                properties.store(is, null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println(configFile + "exists");
        }

        // now get information from the user:
        // uiconfig.getAsideHome();





    }

}
