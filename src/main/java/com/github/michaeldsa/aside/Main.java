package com.github.michaeldsa.aside;

import jdk.swing.interop.SwingInterOpUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
//        UIScanner scannerUI = new UIScanner();
//        scannerUI.start();

//        Config config = Config.INSTANCE;
//        System.out.println("aside_root = " + config.getAside());
//        System.out.println("metapath_root = " + config.getMetapath());
//        System.out.println("viewpath_root = " + config.getViewpath());
//        System.out.println("Successful initialization: " + config.isSuccess_initialization());
//        Prnt.width80ch("width80ch works!");
//        Prnt.width("Prnt.width() works!", 30);
//        if (!config.isSuccess_initialization()) {
//            System.exit(1);
//        }

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


        Path longpath = Paths.get("/","zero","one", "two", "three", "four", "five");

        Path shortpath = Paths.get("zero", "one", "two");

        int first = shortpath.getNameCount();
        int last = longpath.getNameCount();

        System.out.println("EXPECTATION: zero/one/two |RESULT: " + longpath.subpath(0, first));
        System.out.println("remaining elements. EXPECTATION: three/four/five |RESULT: " + longpath.subpath(first, last));
        System.out.println("index farthest from root (path.getName(-1)). EXPECTATION: five. |RESULT: " + longpath.getName(longpath.getNameCount() -1));

        System.out.println("\nPath elements as strings:");
        System.out.println("Path longpath: " + longpath + " no. of elements: " + longpath.getNameCount());
        System.out.println("Path shortpath: " + shortpath + " no. of elements: " + shortpath.getNameCount());
        for(int i = 0; i < last; i++) {
            String name = longpath.getName(i).toString();
            System.out.println(name);
        }

        Path shortlong = shortpath.resolve(longpath);
        Path longshort = longpath.resolve(shortpath);
        System.out.println("shortlong: " + shortlong);
        System.out.println("longshort: " + longshort);


        Path divert = Paths.get("/","zero","one","apple","banana");
        Path longdivert = longpath.resolve(divert);
        System.out.println("divert: " + divert);
        System.out.println("longpath: " + longpath);
        System.out.println("longdivert: " + longdivert);

        MetaPath meta = new MetaPath();
        System.out.println("meta: " + meta);

        Path forMetaPath = Paths.get(".one",".two",".three",".four",".five");

        MetaPath metatest = new MetaPath(forMetaPath);

        System.out.println("metaLongpath: " + metatest);

        Path metaroot = meta.getPath();
        MetaPath metatest2 = new MetaPath(metaroot.resolve(forMetaPath));
        System.out.println("metatest2: " + metatest2);

        Path nodots = Paths.get("");
        System.out.println("nodots: " + nodots);
        for(int i = 0; i < forMetaPath.getNameCount(); i++) {
            nodots = nodots.resolve(forMetaPath.getName(i).toString().substring(1));
        }
        System.out.println("nodots: " + nodots);

        // ViewPath tests:
        // new ViewPath(MetaPath)
        ViewPath vp = new ViewPath(metatest2);
        System.out.println("vp: " + vp);

        // new ViewPath(Path)
        ViewPath vp2 = new ViewPath(divert);
        System.out.println("vp2: " + vp2);

        // new ViewPath()
        ViewPath vp3 = new ViewPath();
        System.out.println("vp3: " + vp3);

        // Test subpath: what happens when .subpath(0, path.getNameCount())?
        System.out.println(".subpath(0, path.getNameCount())");
        // System.out.println(vp3.getPath().subpath(vp3.getPath().getNameCount(), vp3.getPath().getNameCount()));
        // exception thrown.

        // Test new MetaPath(ViewPath)
        MetaPath mp2 = new MetaPath(vp2);
        System.out.println("vp2: " + vp2);
        System.out.println("mp2: " + mp2);

    }
}