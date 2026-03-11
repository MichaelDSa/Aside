package com.github.michaeldsa.aside.AsidePath;

import com.github.michaeldsa.aside.AsidePathElement.RestrictedLists;
import com.github.michaeldsa.aside.RootPaths;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewPath extends AsidePath {
    // defined in superclass
//    RootPaths rp = RootPaths.INSTANCE;
//    private final Path path;

    public ViewPath() {
        path = getViewPathRoot();
    }

    public ViewPath(Path path) {
        path = path.normalize();
        if(validate(path)) {
            // Should ViewPath objects always start with viewpath_root?
            if(!path.startsWith(getViewPathRoot())) {
                path = resolve_to_viewpath_root(path);
            }
            this.path = path;
        } else {
            throw new IllegalArgumentException("Invalid path argument: " + path);
        }
    }

    public ViewPath(MetaPath metaPath) {
        Path candidate = qualifyAsViewPath(metaPath);
//        System.out.println("candidate:" + candidate);
        if(validate(candidate)){
            this.path = candidate;
        } else {
            throw new IllegalArgumentException("Invalid path argument: " + metaPath);
        }
    }

    // getters/setters:
    public ViewPath getFileName() {
        return (ViewPath) super.getFileName(this);
    }
    public ViewPath getParent() {
        return (ViewPath) super.getParent(this);
    }


    private boolean allElementsPassNameRestrictions(Path candidate) {
//        List<String> restrictedNames = new ArrayList<>(Arrays.asList("meta", "view", "aside_home", "aside", "trash"));
        List<String> restrictedNames = RestrictedLists.getRestrictedViewPathNames();
        for(int i = 0; i < candidate.getNameCount(); i++){
            String name = candidate.getName(i).toString().toLowerCase();
            if(restrictedNames.contains(name)){
                return false;
            }
        }
        return true;
    }

    private boolean elementsDoNotStartWithDot(Path candidate){
        for(int i = 0; i < candidate.getNameCount(); i++){
            if(candidate.getName(i).toString().startsWith(".")){
                return false;
            }
        }
        return true;
    }


    private Path removeLeadingDotsFromElements(Path path) {
        Path nodots = Paths.get("");
        for (int i = 0; i < path.getNameCount(); i++) {
            String name = path.getName(i).toString();
            if(name.startsWith(".")){
                name = name.substring(1);
            }
            nodots = nodots.resolve(name);
        }
        return nodots;
    }

    // implemented in superlcass
//    private Path removeXroot(Path path){
//        if(path == rp.getViewpath() || path == rp.getMetapath()){
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

    @Override
    public boolean startsWithRoot() {
        return path.startsWith(getViewPathRoot());
    }

    public ViewPath resolve(ViewPath other) {
        Path this_path = removeXroot(path);
        Path other_path = removeXroot(other.getPath());
        if(other_path.equals(getViewPathRoot())) {
            return this;
        }
        Path resolved_path = this_path.resolve(other_path);
        return new ViewPath(resolved_path);
    }



    private Path resolve_to_viewpath_root(Path path) {
        String noLeadingSlash = path.toString();
        String fileSeparator = FileSystems.getDefault().getSeparator();
        if(noLeadingSlash.startsWith(fileSeparator)){
            noLeadingSlash = noLeadingSlash.substring(fileSeparator.length());
            path = Paths.get(noLeadingSlash);
        }
        return getViewPathRoot().resolve(path);
    }

    private boolean validate(Path path) {
        // validation criteria for ViewPath:
        // This validation checks elements after viewpath_root.
        // elements after viewpath_root must:
        //                             - not have preceding dots
        //                             - not consist of any restricted path names
        // All ViewPaths must start with viewpath_root. If
        // a ViewPath is constructed with a path that does
        // not start with viewpath_root, the path will be
        // resolved to viewpath_root in the constructor.

        if(path.equals(getViewPathRoot())) {
            return true;
        }
        Path candidate = removeXroot(path);

        return elementsDoNotStartWithDot(candidate) && allElementsPassNameRestrictions(candidate);
    }

    private Path qualifyAsViewPath(MetaPath metaPath) {
        if(metaPath.getPath().equals(getMetaPathRoot())){
//            System.out.println("metaPath equals root");
            return getViewPathRoot();
        }
        Path path = removeXroot(metaPath.getPath());
        path = removeLeadingDotsFromElements(path);
        return resolve_to_viewpath_root(path);
    }

    @Override
    public String toString() { return path.toString(); }
}































