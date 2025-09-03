package com.github.michaeldsa.aside.AsidePath;

import com.github.michaeldsa.aside.RootPaths;

import java.nio.file.Path;

public abstract class AsidePath {
    protected RootPaths rp;
    protected Path path;


    public Path getPath(){
        return path;
    }

    protected Path removeXroot(Path path) {
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

    public boolean startsWith(AsidePath other){
        return path.startsWith(other.getPath());
    }

    public abstract boolean startsWithRoot();

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





}
