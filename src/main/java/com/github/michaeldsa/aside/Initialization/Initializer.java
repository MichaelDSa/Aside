package com.github.michaeldsa.aside.Initialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.file.StandardOpenOption.*;

/*
Config.java is a Singleton used to initialize the application. The
initialization process:
    1) determines where the config file will be stored based
       on the os,
    2) determines what directory will store all notes - the sandbox. If a
       config file does not yet exist, a dialogue will ask the user,
    3) creates, if necessary, the fundamental directories of the
       sandbox.
    4) provides the data found in this config file to clients.
 */
public enum Initializer {
    INSTANCE;

    // Paths to configuration directories & files:
    private final Path configPath_full; // eg: will become ~/.config/aside/config
    private final Path aside_root_last_element; // Last element of aside_root directory. Parent dir given by user interaction
    private final Properties properties;  // Java abstraction for reading config files

    private final boolean success_initialization;

    // These values will be found in the config file
    private final Path aside_root;
    private final Path viewpath_root;
    private final Path metapath_root;

    // names of files:
//    private final

    // names of keys:
    private final String key_aside_root = "aside_root";
    private final String key_metapath_root = "metapath_root";
    private final String key_viewpath_root = "viewpath_root";

    Initializer() {

        // DETERMINE CONFIG PATH

        // parent directory of config file on each os:
        final Path unixConfig = Paths.get(System.getProperty("user.home"), ".config", "aside"); // $XDG_CONFIG_HOME
        final Path macConfig = Paths.get(System.getProperty("user.home"), ".aside");
        final Path winConfig = Paths.get(System.getProperty("user.home"), ".aside");

        // switch based on os:
        // default case: "linux":
        String os = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
        Path configPath = unixConfig;                    // eg: ~/.config/aside/
        Path configFile = Paths.get("config");      // eg: ~/.config/aside/config
        switch (os) {
            case "mac":
                configPath = macConfig;
                break;
            case "windows":
                configPath = winConfig;
                configFile = Paths.get("config.txt");
                break;
        }

        configPath_full = configPath.resolve(configFile);

        // TEST:
        // assign to test dir for testing. (un)comment above
        // branch and delete following line when necessary
//        configPath = Paths.get("MOCK", "home", "user", ".config");



        // APPLICATION DIRECTORY SETUP (aside_root):

        // the final element of aside_root
        final String rootDir = "aside_notes";

        // aside_root will be resolved to aside_root_parent
        // from UIConfig. It will then be saved as `aside_root`
        // in config file via Properties
        aside_root_last_element = Paths.get(rootDir);

        properties = new Properties();

        success_initialization = initialize();

        if (isSuccess_initialization() && validateConfigPaths()) {
            aside_root = Paths.get(properties.getProperty(key_aside_root));
            metapath_root = Paths.get(properties.getProperty(key_metapath_root));
            viewpath_root = Paths.get(properties.getProperty(key_viewpath_root));
        } else {
            aside_root = null;
            metapath_root = null;
            viewpath_root = null;
            System.err.println("Error initializing config");
            System.exit(1);
        }


    }

    // detect config file and the sandbox root (aside_root):
    private boolean initialize(){

        // necessary keys:
        Set<String> keys = new HashSet<>(Arrays.asList(key_aside_root, key_metapath_root, key_viewpath_root));

        // load properties; preserve success:
        boolean configured = loadProperties();

        if (!configured) {
            configured = setup() && loadProperties();
        }

        // check that all property keys are found, and that all associated directories exist:
        if (configured) {
            configured = allPropKeysFound(keys) && directoriesInPropertiesExist() && validateConfigPaths();
        } else {
            System.out.println("setup() or loadProperties() failed");
            return false;
        }

        if (!configured) {
            System.out.println("Failed: allPropKeysFound() && directoriesInPropertiesExist() && validateConfigPaths().\ncorrecting...");
            // directories have not been created. attempt setup again, load properties, and check directories again:
            configured = setup("Let's try that again.") && loadProperties() && allPropKeysFound(keys) && directoriesInPropertiesExist() && validateConfigPaths();
        }

        return configured;

    }

    // returns aside_root as specified by config file
    public Path getAside_root() {
        return aside_root;
    }

    public Path getMetapath() {
        return metapath_root;
    }

    public Path getViewpath() {
        return viewpath_root;
    }

