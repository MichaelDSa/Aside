package com.github.michaeldsa.aside.Ops;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePathElement.AsidePathElement;

import java.util.List;

public enum Retrieve implements CrudOps<AsidePathElement, List<AsidePathElement>> {

    CONTENTS_OF_CATEGORY  (ape -> {
        System.out.println("Retrieve.CONTENTS_OF_CATEGORY");
        return List.of();
    }),
    CONTENTS_OF_CATEGORY_RECURSIVE(ape -> {
        System.out.println("Retrieve.CONTENTS_OF_CATEGORY_RECURSIVE");
        return List.of();
    }),
    CATEGORIES_OF_CATEGORY (ape -> {
        System.out.println("Retrieve.CATEGORIES_OF_CATEGORY");
        return List.of();
    }),
    CATEGORIES_OF_CATEGORY_RECURSIVE (ape -> {
        System.out.println("Retrieve.CATEGORIES_OF_CATEGORY_RECURSIVE");
        return List.of();
    }),
    NOTES_OF_CATEGORY (ape -> {
        System.out.println("Retrieve.NOTES_OF_CATEGORY");
        return List.of();
    }),
    NOTES_OF_CATEGORY_RECURSIVE (ape -> {
        System.out.println("Retrieve.NOTES_OF_CATEGORY_RECURSIVE");
        return List.of();
    });

    // class boilerplate
    private final CrudOps<AsidePathElement, List<AsidePathElement>> rops;

    Retrieve(CrudOps<AsidePathElement,List<AsidePathElement>> rops) {
        this.rops = rops;
    }
    @Override
    public List<AsidePathElement> execute(AsidePathElement ape) {
        return rops.execute(ape);
    }
}
