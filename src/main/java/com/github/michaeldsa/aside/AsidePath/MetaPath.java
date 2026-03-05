package com.github.michaeldsa.aside.AsidePath;

import com.github.michaeldsa.aside.AsidePathElement.RestrictedLists;
import com.github.michaeldsa.aside.RootPaths;

import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MetaPath extends AsidePath{
    // defined in superclass
//    RootPaths rp = RootPaths.INSTANCE;
//    private final Path path;

    public MetaPath() {
        path = getMetaPathRoot();
    }

    public MetaPath(Path path) {
        path = path.normalize();
        if(validate(path)) {
            // Should MetaPath objects always start with metapath_root?
            if (!path.startsWith(getMetaPathRoot())) {
                path = getMetaPathRoot().resolve(path);
            }
            this.path = path;
        } else {
            System.out.println("IllegalArgumentException: " + path);
            throw new IllegalArgumentException("invalid Path argument " + path);
        }
    }

    public MetaPath(ViewPath viewPath) {
        Path candidate = qualifyAsMetaPath(viewPath);
        if(validate(candidate)){
            this.path = candidate;
        } else {
            throw new IllegalArgumentException("Invalid path argument: " + viewPath);
        }
    }

    // getters/setters:
    public MetaPath getParent() {
        return (MetaPath) super.getParent(this);
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
//        List<String> restrictedNames = new ArrayList<>(Arrays.asList(".meta", ".view", ".aside", ".aside_home", ".trash"));
        List<String> restrictedNames = RestrictedLists.getRestrictedMetaPathNames();
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
    // implemented in superclass
//    @Override
//    public Path getPath() {
//        return path;
//    }

    // implemented in superclass
//    private Path removeXroot(Path path){
//        if(path == rp.getMetapath() || path == rp.getViewpath()) {
//            return path;
//        }
//        if(path.startsWith(rp.getMetapath())) {
//            int first = rp.getMetapath().getNameCount();
//            int last = path.getNameCount();
//            path = path.subpath(first, last);
//        } else if(path.startsWith(rp.getViewpath())) {
//            int first = rp.getViewpath().getNameCount();
//            int last = path.getNameCount();
//            path = path.subpath(first, last);
//        }
//        return path;
//    }

    public MetaPath resolve(MetaPath other) {
        Path this_path = removeXroot(path);
        Path other_path = removeXroot(other.getPath());
        if(other_path.equals(getMetaPathRoot())) {
            return this;
        }
        Path resolved_path = this_path.resolve(other_path);

        return new MetaPath(resolved_path);
    }

    private Path resolve_to_metapath_root(Path path) {
        String noleadingSlash = path.toString();
        String fileSeparator = FileSystems.getDefault().getSeparator();
        if(noleadingSlash.startsWith(fileSeparator)) {
            noleadingSlash = noleadingSlash.substring(fileSeparator.length());
            path = Paths.get(noleadingSlash);
        }
        return getMetaPathRoot().resolve(path);
    }

    // implemented in superclass
//    public boolean startsWith(AsidePath other) {
//        return path.startsWith(other.getPath());
//    }
    public boolean startsWithRoot() {
        return path.startsWith(getMetaPathRoot());
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

        if(path.equals(getMetaPathRoot())) {
            return true;
        }

        Path candidate = removeXroot(path);

        return allElementsStartWithDot(candidate)
                && allElementsStartWithOnlyOneDot(candidate)
                && allElementsPassNameRestrictions(candidate);

    }

    private Path qualifyAsMetaPath(ViewPath viewPath) {
        if(viewPath.getPath().equals(getViewPathRoot())) {
            return getMetaPathRoot();
        }
        Path path = removeXroot(viewPath.getPath());
        path = addLeadingDotToEachElement(path);
        return resolve_to_metapath_root(path);
    }

    @Override
    public String toString() {
        return path.toString();
    }
}
