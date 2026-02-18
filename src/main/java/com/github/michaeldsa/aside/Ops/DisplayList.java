package com.github.michaeldsa.aside.Ops;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePathElement.AsidePathElement;

public enum DisplayList implements DisplayOps<AsidePathElement, AsidePathElement> {

    CATEGORY (ape -> {
        System.out.println("DisplayList.CATEGORY");
        return ape;
    }),
    CATEGORIES (ape -> {
        System.out.println("DisplayList.CATEGORIES");
        return ape;
    }),
    NOTE (ape -> {
        System.out.println("DisplayList.NOTE");
        return ape;
    }),
    NOTES (ape -> {
        System.out.println("DisplayList.NOTES");
        return ape;
    }),
    ALL_IN_CATEGORY (ape -> {
        System.out.println("DisplayList.ALL_IN_CATEGORY");
        return ape;
    }),
    ALL_RECURSIVE ( ape-> {
        System.out.println("DisplayList.ALL_RECURSIVE");
        return ape;
    });

    // class boilerplate
    private final DisplayOps<AsidePathElement, AsidePathElement> dops;

    DisplayList(DisplayOps<AsidePathElement, AsidePathElement> dops) {
        this.dops = dops;
    }

    public AsidePathElement execute(AsidePathElement ape) {
        return dops.execute(ape);
    }
}
