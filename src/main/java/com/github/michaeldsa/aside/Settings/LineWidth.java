package com.github.michaeldsa.aside.Settings;

import java.nio.file.Paths;
import java.util.Properties;

public class LineWidth extends AbstractSettings {
    private final String key_stdout = "stdout";
    private final String key_file = "file";

    private int stdout = 80;
    private int file = 80;

    protected LineWidth() {
        properties = new Properties();
        properties.setProperty(key_stdout, String.valueOf(stdout));
        properties.setProperty(key_file, String.valueOf(file));
        settingsFileName = "LineWidth";
        settingsFile = settingsDir.resolve(Paths.get(settingsFileName));

        if (super.settingsFileExists()) {
            super.readFile();
            stdout = Integer.parseInt(properties.getProperty(key_stdout));
            file = Integer.parseInt(properties.getProperty(key_file));
        }
    }

    public int stdout() {
        return stdout;
    }
    public int file() {
        return file;
    }

    public void setFile(int width) {
        file = width;
        properties.setProperty(key_file, String.valueOf(file));
        super.writeFile();
    }
    public void setStdout(int width) {
        stdout = width;
        properties.setProperty(key_stdout, String.valueOf(width));
        super.writeFile();
    }
}
