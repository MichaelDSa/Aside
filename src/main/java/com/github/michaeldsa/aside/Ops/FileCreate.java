package com.github.michaeldsa.aside.Ops;

import com.github.michaeldsa.aside.AsidePath.MetaPath;

public enum FileCreate implements FileOps<MetaPath> {

    NEW_CATEGORY (m -> {
        System.out.println("Create.NEW_CATEGORY");
        return m;
    }),
    NEW_NOTE (m -> {
        System.out.println("Create.NEW_NOTE");
        return m;
    });

    // class boilerplate:
    private final FileOps<MetaPath> fops;

    FileCreate(FileOps<MetaPath> fops) {
        this.fops = fops;
    }

    public MetaPath execute(MetaPath m) {
        return fops.execute(m);
    }
}
