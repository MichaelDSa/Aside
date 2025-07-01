package com.github.michaeldsa.aside;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
//        UIScanner scannerUI = new UIScanner();
//        scannerUI.start();

        Config config = new Config();
        config.initialize();
        System.out.println("aside_root = " + config.get_aside_root());
        System.out.println("metapath_root = " + config.get_meta_root());
        System.out.println("viewpath_root = " + config.get_view_root());

//        PathKeeper pk = PathKeeper.INSTANCE;
//        Test.metaPathNonDefaultConstructor();
//        Test.metaPathDefaultConstructor();
//        Test.metaPathValidationNoDots();
//        Test.metaPathValidationRestrictedNames(); // failed successfully
//        Test.metaPathValidationTryCatch(Paths.get(""), "empty"); // failed successfully
//        Test.metaPathValidationTryCatch(Paths.get("/"), "Slash"); // not caught.
//        Test.metaPathValidationSlashes(); // `/`, `//`, File.separator all pass. `\\` fails sucessfully.

        // test miscellaneous paths
//        Path[] miscPaths = {
//                Paths.get(".testPath"), // should be no prob.
//                Paths.get(""), // "Aside" - not allowed (restricted name).
//                Paths.get("."), // "Aside" - not allowed (restricted name)
//                Paths.get(".."), // "cli_applications" - no dot, not allowed.
//                Paths.get("../.."), // "Java" - no dot. not allowed.
//        };
//        Test.metaPathValidationPaths(miscPaths);

//        System.out.println("dot: " + Paths.get(".").toAbsolutePath().normalize());



    }
}