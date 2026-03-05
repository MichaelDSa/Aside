package com.github.michaeldsa.aside.Search;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;

import java.nio.file.Path;
import java.nio.file.Paths;

public class SearchFor {

    private SearchFor () {}

    public static Search userHomeDirectories() {
        return new UserHomeDirectories();
    }

    public static Search mockHomeUserDirectories() {
        return new MockHomeUserDirectories();
    }

    public static Search categoryFromParent(MetaPath parent) {
        return new CategoryFromParent(parent.getPath());
    }
    public static Search categoryFromParent(ViewPath parent) {
        return categoryFromParent(new MetaPath(parent));
    }
    public static Search categoryFromParent(Path parent) {
        return categoryFromParent(new MetaPath(parent));
    }
    public static Search categoryFromParent(String parent) {
        return categoryFromParent(new MetaPath(Paths.get(parent)));
    }

    public static Search categoryFromRoot() {
        return new CategoryFromRoot();
    }

}
