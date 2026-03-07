package com.github.michaeldsa.aside.AsidePathElement;

import java.util.ArrayList;
import java.util.Arrays;

public class RestrictedLists {

    private static final ArrayList<String> restrictedMetaPathNames = new ArrayList<>(Arrays.asList(".meta", ".view", ".aside", ".aside_home"));
    private static final ArrayList<String> restrictedViewPathNames = new ArrayList<>(Arrays.asList("meta", "view", "aside_home", "aside"));
    private static final ArrayList<String> permanentDirectories = new ArrayList<>(Arrays.asList( "default", ".default", "trash", ".trash" ));
    public static ArrayList<String> getRestrictedMetaPathNames() { return restrictedMetaPathNames; }
    public static ArrayList<String> getRestrictedViewPathNames() { return restrictedViewPathNames; }
    public static ArrayList<String> getPermanentDirectories() {
        // this must be used with .toString().toLowerCase()
        return permanentDirectories;
    }
}