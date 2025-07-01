package com.github.michaeldsa.aside;

import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MetaPath {
    RootPaths rp = RootPaths.INSTANCE;
    private final Path path;

    MetaPath() {
        path = rp.getMetapath();
    }
    MetaPath(Path path) {

        path = path.normalize();

        if(validate(path)) {
            if (!path.startsWith(rp.getMetapath())) {
                path = rp.getMetapath().resolve(path);
            }
            this.path = path;
        } else {
            throw new IllegalArgumentException("invalid Path argument");
        }

    }
    private boolean validate(Path path) {
        // validation criteria for MetaPath:
        // 1) must start with meta_home
        // 2) all path elements after home_directory
        //    must begin with dot
        // 2) if path does not start with meta_home,
        //    all Path elements must start with a dot.
        // 3) if path does not start with meta_home,
        //    MetaPath will resolve it to meta_home.
        // 4) Path elements after meta_home must not:
        //   - have more than one dot
        //   - have the name:
        //              .meta
        //              .view
        //              .Aside_home
        //              .trash
        // 5) Path argument must first be modified
        //    to handle all relative path shorthand
        //    (`.`, `..`, `../..` etc). args must be
        //    normalized before validation.
        Path candidate;
        boolean valid = false;
        if(path.startsWith(rp.getMetapath())) {
            int first = rp.getMetapath().getNameCount() - 1;
            int last = path.getNameCount() - 1;
            candidate = path.subpath(first, last);
        } else {
            candidate = path;
        }
        if (allElementsStartWithDot(candidate)
            && allElementsHaveOnlyOneDot(candidate)
            && allElementsPassNameRestrictions(candidate)
           ) {
            valid = true;
        }
        return valid;
    }
    // check that each element of a path have only one dot
    private boolean allElementsHaveOnlyOneDot(Path candidate) {
        for(int i = 0; i < candidate.getNameCount(); i++){
            String name = candidate.getName(i).toString().substring(1);
            if(name.contains(".")) {
                return false;
            }
        }
        return true;
    }

    // check that each element of a path passes name restrictions
    private boolean allElementsPassNameRestrictions(Path candidate) {
        List<String> restrictedNames = new ArrayList<>(Arrays.asList(".meta", ".view", ".aside", ".aside_home", ".trash"));

        for(int i = 0; i < candidate.getNameCount(); i++){
            String name = candidate.getName(i).toString().toLowerCase(Locale.ENGLISH);
            if(restrictedNames.contains(name)) {
                return false;
            }
        }
        return true;
    }

    // check that each element of a path segment starts with dot
    private boolean allElementsStartWithDot(Path candidate) {
        for(int i = 0; i < candidate.getNameCount(); i++) {
            if(!candidate.getName(i).toString().startsWith(".")) {
                return false;
            }
        }
        return true;
    }

    public Path getPath() {
        return path;
    }

    @Override
    public String toString() {
        return path.toString();
    }
}
