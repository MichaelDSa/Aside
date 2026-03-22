package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.RootPaths;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class RestrictedLists {

    /*
    Instance vars need to be assigned in order of dependency priority. Least abstract to most abstract.
     */

    // AsidePath dependencies (Must be instantiated first):
    private static final ArrayList<String> restrictedMetaPathNames = new ArrayList<>(Arrays.asList(".meta", ".view", ".aside", ".aside_home"));
    private static final ArrayList<String> restrictedViewPathNames = new ArrayList<>(Arrays.asList("meta", "view", "aside_home", "aside"));

    // AsidePathElement dependencies:
    private static final String metaPathDefaultDirectory = ".DEFAULT";
    private static final String viewPathDefaultDirectory = metaPathDefaultDirectory.substring(1);
    private static final String metaPathDiscardedDirectory = ".DISCARDED";
    private static final String viewPathDiscardedDirectory = metaPathDiscardedDirectory.substring(1);
    private static final ArrayList<String> permanentDirectories = new ArrayList<>(Arrays.asList(
            metaPathDefaultDirectory, viewPathDefaultDirectory, metaPathDiscardedDirectory, viewPathDiscardedDirectory));

    // Higher level dependencies (all classes that use AsidePath & AsidePathElement subclasses):
    private static final Category defaultCategory = new Category(new MetaPath(Paths.get(metaPathDefaultDirectory)));
    private static final DiscardedElement discardedElementDirectory = new DiscardedElement();


    // getters:
    // for AsidePath subclasses and higher:
    public static ArrayList<String> getRestrictedMetaPathNames() { return restrictedMetaPathNames; }
    public static ArrayList<String> getRestrictedViewPathNames() { return restrictedViewPathNames; }

    // for AsidePathElement subclasses and higher
    public static ArrayList<String> getPermanentDirectories() { return permanentDirectories; }
    public static String getMetaPathDefaultDirectoryName() { return metaPathDefaultDirectory; }
    public static String getViewPathDefaultDirectoryName() { return viewPathDefaultDirectory; }
    public static String getMetaPathDiscardedDirectoryName() { return metaPathDiscardedDirectory; }
    public static String getViewPathDiscardedDirectoryName() { return viewPathDiscardedDirectory; }


    // for higher level classes and interfaces:
    public static Category getDefaultCategory() {return defaultCategory;}
    public static DiscardedElement getDiscardedElementDirectory() {return discardedElementDirectory;}

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