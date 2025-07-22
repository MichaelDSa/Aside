package com.github.michaeldsa.aside;

import java.nio.file.Files;

public class Category {
    MetaPath mp;
    ViewPath vp;

    Category(MetaPath mp) {
        this.mp = mp;
        this.vp = new ViewPath(mp);

        // test:
        if(areDirectories(this.mp, vp)) {
            throw new IllegalArgumentException("Invalid path argument: " + mp.getPath() + ";  " + vp.getPath());
        }
    }

    Category(ViewPath vp) {
        this.vp = vp;
        this.mp = new MetaPath(vp);

        // test:
        if(areDirectories(mp, this.vp)) {
            throw new IllegalArgumentException("Invalid path argument: " + mp.getPath() + ";  " + vp.getPath());
        }
    }

    boolean areDirectories(MetaPath mp, ViewPath vp) {
        if(!Files.isDirectory(mp.getPath()) || !Files.isDirectory(vp.getPath())) {
            return false;
        }
        return true;
    }

    // What methods do I need?
    // getMetaPath()
    // getViewPath()
    // toString()
    //       - This should print the category of the

}
