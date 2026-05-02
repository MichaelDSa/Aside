package com.github.michaeldsa.aside.Initialization;

import java.nio.file.Path;

public enum RootPaths {
    INSTANCE;
    private final Path aside;
    private final Path metapath;
    private final Path viewpath;

    RootPaths() {
        Initialize initialize = Initialize.INSTANCE;
        aside = initialize.getAside_root();
        metapath = initialize.getMetapath();
        viewpath = initialize.getViewpath();
    }

    public Path getAside() {return aside;}
    public Path getMetapath() {return metapath;}
    public Path getViewpath() {return viewpath;}
}
