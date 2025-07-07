package com.github.michaeldsa.aside;

import java.nio.file.Path;

public enum RootPaths {
    INSTANCE;
    private final Path aside;
    private final Path metapath;
    private final Path viewpath;

    RootPaths() {
        Config config = Config.INSTANCE;
        aside = config.getAside();
        metapath = config.getMetapath();
        viewpath = config.getViewpath();
    }

    public Path getAside() {return aside;}
    public Path getMetapath() {return metapath;}
    public Path getViewpath() {return viewpath;}
}
