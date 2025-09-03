package com.github.michaeldsa.aside.Ops;

import com.github.michaeldsa.aside.AsidePath.MetaPath;

import java.util.List;

public enum Retrieve implements CrudOps<MetaPath, List<String>> {

    CONTENTS_OF_CATEGORY  (m -> {
        System.out.println("Retrieve.CONTENTS_OF_CATEGORY");
        return List.of();
    }),
    CONTENTS_OF_CATEGORY_RECURSIVE(m -> {
        System.out.println("Retrieve.CONTENTS_OF_CATEGORY_RECURSIVE");
        return List.of();
    }),
    CATEGORIES_OF_CATEGORY (m -> {
        System.out.println("Retrieve.CATEGORIES_OF_CATEGORY");
        return List.of();
    }),
    CATEGORIES_OF_CATEGORY_RECURSIVE (m -> {
        System.out.println("Retrieve.CATEGORIES_OF_CATEGORY_RECURSIVE");
        return List.of();
    }),
    NOTES_OF_CATEGORY (m -> {
        System.out.println("Retrieve.NOTES_OF_CATEGORY");
        return List.of();
    }),
    NOTES_OF_CATEGORY_RECURSIVE (m -> {
        System.out.println("Retrieve.NOTES_OF_CATEGORY_RECURSIVE");
        return List.of();
    });

    // class boilerplate
    private final CrudOps<MetaPath, List<String>> rops;

    Retrieve(CrudOps<MetaPath,List<String>> rops) {
        this.rops = rops;
    }
    @Override
    public List<String> execute(MetaPath metaPath) {
        return rops.execute(metaPath);
    }
}
