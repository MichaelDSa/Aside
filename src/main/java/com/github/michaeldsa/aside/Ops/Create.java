package com.github.michaeldsa.aside.Ops;

import com.github.michaeldsa.aside.AsidePathElement.AsidePathElement;

public enum Create implements CrudOps<AsidePathElement, AsidePathElement> {

    NEW_CATEGORY ( ape-> {
        System.out.println("Create.NEW_CATEGORY");
        // if the ape is not a Category type, the alg should get the parent of the file.
        return ape;
    }),
    NEW_NOTE (ape -> {
        System.out.println("Create.NEW_NOTE");
        return ape;
    });

    // class boilerplate:
    private final CrudOps<AsidePathElement,AsidePathElement> fops;

    Create(CrudOps<AsidePathElement,AsidePathElement> fops) {
        this.fops = fops;
    }

    public AsidePathElement execute(AsidePathElement ape) {
        return fops.execute(ape);
    }
}
