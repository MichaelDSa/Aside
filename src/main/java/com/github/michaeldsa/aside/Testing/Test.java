package com.github.michaeldsa.aside.Testing;
// I don't have time to learn unit testing,
// so I'm writing this class to test stuff.

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.AsidePathElement.Category;
import com.github.michaeldsa.aside.AsidePathElement.ImmutableNote;
import com.github.michaeldsa.aside.AsidePathElement.MutableNote;
import com.github.michaeldsa.aside.CurrentCategory;
import com.github.michaeldsa.aside.PathKeeper;
import com.github.michaeldsa.aside.Validation.ValidatePath;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Test {
    public static PathKeeper pk = PathKeeper.INSTANCE;
    public static CurrentCategory cc = CurrentCategory.INSTANCE;




    public static void numberOfPathElements(){
        System.out.printf("Number of elements in `home_directory`: %s %s%n", pk.getHome_directory().getNameCount(), pk.getHome_directory() );
        System.out.printf("Number of elements in      `meta_home`: %s %s%n", pk.getMeta_home().getNameCount(), pk.getMeta_home() );
    }

    public static void metaPathDefaultConstructor() {
        MetaPath home = new MetaPath();
        System.out.println(home);
    }

    public static void metaPathNonDefaultConstructor() {
        MetaPath noHome = new MetaPath(Paths.get(".one", ".two"));
        System.out.println(noHome);
    }

    public static void metaPathValidationNoDots() {
        metaPathValidationTryCatch(Paths.get("one", "two"), "element names have no dots");
    }

    public static void metaPathValidationRestrictedNames(String ...addString) {
        String[] array = {
                ".meta",
                ".META",
                ".view",
                ".VIEW",
                ".ASIDE_HOME",
                ".aside_home",
                ".Aside_home",
                ".aSide_home",
                ".trash",
                ".TRASH"
        };
        List<String> names = Arrays.asList(array);
        names.addAll(Arrays.asList(addString));
        for(String name : names){
            metaPathValidationTryCatch(Paths.get(name), "element may not be named " + name);
        }
    }

    public static void metaPathValidationSlashes() {
        Path fwdSl = Paths.get("/");
        Path doubleFw = Paths.get("//");
        Path bckSl =  Paths.get("\\");
        Path sysSl = Paths.get(File.separator);
        Path[] paths = {fwdSl, doubleFw, bckSl, sysSl};
        int num = 0;
        for(Path p : paths){
            metaPathValidationTryCatch(p, num++ + p.toString());
        }
    }

    public static void metaPathValidationPaths(Path ...paths) {
        for(int i = 0; i < paths.length; i++){
            metaPathValidationTryCatch(paths[i], i + " " + paths[i].toAbsolutePath().normalize());
        }
    }

    public static void metaPathValidationTryCatch(Path path, String msg) {
        try {
            MetaPath illegal = new MetaPath(path);
        } catch (IllegalArgumentException e) {
            System.err.println("Caught Exception: IllegalArgumentException. " + msg);
            return;
        }
        System.out.println("IllegalArgumentException not caught. " + msg);
    }

    public static boolean validatePath_CN(Path path) {
        return ValidatePath.CATEGORY_OR_NOTE_NAME_SUBMISSION.test(path);
    }
    public static boolean validatePath_C(Path path) {
        return ValidatePath.CATEGORY_NAME_SUBMISSION.test(path);
    }
    public static boolean validatePath_N(Path path) {
        return ValidatePath.NOTE_NAME_SUBMISSION.test(path);
    }



}