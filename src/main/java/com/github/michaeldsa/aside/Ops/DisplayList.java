package com.github.michaeldsa.aside.Ops;

import com.github.michaeldsa.aside.AsidePath.MetaPath;

public enum DisplayList implements DisplayOps<MetaPath,MetaPath> {

    CATEGORY (m -> {
        System.out.println("DisplayList.CATEGORY");
        return m;
    }),
    CATEGORIES (m -> {
        System.out.println("DisplayList.CATEGORIES");
        return m;
    }),
    NOTE (m -> {
        System.out.println("DisplayList.NOTE");
        return m;
    }),
    NOTES (m -> {
        System.out.println("DisplayList.NOTES");
        return m;
    }),
    ALL_IN_CATEGORY (m -> {
        System.out.println("DisplayList.ALL_IN_CATEGORY");
        return m;
    }),
    ALL_RECURSIVE (m -> {
        System.out.println("DisplayList.ALL_RECURSIVE");
        return m;
    });

    // class boilerplate
    private final DisplayOps<MetaPath,MetaPath> dops;

    DisplayList(DisplayOps<MetaPath,MetaPath> dops) {
        this.dops = dops;
    }

    public MetaPath execute(MetaPath m) {
        return dops.execute(m);
    }
}
