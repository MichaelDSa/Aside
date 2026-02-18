package com.github.michaeldsa.aside.Ops;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePathElement.AsidePathElement;

public enum Update implements CrudOps<AsidePathElement,AsidePathElement> {



    METADATA_CONTENT_PREPEND (ape -> {
        System.out.println("METADATA_UPDATE_CONTENT_PREPEND" + ape);
        return ape;
    }),
    METADATA_CONTENT_APPEND (ape -> {
        System.out.println("METADATA_UPDATE_CONTENT_APPEND" + ape);
        return ape;
    }),
    METADATA_CONTENT_TRUNCATE (ape -> {
        System.out.println("METADATA_UPDATE_CONTENT_TRUNCATE" + ape);
        return ape;
    }),
    RESOLVE_CONTENT_META_TO_VIEW (ape -> {
        System.out.println("RESOLVE_CONTENT_META_TO_VIEW" + ape);
        return ape;
    }),
    RESOLVE_CONTENT_VIEW_TO_META (ape -> {
        System.out.println("RESOLVE_CONTENT_VIEW_TO_META" + ape);
        return ape;
    });


    // class boilerplate
    private final CrudOps<AsidePathElement,AsidePathElement> fops;

    Update(CrudOps<AsidePathElement,AsidePathElement> fops) {
        this.fops = fops;
    }

    @Override
    public AsidePathElement execute(AsidePathElement ape) {
        return fops.execute(ape);
    }

}
