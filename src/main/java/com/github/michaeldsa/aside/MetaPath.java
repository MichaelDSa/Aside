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
            throw new IllegalArgumentException("invalid Path argument" + path);
        }
    }

    MetaPath(ViewPath viewPath) {
        Path candidate = qualifyAsMetaPath(viewPath);
        if(validate(candidate)){
            this.path = candidate;
        } else {
            throw new IllegalArgumentException("Invalid path argument: " + viewPath);
        }
    }

    private Path addLeadingDotToEachElement(Path path) {
        Path hasdots = Paths.get("");
        for(int i = 0; i < path.getNameCount(); i++) {
            String name = path.getName(i).toString();
            if(!name.startsWith(".")) {
                name = "." + name;
            }
            hasdots = hasdots.resolve(name);
        }
        return hasdots;
    }

    // check that each element of a path have only one
    // dot. To be used after allElementsStartWithDot()
    private boolean allElementsStartWithOnlyOneDot(Path candidate) {
        for(int i = 0; i < candidate.getNameCount(); i++){
            String name = candidate.getName(i).toString().substring(1);
            if(name.startsWith(".")) {
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
                System.out.println("starts with dot: " + candidate);
                return false;
            }
        }
        return true;
    }

    public Path getPath() {
        return path;
    }

    private Path removeXroot(Path path){
        if(path == rp.getMetapath() || path == rp.getViewpath()) {
            return path;
        }
        if(path.startsWith(rp.getMetapath())) {
            int first = rp.getMetapath().getNameCount();
            int last = path.getNameCount();
            path = path.subpath(first, last);
        } else if(path.startsWith(rp.getViewpath())) {
            int first = rp.getViewpath().getNameCount();
            int last = path.getNameCount();
            path = path.subpath(first, last);
        }
        return path;
    }

    private Path resolve_to_metapath_root(Path path) {
        String noleadingSlash = path.toString();
        String fileSeparator = FileSystems.getDefault().getSeparator();
        if(noleadingSlash.startsWith(fileSeparator)) {
            noleadingSlash = noleadingSlash.substring(fileSeparator.length());
            path = Paths.get(noleadingSlash);
        }
        return rp.getMetapath().resolve(path);
    }

    private boolean validate(Path path) {
        // validation criteria for MetaPath:
        // This validation checks elements after metapath_root.
        // elements after metapath_root must:
        //                             - have precdeding dots (they must be hidden files).
        //                             - not consist of any restricted path names.
        // All MetaPaths must start with metapath_root. If
        // a MetaPath is constructed with a path that does
        // not start with metapath_root, the path will be
        // resolved to metapath_root in the constructor.

        if(path == rp.getMetapath()) {
            return true;
        }

        Path candidate = removeXroot(path);

        return allElementsStartWithDot(candidate)
                && allElementsStartWithOnlyOneDot(candidate)
                && allElementsPassNameRestrictions(candidate);

    }

    private Path qualifyAsMetaPath(ViewPath viewPath) {
        Path path = removeXroot(viewPath.getPath());
        path = addLeadingDotToEachElement(path);
        return resolve_to_metapath_root(path);
    }

    @Override
    public String toString() {
        return path.toString();
    }
}
