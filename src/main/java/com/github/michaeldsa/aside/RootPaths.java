package com.github.michaeldsa.aside;

import java.nio.file.Path;

public enum RootPaths {
    INSTANCE;
    private final Path aside;
    private final Path metapath;
    private final Path viewpath;

    RootPaths() {
        Config config = new Config();
        config.initialize();
        aside = config.get_aside_root();
        metapath = config.get_metapath_root();
        viewpath = config.get_viewpath_root();
    }

    public Path getAside() {return aside;}
    public Path getMetapath() {return metapath;}
    public Path getViewpath() {return viewpath;}
}
