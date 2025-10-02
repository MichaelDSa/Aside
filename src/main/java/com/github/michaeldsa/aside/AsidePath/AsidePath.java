package com.github.michaeldsa.aside.AsidePath;

import com.github.michaeldsa.aside.RootPaths;

import java.nio.file.Path;
import java.util.Objects;

public abstract class AsidePath {
    protected Path path;


    // abstract classes:
    public abstract boolean startsWithRoot();

    // getters/setters:
    protected AsidePath getParent(AsidePath asidePath) {
        if (!(asidePath.getPath().equals(getMetaPathRoot()) || asidePath.getPath().equals(getViewPathRoot()))) {
            if(asidePath instanceof MetaPath) {
                return new MetaPath(asidePath.getPath().getParent());
            }
            if(asidePath instanceof ViewPath) {
                return new ViewPath(asidePath.getPath().getParent());
            }
        }
        return null;
    }

    public Path getPath(){
        return path;
    }


    protected Path removeXroot(Path path) {
        if(path.equals(getMetaPathRoot()) || path.equals(getViewPathRoot())) {
            return path;
        }
        if(path.startsWith(getMetaPathRoot())) {
            int first = getMetaPathRoot().getNameCount();
            int last = path.getNameCount();
            path = path.subpath(first, last);
        } else if(path.startsWith(getViewPathRoot())) {
            int first = getViewPathRoot().getNameCount();
            int last = path.getNameCount();
            path = path.subpath(first, last);
        }
        return path;
    }

    public boolean startsWith(AsidePath other){
        return path.startsWith(other.getPath());
    }


    public static Path getMetaPathRoot(){
        return RootPaths.INSTANCE.getMetapath();
    }
    public static Path getViewPathRoot(){
        return RootPaths.INSTANCE.getViewpath();
    }
//    public abstract boolean endsWith(AsidePath other);

    // write implementation for valdating AsidePath
//    private boolean validate(Path path) {
//        return true;
//    }

//    private MetaPath qualifyAsMetaPath(AsidePath ap){
//        return null;
//    }
//    private ViewPath qualifyAsViewPath(AsidePath ap){
//        return null;
//    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AsidePath asidePath)) return false;
        return Objects.equals(path, asidePath.path);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(path);
    }
}
