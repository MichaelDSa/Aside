package com.github.michaeldsa.aside;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;

import java.nio.file.Path;
import java.nio.file.Paths;

public class SearchFor {

    private SearchFor () {}

    static Search userHomeDirectories() {
        return new UserHomeDirectories();
    }

    static Search mockHomeUserDirectories() {
        return new MockHomeUserDirectories();
    }

    static Search categoryFromParent(MetaPath parent) {
        return new CategoryFromParent(parent.getPath());
    }
    static Search categoryFromParent(ViewPath parent) {
        return categoryFromParent(new MetaPath(parent));
    }
    static Search categoryFromParent(Path parent) {
        return categoryFromParent(new MetaPath(parent));
    }
    static Search categoryFromParent(String parent) {
        return categoryFromParent(new MetaPath(Paths.get(parent)));
    }

    static Search categoryFromRoot() {
        return new CategoryFromRoot();
    }

}