    // get config file parent:
    public Path getConfigDirectory() {
        return configPath_full.getParent();
    }

    public boolean isSuccess_initialization() {
        return success_initialization;
    }

    private boolean allPropKeysFound(Set<String> keys) {

        Set<String> propKeys = properties.stringPropertyNames();

        boolean valid = propKeys.size() == keys.size();

        if (!valid) {
            System.out.println("allPropKeysFound(): key sizes don't match!");
        }

        if(valid) {
            for (String key : keys) {
                if (!propKeys.contains(key)){
                    System.out.println("allPropKeysFound() false");
                    return false;
                }
            }
        }
        return valid;
    }

    private void configurePropertiesWithUser() {
        configurePropertiesWithUser(null);
    }

    private void configurePropertiesWithUser(String message) {

        // interact with user to get configura
        InitDialogue uiconfig = new InitDialogue();
        if(message == null){
            uiconfig.ui();
        } else {
            uiconfig.ui(message);
        }

        // get the full path of the "home_directory" value
        Path value = uiconfig.get_aside_root_parent().resolve(aside_root_last_element).toAbsolutePath().normalize();

        // set the user data
        properties.setProperty("aside_root", value.toString());
    }

    private void configure_metapath_root(){
        configure_metapath_root(null);
    }
    private void configure_metapath_root(String path) {
        Path metapath;
        if(path == null) {
            // default metapath_root is aside_root/.meta
            metapath = Paths.get(properties.getProperty(key_aside_root)).resolve(".meta");
        } else {
            metapath = Paths.get(path);
        }
        properties.setProperty(key_metapath_root, metapath.toString());
    }

    private void configure_viewpath_root() {configure_viewpath_root(null);}
    private void configure_viewpath_root(String path) {
        Path viewpath;
        if(path == null) {
            // default viewpath_root is aside_root/vidw
            viewpath = Paths.get(properties.getProperty(key_aside_root)).resolve("view");
        } else {
            viewpath = Paths.get(path);
        }
        properties.setProperty(key_viewpath_root, viewpath.toString());
    }

    private boolean directoriesInPropertiesExist() {
        // traverse keys of properties file. All values should be Paths.
        for(String key : properties.stringPropertyNames()) {
            String value = properties.getProperty(key);
            Path path = Paths.get(value);
            if(Files.notExists(path) || !Files.isDirectory(path) || value.isEmpty()){
                return false;
            }
        }
        return true;
    }

    private boolean loadProperties() {
        try (InputStream in = Files.newInputStream(configPath_full, READ)) {
            properties.load(in);
        } catch (IOException e) {
            System.err.println("failed to read config file: " + e.getMessage());
            return false;
        }
        return true;
    }

    private boolean setup() {return setup(null);}
    private boolean setup(String message) {

        // first set the properties:
        if(Files.notExists(configPath_full) || !validateFileSize(configPath_full) || properties.getProperty(key_aside_root).isEmpty()){
            configurePropertiesWithUser(message);
        }
        configure_metapath_root();
        configure_viewpath_root();

        // create the directories from the key values that are directories:
        Set<String> nonDirKeys = new HashSet<>(Arrays.asList("Non_directory_key_example", "another_example"));
        try {
            for (String s : properties.stringPropertyNames()) {
                if (!nonDirKeys.contains(s)) {
                    Files.createDirectories(Paths.get(properties.getProperty(s)));
                }
            }
            // then store properties:
            storeProperties();
        } catch (IOException e) {
            System.err.printf("exception caught in Config.setup(). exception: %n%s%n", e.getMessage());
            return false;
        }

        return true;

    }

    private void storeProperties() {
        // store user data
        try (OutputStream out = Files.newOutputStream(configPath_full, CREATE, WRITE)) {
            properties.store(out, null);
        } catch (IOException e) {
            System.err.println("failed to write config file: " + e.getMessage());
        }
    }

    private boolean validateConfigPaths() {
        Path root = Paths.get(properties.getProperty(key_aside_root));
        Path viewpath = Paths.get(properties.getProperty(key_viewpath_root));
        Path metapath = Paths.get(properties.getProperty(key_metapath_root));

        return viewpath.getParent().equals(root) && metapath.getParent().equals(root);
    }

    private boolean validateFileSize(Path file) {
        try {
            return Files.size(file) > 0;
        } catch (IOException e) {
            return false;
        }
    }
}
