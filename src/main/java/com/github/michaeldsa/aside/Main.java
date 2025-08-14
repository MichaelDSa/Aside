package com.github.michaeldsa.aside;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.github.michaeldsa.aside.Create.Alg.*;
import static com.github.michaeldsa.aside.Deleters.CATEGORY_TEST;

public class Main {
    public static void main(String[] args) {
        CurrentCategory cc = CurrentCategory.INSTANCE;
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

        // Test resolve method on MetaPaths:
        RootPaths rp = RootPaths.INSTANCE;
        Path meta_root = rp.getMetapath();
//        Path cities = meta_root.resolve(Paths.get(".Vancouver", ".London", ".Toronto", ".New_York", ".Montreal", ".Ottawa"));
//        Path fruits = meta_root.resolve(Paths.get(".Apple", ".Banana", ".Pear", ".Fig", ".Grape", ".Lemon"));
        Path cities = Paths.get(".Vancouver", ".London", ".Paris", ".New York", ".Toronto", ".Montreal", ".Ottawa");
        Path fruits = Paths.get(".Apple", ".Banana", ".Pear", ".Fig", ".Grape", ".Lemon");
        // regulate paths:
        MetaPath meta_cities = new MetaPath(cities);
        MetaPath meta_fruits = new MetaPath(fruits);

        // resolve fruits to cities. See what happens:
        Path path_resolve = meta_cities.getPath().resolve(meta_fruits.getPath());
        MetaPath meta_resolve = new MetaPath(path_resolve);
        MetaPath meta_join = meta_cities.resolve(meta_fruits);

        // resolve cities to fruits: see what happens:
        Path path_resolve2 = meta_fruits.getPath().resolve(meta_cities.getPath());
        MetaPath meta_resolve2 = new MetaPath(path_resolve2);
        MetaPath meta_join2 = meta_fruits.resolve(meta_cities);

        // print results:
        System.out.println();
        System.out.printf("Path cities: %s\n", cities);
        System.out.printf("MetaPath meta_cities: %s\n", meta_cities);

        System.out.println();
        System.out.printf("Path fruits: %s\n", fruits);
        System.out.printf("MetaPath meta_fruits %s\n", meta_fruits);

        System.out.println();
        System.out.printf("Path path_resolve %s\n", path_resolve);
        System.out.printf("MetaPath meta_resolve %s\n", meta_resolve);
        System.out.printf("MetaPath meta_join %s\n", meta_join);
        System.out.printf("Path path_resolve2 %s\n", path_resolve2);
        System.out.printf("MetaPath meta_resolve2 %s \n", meta_resolve2);
        System.out.printf("MetaPath meta_join2 %s\n", meta_join2);

        // test slash
        Path leading_slash = Paths.get("/", "element", "element1");
        System.out.println("leading_slash: " + leading_slash);
        System.out.println("element list:");
        for(Path e : leading_slash) {
            System.out.println(e.toString());
        }
        Path no_leading_slash = Paths.get("element", "element1");
        System.out.println("no_leading_slash: " + no_leading_slash);
        System.out.println("element list:");
        for(Path e : no_leading_slash) {
            System.out.println(e.toString());
        }

        // test resolve method:
        Path animals = Paths.get("alpaca", "bat", "cat", "dog", "elephant", "ferret");
        Path animals1 = animals.resolve(Paths.get("alpaca",  "beetle", "caterpillar"));
        Path animals2 = animals1.resolve(animals);
        Path animals3 = meta_root.resolve(animals);
        Path animals4 = meta_root.resolve(animals1);
        Path animals5 = animals3.resolve(animals4);
        Path animals6 = animals5.resolve(Paths.get("whale", "cougar", "dinosaur"));

        System.out.println("animals: " + animals);
        System.out.println("animals1: " + animals1);
        System.out.println("animals2: " + animals2);
        System.out.println("animals3: " + animals3);
        System.out.println("animals4: " + animals4);
        System.out.println("animals5: " + animals5);
        System.out.println("animals6: " + animals6);
        // CONCLUSION: Slash at beginning of path replaces the path it resolves to.
        //             A Path that starts with a slash can't have parents.

        // rest resolve one is empty path:
        Path empty = Paths.get("");
        Path empty1 = Paths.get("");
        Path empty_resolve1 = empty.resolve(meta_root);
        Path empty_resolve2 = meta_root.resolve(empty);
        Path empty_resolve3 = empty.resolve(animals);
        Path empty_resolve4 = animals.resolve(empty);
        Path empty_resolve5 = empty.resolve(empty1);
        System.out.println("empty_resolve1: " + empty_resolve1);
        System.out.println("empty_resolve2: " + empty_resolve2);
        System.out.println("empty_resolve3: " + empty_resolve3);
        System.out.println("empty_resolve4: " + empty_resolve4);
        System.out.println("empty_resolve5: " + empty_resolve5);


        // invoker UI: success.
//        UIInvoker ui_invoker = new UIInvoker();
//        ui_invoker.invokeUI();
        // this invoker worked as expected.


//        Path pathStart = Paths.get("/home/michael");
//        Search testSearch = new Search() {
//            private final Path start = pathStart;
//            @Override
//            public List<Path> search(String searchTerm) {
//                PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + searchTerm);
//                return search(start, matcher, 3).stream().sorted().collect(Collectors.toList());
//            }
//        };

//        List<Path> contents = SearchFor.userHomeDirectories().search("Documents");
//        List<Path> contents = testSearch.search("?*");
//        for (Path path : contents) {
//            System.out.println(path.toString());
//        }
        //------------------------------------------------------------------------------

        // worked as expected:
//        MetaPath test = NEW_NAME.execute(cc.getCurrentMetaPath());
//        System.out.println(test);
        // worked as expected:
//        NEW_NOTE.execute(cc.getCurrentMetaPath());

        // test Create.newCateory(MetaPath metaPath):
        MetaPath newCategory = new MetaPath(Paths.get(".NewNewCategory"));
        newCategory = cc.getCurrentMetaPath().resolve(newCategory);
        MetaPath testMP = Create.newCategory(newCategory);
        System.out.println("test: Create.newCategory(newCategory): File exists: " + Files.exists(testMP.getPath()));




    }
}