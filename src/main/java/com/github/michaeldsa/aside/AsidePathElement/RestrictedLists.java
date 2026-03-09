package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.RootPaths;

import java.nio.file.Path;
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

    // utility methods:
    public static boolean isPermanentDirectory(Path dir) {
        RootPaths rp = RootPaths.INSTANCE;
        Path mpr = rp.getMetapath();
        Path vpr = rp.getViewpath();
        // permanent directories are immediate children of mpr/vpr.
        if (dir.getNameCount() != mpr.getNameCount() + 1
                && dir.getNameCount() != vpr.getNameCount() + 1) {
            return false;
        }

        boolean identical = false;
        String arg = dir.toString().toLowerCase();
        for (String s : RestrictedLists.getPermanentDirectories()) {
            String perm;
            if (s.startsWith(".")) {
                perm = mpr.resolve(s).toString().toLowerCase();
            } else {
                perm = vpr.resolve(s).toString().toLowerCase();
            }
            if (arg.equals(perm)) {
                identical = true;
                break;
            }
        }

        return identical;
    }
}