package com.github.michaeldsa.aside;

import com.github.michaeldsa.aside.Initialization.Initializer;

import java.nio.file.Path;

// RENAME TO: PathTracker
public enum PathKeeper {
    INSTANCE;
    private final Path home_directory;
    private final Path meta_home;
    private final Path view_home;
    private Path current;
    public final PathCheck pathCheck;

    PathKeeper(){
        Initializer initializer = Initializer.INSTANCE;
        home_directory = initializer.getAside_root();
        meta_home = home_directory.resolve(".meta");
        view_home = home_directory.resolve("view");
        current = home_directory;
        pathCheck = new PathCheck();
    }

    public Path getCurrent() {
        return current;
    }

    public Path getHome_directory() {
        return home_directory;
    }

    public Path getMeta_home() {
        return meta_home;
    }

    public Path getView_home() {
        return view_home;
    }

    public void setCurrent(Path candidate) {
        Path testPath = pathCheck.currentConvert(candidate);
        if(testPath != null) {
            current = testPath;
        }
    }

    // INSERT configureCurrentMetaPath(MetaPath meat).
    // reassigns "current_metaPath" in ~/.config/aside/config.



}
