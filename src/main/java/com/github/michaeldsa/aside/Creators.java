package com.github.michaeldsa.aside;

import java.io.IOException;
import java.nio.file.Files;

public enum Creators implements Creator<MetaPath>{
    NEW_CATEGORY {
        @Override
        public void create(MetaPath mp) {
            try {
                Files.createDirectories(mp.getPath());
                if(Files.exists(mp.getPath())) {
                    Files.createDirectories(new ViewPath(mp).getPath());
                }
            } catch (IOException ex) {
                System.err.printf("unable to create directories: %s%n", mp);
            }
        }
    },
    NEW_NOTE {
        @Override
        public void create(MetaPath metaPath) {

        }
    }
}
