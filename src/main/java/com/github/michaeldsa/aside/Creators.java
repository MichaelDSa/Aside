package com.github.michaeldsa.aside;

import java.io.IOException;
import java.nio.file.Files;

public class Creators {

    public static Creator<MetaPath> newCategory() {
        return mp -> {
            try {
                Files.createDirectories(mp.getPath());
                if(Files.exists(mp.getPath())){
                    Files.createDirectories(new ViewPath(mp).getPath());
                }
            } catch (IOException ex) {
                System.err.printf("unable to create directories: %s%n", mp);
            }
        };
    }
}
