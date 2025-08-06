package com.github.michaeldsa.aside;

import java.nio.file.Paths;

// This is the Create factory class, that returns reciever actions.
// The Recievers are replaced with Lambdas which use the functional interfaces:
// MakeNew<T>,
// Predefined MakeNew<T> lambdas can be found in Creators.java.
public class Create {


    private Create(){}

    public static <T> void inCurrent(Creator<MetaPath> newCategory, T t) {
        CurrentCategory cc = CurrentCategory.INSTANCE;
        MetaPath metaPath = asMetaPath(t);

        if(metaPath != null) {
            if(!metaPath.startsWith(cc.getCurrentMetaPath())) {
                metaPath = cc.getCurrentMetaPath().resolve(metaPath);
            }
            newCategory.create(metaPath);
        } else {
            System.err.printf("newCategory(T t): invalid type arg. must be MetaPath, ViewPath, or String. arg type: %s%n", t.getClass());
        }
    }

    public static <T, U> void inOther(Creator<MetaPath> newCategory, T parent, U child) {
        CurrentCategory cc = CurrentCategory.INSTANCE;
        MetaPath mpFirst = asMetaPath(parent);
        MetaPath mpSecond = asMetaPath(child);
        mpFirst = cc.getCurrentMetaPath().resolve(mpFirst);
        // if T is a String, search for parent

    }
    private static <T> MetaPath asMetaPath(T t) {
        return switch (t) {
            case MetaPath mp ->
                mp;
            case ViewPath vp ->
                new MetaPath(vp.getPath());
            case String s ->
                new MetaPath(Paths.get(s));
            default -> null;
        };
    }

}
