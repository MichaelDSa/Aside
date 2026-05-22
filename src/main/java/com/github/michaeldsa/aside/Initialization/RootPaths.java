package com.github.michaeldsa.aside.Initialization;

import java.nio.file.Path;

public enum RootPaths {
    INSTANCE;
    private final Path aside;
    private final Path metapath;
    private final Path viewpath;

    RootPaths() {
        Initializer initializer = Initializer.INSTANCE;
        aside = initializer.getAside_root();
        metapath = initializer.getMetapath();
        viewpath = initializer.getViewpath();
    }

    public Path getAside() {return aside;}
    public Path getMetapath() {return metapath;}
    public Path getViewpath() {return viewpath;}
}
