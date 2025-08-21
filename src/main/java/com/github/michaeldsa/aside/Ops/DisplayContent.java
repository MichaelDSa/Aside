package com.github.michaeldsa.aside.Ops;

import com.github.michaeldsa.aside.AsidePath.MetaPath;

public enum DisplayContent implements DisplayOps<MetaPath> {

    METADATA_ALL (m -> {
        System.out.println("DisplayList.METADATA_ALL");
        return m;
    }),
    METADATA_TITLE (m -> {
        System.out.println("DisplayList.METADATA_TITLE");
        return m;
    }),
    METADATA_TO (m -> {
        System.out.println("DisplayList.METADATA_TO");
        return m;
    }),
    METADATA_FROM (m -> {
        System.out.println("DisplayList.METADATA_FROM");
        return m;
    }),
    METADATA_TAGS (m -> {
        System.out.println("DisplayList.METADATA_TAGS");
        return m;
    }),
    METADATA_CONTENT (m -> {
        System.out.println("DisplayList.METADATA_CONTENT");
        return m;
    });





    // class boilerplate
    private final DisplayOps<MetaPath> dops;

    DisplayContent(DisplayOps<MetaPath> dops) {
        this.dops = dops;
    }

    public MetaPath execute(MetaPath m) {
        return dops.execute(m);
    }
}
