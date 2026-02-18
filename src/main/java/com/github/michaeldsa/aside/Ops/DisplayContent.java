package com.github.michaeldsa.aside.Ops;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePathElement.AsidePathElement;

public enum DisplayContent implements DisplayOps<AsidePathElement, AsidePathElement> {

    METADATA_ALL (ape -> {
        System.out.println("DisplayList.METADATA_ALL");
        return ape;
    }),
    METADATA_TITLE (ape -> {
        System.out.println("DisplayList.METADATA_TITLE");
        return ape;
    }),
    METADATA_TO (ape -> {
        System.out.println("DisplayList.METADATA_TO");
        return ape;
    }),
    METADATA_FROM (ape -> {
        System.out.println("DisplayList.METADATA_FROM");
        return ape;
    }),
    METADATA_TAGS (ape -> {
        System.out.println("DisplayList.METADATA_TAGS");
        return ape;
    }),
    METADATA_CONTENT (ape -> {
        System.out.println("DisplayList.METADATA_CONTENT");
        return ape;
    });





    // class boilerplate
    private final DisplayOps<AsidePathElement, AsidePathElement> dops;

    DisplayContent(DisplayOps<AsidePathElement, AsidePathElement> dops) {
        this.dops = dops;
    }

    public AsidePathElement execute(AsidePathElement ape) {
        return dops.execute(ape);
    }
}
