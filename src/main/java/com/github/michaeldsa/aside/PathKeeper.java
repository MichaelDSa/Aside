package com.github.michaeldsa.aside;

import java.nio.file.Path;

public enum PathKeeper {
    INSTANCE;
    private final Path home;
    private Path current;
    public final PathCheck pathCheck;

    PathKeeper(){
        Config config = new Config();
        config.initialize();
        home = config.getAsideHome();
        current = home;
        pathCheck = new PathCheck();
    }

    public Path getHome() {
        return home;
    }
    public Path getCurrent() {
        return current;
    }
    public void setCurrent(Path candidate) {
        Path testPath = pathCheck.currentConvert(candidate);
        if(testPath != null) {
            current = testPath;
        }
    }



}
