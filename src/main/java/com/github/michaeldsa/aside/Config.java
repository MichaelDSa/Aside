package com.github.michaeldsa.aside;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.file.StandardOpenOption.*;

// Config validates or creates config file and application
// root dir (aside_root=/path/to/aside_root). aside_root is
// the root of the sandbox in which aside operates. If not
// exists, its created interactively using UIConfig.ui().
public class Config {

    // Paths to configuration directories & files:
    private final Path configPath_full; // eg: will become ~/.config/aside/config
    private final Path aside_root; // Last element of aside_root directory. Parent dir given by user interaction
    private final Properties properties;  // Java abstraction for reading config files


    public Config() {

        // CONFIGURATION PATH SETUP:

        // parent directory of config file on each os:
        final Path unixConfig = Paths.get(System.getProperty("user.home"), ".config", "aside"); // $XDG_CONFIG_HOME
        final Path macConfig = Paths.get(System.getProperty("user.home"), ".aside");
        final Path winConfig = Paths.get(System.getProperty("user.home"), ".aside");

        String os = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
        // switch based on os:
        // default case: "linux":
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

        // the final element of aside_home
        final String rootDir = "aside_home";

        // aside_home will be resolved to aside_root_parent
        // from UIConfig. It will then be saved as `aside_home`
        // in config file via Properties
        aside_root = Paths.get(rootDir);

        properties = new Properties();

    }

    // detect config file and the sandbox root (aside_root):
    public boolean initialize(){

        // necessary keys:
        Set<String> keys = new HashSet<>(Arrays.asList("aside_root", "metapath_root", "viewpath_root"));

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
    public Path get_aside_root(){
        return Paths.get(properties.getProperty("aside_root"));
    }

    public Path get_metapath_root() {
        return Paths.get(properties.getProperty("metapath_root"));
    }

    public Path get_viewpath_root() {
        return Paths.get(properties.getProperty("viewpath_root"));
    }

    public boolean allPropKeysFound(Set<String> keys) {

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

    public void configurePropertiesWithUser() {
        configurePropertiesWithUser(null);
    }

    public void configurePropertiesWithUser(String message) {

        // interact with user to get configura
        UIConfig uiconfig = new UIConfig();
        if(message == null){
            uiconfig.ui();
        } else {
            uiconfig.ui(message);
        }

        // get the full path of the "home_directory" value
        Path value = uiconfig.getDirectoryHomeParent().resolve(aside_root).toAbsolutePath().normalize();

        // set the user data
        properties.setProperty("aside_root", value.toString());
    }
    public void configure_metapath_root(){
        configure_metapath_root(null);
    }
    public void configure_metapath_root(String path) {
        Path metapath;
        if(path == null) {
            // default metapath_root is aside_root/.meta
            metapath = Paths.get(properties.getProperty("aside_root")).resolve(".meta");
        } else {
            metapath = Paths.get(path);
        }
        properties.setProperty("metapath_root", metapath.toString());
    }

    public void configure_viewpath_root() {configure_viewpath_root(null);}
    public void configure_viewpath_root(String path) {
        Path viewpath;
        if(path == null) {
            // default viewpath_root is aside_root/vidw
            viewpath = Paths.get(properties.getProperty("aside_root")).resolve("view");
        } else {
            viewpath = Paths.get(path);
        }
        properties.setProperty("viewpath_root", viewpath.toString());
    }

    public boolean directoriesInPropertiesExist() {

        // make a modifiable set of the keys:
        Set<String> keys = new HashSet<>(properties.stringPropertyNames());

        // make a set of keys associated with non-directory values:
        Set<String> nonDirKeys = new HashSet<>(Arrays.asList("Some_nonDir_example", "another_nonDir_example"));

        // remove all non-dirs from set:
        keys.removeAll(nonDirKeys);

        // traverse only the directory keys:
        for(String key : keys) {
            String value = properties.getProperty(key);
            Path path = Paths.get(value);
            if(Files.notExists(path) || !Files.isDirectory(path) || value.isEmpty()){
                return false;
            }
        }
        return true;
    }

    public boolean loadProperties() {
        try (InputStream in = Files.newInputStream(configPath_full, READ)) {
            properties.load(in);
        } catch (IOException e) {
            System.err.println("failed to read config file: " + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean setup() {return setup(null);}
    public boolean setup(String message) {

        // first set the properties:
        if(Files.notExists(configPath_full) || !validateFileSize(configPath_full) || properties.getProperty("aside_root").isEmpty()){
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

    public void storeProperties() {
        // store user data
        try (OutputStream out = Files.newOutputStream(configPath_full, CREATE, WRITE)) {
            properties.store(out, null);
        } catch (IOException e) {
            System.err.println("failed to write config file: " + e.getMessage());
        }
    }

    private boolean validateConfigPaths() {
        Path root = get_aside_root();
        Path viewpath = get_viewpath_root();
        Path metapath = get_metapath_root();

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
