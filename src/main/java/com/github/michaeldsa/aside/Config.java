package com.github.michaeldsa.aside;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import static java.nio.file.StandardOpenOption.*;

// Config validates or creates config file and application root dir
// (Aside_home). If not exists, created interactively using UIConfig.ui().
public class Config {
    // Paths to configuration directories & files:
//    private final Path home = Paths.get(System.getProperty("user.home"));  // This will be the real user home:
    private final Path configParentPath;    // ~/.config/
    private final Path configFilePath;      // ~/.config/aside/
    private final Path asideHomeFilename; // Last element of application root directory Path.
                                          // Full parent dir given by user interaction
    private final Properties properties;  // abstraction for reading config file

    public Config() {

        // unchanging file names. Other folder/file names may change based on OS.
        final String configFolder = "aside";
        final String configFile = "config";
        final String rootFolder = "Aside_home";

        // TODO:
        // [ ] branching statement based on o.s.. dir names will differ. Linux for now.
        //    [ ] Windows
        //    [ ] Mac

        // private final Path home = Paths.get(System.getProperty("user.home"));  // This will be the real user home:
        Path home = Paths.get("MOCK", "home", "user");  // pretend this is /home/user/
        configParentPath = home.resolve(Paths.get(".config", configFolder)); // ~/.config/aside/
        configFilePath = configParentPath.resolve(configFile);  // ~/.config/aside/config

        // application root directory name: Will be resolved to user's choice.
        // full resolved path to be saved in config file via Properties object.
        asideHomeFilename = Paths.get(rootFolder);

        properties = new Properties();

        initialize();
    }


    // goals of Config.intialize():
    // 1) verify config file exists
    // 2) validate config file data
    // 3) establish root directory of application, /Aside_home.
    private void initialize() {

        // If not exists, create the parent directory
        // of the config file (~/.config/aside/):
        if(Files.notExists(configParentPath)){
            try {
                Files.createDirectories(configParentPath);
            } catch (IOException e) {
                System.err.printf("failed to create config parent directory: %s%n%s%n", configParentPath, e);
            }
        }

        // get, set, store user data, or load it if it already stored.
        if (Files.notExists(configFilePath) || !validateFileSize(configFilePath)) {

            // interact with user to get configuration property: aside_home=?
            UIConfig uiconfig = new UIConfig();
            uiconfig.ui();
            Path asideHomeFilePath = uiconfig.getParentAsideHome().resolve(asideHomeFilename).toAbsolutePath().normalize();


            // set the user data
            properties.setProperty("aside_home", asideHomeFilePath.toString());

            // store user data
            try (OutputStream out = Files.newOutputStream(configFilePath, CREATE, WRITE)) {
                properties.store(out, " important directories:");
                properties.load(Files.newInputStream(configFilePath, READ));
            } catch (IOException e) {
                System.err.println("failed to write config file: " + e.getMessage());
            }

        // if config file exists, load it:
        } else {
            try {
                properties.load(Files.newInputStream(configFilePath, READ));
            } catch (IOException e) {
                System.out.printf("failed to load config file: %s%n%s%n", configFilePath, e);
            }
        }

        // If necessary create Aside_home root
        // folder as specified by config file:
        if (Files.notExists(getAsideHome())) {
            try {
                Files.createDirectories(getAsideHome());
            } catch (IOException e) {
                System.err.printf("failed to create aside_home directory: %s%n%s%n", getAsideHome(), e);
            }
        }
    }

    // returns Aside_home root folder
    // as specified by config file:
    public Path getAsideHome(){
        return Paths.get(properties.getProperty("aside_home"));
    }

    private boolean validateFileSize(Path file) {
        try {
            return Files.size(file) > 0;
        } catch (IOException e) {
            return false;
        }
    }

}
