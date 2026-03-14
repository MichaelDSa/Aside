package com.github.michaeldsa.aside.Testing;
// I don't have time to learn unit testing,
// so I'm writing this class to test stuff.

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePathElement.AsidePathElement;
import com.github.michaeldsa.aside.AsidePathElement.Category;
import com.github.michaeldsa.aside.AsidePathElement.MutableNote;
import com.github.michaeldsa.aside.CurrentCategory;
import com.github.michaeldsa.aside.FileTraversal.Traversers;
import com.github.michaeldsa.aside.Ops.Create;
import com.github.michaeldsa.aside.PathKeeper;
import com.github.michaeldsa.aside.Search.Search;
import com.github.michaeldsa.aside.Validation.ValidatePath;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        return ValidatePath.CATEGORY_OR_NOTE_NAME.test(path);
    }
    public static boolean validatePath_C(Path path) {
        return ValidatePath.CATEGORY_NAME.test(path);
    }
    public static boolean validatePath_N(Path path) {
        return ValidatePath.NOTE_NAME.test(path);
    }

    public static void searchLambda() {
        String h = System.getProperty("user.home");
        Path home = Paths.get(h);
        String searchTerm = "documents";
        int depth = 5;
        Search lambda = (s) -> {
            try (Stream<Path> stream = Files.find(
                    home,
                    depth,
                    ((path, baf) ->
                            path.getFileName().toString().equalsIgnoreCase(s)))
                    ){

                return stream.collect(Collectors.toList());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            return new ArrayList<>();
        };
        ArrayList<Path> ls = new ArrayList<>();
        ls.addAll(lambda.search("documents"));
        ls.addAll(lambda.search("projects"));
        ls.addAll(lambda.search("pictures"));
        ArrayList<Path> lssearch = new ArrayList<>(ls.stream().sorted().toList());

        for(Path p : lssearch) {
            System.out.println(p);
        }
    }

    public static void getRootDirs() {
        Iterable<Path> paths = FileSystems.getDefault().getRootDirectories();
        for(Path path : paths) {
            System.err.println(path);
        }
    }

    // Traversers:
    public static void resolveViewPath() {
        MetaPath mr = new MetaPath(); // metapath root
        try {
            Traversers.resolveViewPath()
                    .setStartingPoint(mr) // not necessary as default is metaPath root.
                    .setWidth(80)
                    .traverse();
        } catch (IOException e) {
            System.out.println("Test.resolveViewPath() failed \n" + e.getMessage());
        }
    }

    public static void createDefaultCategory() {
        Create.createDefaultCategory();
    }

    public static void purgeViewPathOrphans() {
        try {
            Traversers.purgeViewPathOrphans()
                    .traverse();
        } catch (IOException e) {
            System.err.println("Test.purgeViewPathOrphans() failed \n" + e.getMessage());
        }
    }

    public static void createPermanentCategories(){
        Create.createPermanentCategories();
    }

    public static void createCategory() {
        MutableNote in1 = new MutableNote(new MetaPath(Paths.get(".Default", ".one", ".two")));
        MutableNote in2 = new MutableNote(new MetaPath(Paths.get(".zero",".one", ".two", ".three")));
        Category c1 = new Category(new MetaPath(Paths.get(".Default", ".one", ".two", ".three")));
        Category c2 = new Category(new MetaPath(Paths.get(".zero", ".one", ".two", ".three")));
        MutableNote noCat = new MutableNote(new MetaPath());
        AsidePathElement[] array = {in1, in2, c1, c2, noCat};
        for (AsidePathElement ape : array) {
            Create.CATEGORY.execute(ape);
        }
    }


}