package com.github.michaeldsa.aside;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Properties;

import static java.nio.file.StandardOpenOption.*;

// Config validates or creates config file and application root dir
// (Aside_home). If not exists, created interactively using UIConfig.ui().
public class Config {

    // Paths to configuration directories & files:
    private final Path configPath;    // ~/.config/aside/config
    private final Path configPath_full;
    private final Path asideDir; // Last element of application root directory Path.
                                          // Full parent dir given by user interaction
    private final Properties properties;  // abstraction for reading config file

    public Config() {

        // main folder that serves as root of aside directory:
        final String rootDir = "Aside_home";

        // config file, last element of path:


        final Path unixConfig = Paths.get(System.getProperty("user.home"), ".config", "aside"); // $XDG_CONFIG_HOME
        final Path macConfig = Paths.get(System.getProperty("user.home"), ".aside");
        final Path winConifg = Paths.get(System.getProperty("user.home"), ".aside");

        // default config path & file is linuxConfig / 'config':


        // CROSS-PLATFORM CONFIG SETUP:
        // VERIFIED WORKS ON:
        // [x] linux :-)
        // [x] mac
        // [ ] win
        // default non-mac unix ($XDG_CONFIG_HOME)
        // uncomment following to pacify Intellij.
        String osName = System.getProperty("os.name");
        Path dir = unixConfig;
        Path file = Paths.get("config");
        if (osName.toLowerCase(Locale.ENGLISH).contains("windows")) {
            dir = winConifg;
            file = Paths.get("config.txt");
        } else if (osName.toLowerCase(Locale.ENGLISH).contains("mac")) {
            dir = macConfig;
        }
        configPath = dir;
        final Path config_file = file;

        // assign to test dir for now. uncomment above
        // branch and delete following line when ready
//        configPath = Paths.get("MOCK", "home", "user", ".config");

        // configPath_full: config directory including config file:
        configPath_full = configPath.resolve(config_file);

        System.out.println("CONFIG_FILE: " + configPath_full);

        // application root directory name: Will be resolved to user's choice.
        // full resolved path to be saved in config file via Properties object.
        asideDir = Paths.get(rootDir);

        properties = new Properties();

    }


    // detect config file and application home directory:
    public void initialize() {

        // If not exists, create the parent directory
        // of the config file (~/.config/aside/):
        if(Files.notExists(configPath)){
            try {
                Files.createDirectories(configPath);
            } catch (IOException e) {
                System.err.printf("failed to create config parent directory: %s%n%s%n", configPath, e);
            }
        }

        // get, set, store user data, or load it if it already stored.
        if (Files.notExists(configPath_full) || !validateFileSize(configPath_full)) {

            configurePropertiesWithUser();
            storeProperties();
        }

        // if the loaded file does not have the home_directory
        // key, we need to get, set, save, and load that data.
        loadProperties();
        if(properties.contains("home_directory")
                || properties.getProperty("home_directory") == null
                || properties.getProperty("home_directory").isEmpty()
                || Files.notExists(Paths.get(properties.getProperty("home_directory")).getParent())
        ) {

           configurePropertiesWithUser("Problem with config file.");
           storeProperties();
           loadProperties();

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
        return Paths.get(properties.getProperty("home_directory"));
    }


    private boolean validateFileSize(Path file) {
        try {
            return Files.size(file) > 0;
        } catch (IOException e) {
            return false;
        }
    }

    public void configurePropertiesWithUser() {
        configurePropertiesWithUser(null);
    }

    public void configurePropertiesWithUser(String message) {

        // interact with user to get configuration property: aside_home=?
        UIConfig uiconfig = new UIConfig();
        if(message == null){
            uiconfig.ui();
        } else {
            uiconfig.ui(message);
        }

        // get the full path of the aside_home_directory
        Path asideHomeFilePath = uiconfig.getParentAsideHome().resolve(asideDir).toAbsolutePath().normalize();

        // set the user data
        properties.setProperty("home_directory", asideHomeFilePath.toString());
    }

    public void storeProperties() {
        // store user data
        try (OutputStream out = Files.newOutputStream(configPath_full, CREATE, WRITE)) {
            properties.store(out, " home directory:");
        } catch (IOException e) {
            System.err.println("failed to write config file: " + e.getMessage());
        }
    }

    public void loadProperties() {
        // load user data
        try (OutputStream out = Files.newOutputStream(configPath_full, CREATE, WRITE)) {
            properties.load(Files.newInputStream(configPath_full, READ));
        } catch (IOException e) {
            System.err.println("failed to write config file: " + e.getMessage());
        }
    }
}
