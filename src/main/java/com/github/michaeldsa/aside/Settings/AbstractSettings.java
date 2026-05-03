package com.github.michaeldsa.aside.Settings;

import com.github.michaeldsa.aside.Initialization.Initialize;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public abstract class AbstractSettings {
    protected Properties properties;
    protected String settingsFileName; // each subclass will manage its own file
    protected Path settingsDir = Initialize.INSTANCE.getConfigDirectory().resolve(Paths.get("settings")); // default settings dir
    protected Path settingsFile;

    public boolean settingsDirExists() {
        return Files.exists(settingsDir);
    }
    public boolean settingsFileExists() {
        return Files.exists(settingsFile);
    }
    private boolean createDirAndFile() {
        if (!settingsDirExists() || !settingsFileExists()) {
            try {
                Files.createDirectories(settingsDir);
                Files.createFile(settingsFile);
            } catch (IOException e) {
                System.out.println("AbstractSettings.createDirAndFile() IOException: " + settingsDir + " " + settingsFile);
            }
        }
        return settingsDirExists() && settingsFileExists();
    }
    protected void readFile() {
        if (settingsFileExists()) {
            try (InputStream in = Files.newInputStream(settingsFile)) {
                properties.load(in);
            } catch (IOException e) {
                System.err.println("AppSettings.readFile() IOException: " + settingsFile + " " + e.getMessage());
            }
        }
    }
    protected void writeFile() {
        if (createDirAndFile()) {
            try (OutputStream out = Files.newOutputStream(settingsFile)) {
                properties.store(out, null);
            } catch (IOException e) {
                System.err.println("AppSettings.writeFile() IOException: " + settingsFile);
            }
        }
    }


}
