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
    private final Path configPath;    // eg: ~/.config/aside/
    private final Path configPath_full; // eg: will become ~/.config/aside/config
    private final Path aside_root; // Last element of aside_root directory. Parent dir given by user interaction
    private final String[] subdirectory_roots; // subdirectories to be resolved to directory_home, such as metapath_root, viewpath_root.
    private final Properties properties;  // Java abstraction for reading config files


    public Config() {

        // CONFIGURATION PATH SETUP:

        // parent directory of config file on each os:
        final Path unixConfig = Paths.get(System.getProperty("user.home"), ".config", "aside"); // $XDG_CONFIG_HOME
        final Path macConfig = Paths.get(System.getProperty("user.home"), ".aside");
        final Path winConifg = Paths.get(System.getProperty("user.home"), ".aside");

        // default config file parent directory
        Path dir = unixConfig;

        // default config file name:
        Path file = Paths.get("config");

        // assign value to configPath and file path based on os.name.
        // Later configPath.resolve(file) will be configPath_full
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase(Locale.ENGLISH).contains("windows")) {
            dir = winConifg;
            file = Paths.get("config.txt");
        } else if (osName.toLowerCase(Locale.ENGLISH).contains("mac")) {
            dir = macConfig;
        }
        configPath = dir;
        final Path config_file = file;

        // assign to test dir for testing. (un)comment above
        // branch and delete following line when necessary
//        configPath = Paths.get("MOCK", "home", "user", ".config");

        // configPath_full: config directory including config file:
        configPath_full = configPath.resolve(config_file);


        // APPLICATION DIRECTORY SETUP (aside_root):

        // the final element of aside_home
        final String rootDir = "aside_home";

        // aside_home will be resolved to aside_root_parent
        // from UIConfig. It will then be saved as `aside_home`
        // in config file via Properties
        aside_root = Paths.get(rootDir);

        // necessary subdirectories of aside_home
        subdirectory_roots = new String[]{".meta", "view", ".trash"};

        properties = new Properties();

    }


    // detect config file and the sandbox root, aside_root:
    public void initialize() {

        // create the path to the config file
        // if it doesn't exist (~/.config/aside/):
        if(Files.notExists(configPath)){
            try {
                Files.createDirectories(configPath);
            } catch (IOException e) {
                System.err.printf("failed to create config parent directory: %s%n%s%n", configPath, e);
            }
        }

        // If the config file does not exist, first get the data it
        // needs from the user and other sources, then create the config file
        // and store the properties. Later, we load the properties from source.
        if (Files.notExists(configPath_full) || !validateFileSize(configPath_full)) {
            configurePropertiesWithUser();
            configure_metapath_root();
            configure_viewpath_root();
            storeProperties();
        }

        // Now we load the properties, but if the file does not have the
        // aside_root key, we again get the data from the user and other
        // sources, and load the config file again.
        loadProperties();
        if( !properties.containsKey("aside_root")
                || properties.getProperty("aside_root") == null
                || properties.getProperty("aside_root").isEmpty()
                || Files.notExists(Paths.get(properties.getProperty("aside_root")).getParent())
                || !Files.isDirectory(Paths.get(properties.getProperty("aside_root")))
        ) {


            configurePropertiesWithUser("Problem with config file.");
            configure_metapath_root();
            configure_viewpath_root();
            storeProperties();
            loadProperties();

        }

        // if aside_root path does not exist, create
        // it as well as its necessary subdirectories.
        // first check if the subdirectories exist
        boolean subdirs_exist = false;
        for (String subdir : subdirectory_roots) {
            if (Files.exists(get_aside_root().resolve(subdir))){
                subdirs_exist = true;
            }
        }
        if (Files.notExists(get_aside_root()) || !subdirs_exist) {
            try {
                // directory_home
                Files.createDirectories(get_aside_root());

                // sub-dirs of directory_home: .meta/, view/, .trash/
                for (String subdir : subdirectory_roots) {
                    Files.createDirectories(get_aside_root().resolve(subdir));
                }
            } catch (IOException e) {
                System.err.printf("failed to create aside_home directory: %s%n%s%n", get_aside_root(), e);
            }
        }
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

        // interact with user to get configuration property: aside_root=?
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

    public void storeProperties() {
        // store user data
        try (OutputStream out = Files.newOutputStream(configPath_full, CREATE, WRITE)) {
            properties.store(out, null);
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
    public void loadProperties1() {
        try (InputStream in = Files.newInputStream(configPath_full, CREATE, WRITE)) {
            properties.load(in);
        } catch (IOException e) {
            System.err.println("failed to read config file: " + e.getMessage());
        }
    }
}
