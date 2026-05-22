package com.github.michaeldsa.aside.Settings;

import com.github.michaeldsa.aside.Initialization.Initializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class TempSettings {
    private static final Properties settings = new Properties();
    private static final Path settingsFile = Initializer.INSTANCE.getConfigDirectory().resolve(Paths.get("settings"));


    static {
        if (Files.exists(settingsFile)) {
            loadProperties();
        }
    }

    // String getters:
    public static String get(String key) {
        return get(key, "");
    }
    public static String get(String key, String defaultVal) {
        return settings.getProperty(key, defaultVal);
    }

    // int getters:
    public static int getInt(String key) {
        return getInt(key, 0);
    }
    public static int getInt(String key, int defaultVal) {
        return Integer.parseInt(settings.getProperty(key, Integer.toString(defaultVal)));
    }

    // non-persisting setters
    public static void set(String key, String value) { settings.setProperty(key, value);
    }
    public static void setInt(String key, int value) {
        settings.setProperty(key, Integer.toString(value));
    }

    // set & persist
    public static void setPersist(String key, String value) {
        settings.setProperty(key, value);
        persist();
    }
    public static void setIntPersist(String key, int value) {
        settings.setProperty(key, Integer.toString(value));
        persist();
    }

    // stand-alone persist
    public static void persist() {
        if (Files.notExists(settingsFile)) {
            createSettingsFile();
        }
        try (OutputStream out = Files.newOutputStream(settingsFile)) {
            settings.store(out, "Temporary settings file");
        } catch (IOException e) {
            System.err.println(".saveSettings() IOException: " + settingsFile);
        }
    }

    private static void createSettingsFile() {
        if (Files.notExists(settingsFile)) {
            try {
                Files.createFile(settingsFile);
            } catch (IOException e) {
                System.err.println(".createSettingsFile() IOException: " + settingsFile);
            }
        }
    }

    private static void loadProperties() {
        try (InputStream in = Files.newInputStream(settingsFile)) {
            settings.load(in);
        } catch (IOException e) {
            System.err.println((".loadProperties() IOException "  + settingsFile));
        }
    }

}
