package com.github.michaeldsa.aside.Ops;

import com.github.michaeldsa.aside.AsidePathElement.AsidePathElement;

public enum NewCreate implements CrudOps<AsidePathElement, AsidePathElement> {
    NEW_CATEGORY (ape -> {
       System.out.println("NewCreate.NEW_CATEGORY");
       /*
       WHAT I NEED TO UNDERSTAND ABOUT USING AsidePathElement AS THE PARAMATERIZED TYPES:
       Using the `NEW_CATEGORY` strategy:
       There are three AsidePathElement classes whose instantiations can be
       passed to NEW_CATEGORY: ImmutableNote, MutableNote and Category. Note,
        */
       return ape;
    }),
    NEW_NOTE (ape -> {
        System.out.println("NewCreate.NEW_NOTE");
        return ape;
    });

    // class boilerplate
    private final CrudOps<AsidePathElement,AsidePathElement> state;

    NewCreate(CrudOps<AsidePathElement, AsidePathElement> arg) {
        this.state = arg;
    }

    public AsidePathElement execute(AsidePathElement arg) {
        return state.execute(arg);
    }

}
