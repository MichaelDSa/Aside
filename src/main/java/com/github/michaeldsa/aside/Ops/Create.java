package com.github.michaeldsa.aside.Ops;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.AsidePathElement.AbstractNote;
import com.github.michaeldsa.aside.AsidePathElement.AsidePathElement;
import com.github.michaeldsa.aside.AsidePathElement.Category;
import com.github.michaeldsa.aside.RootPaths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public enum Create implements CrudOps<AsidePathElement, AsidePathElement> {

    NEW_CATEGORY ( ape-> {
        Path mpr = RootPaths.INSTANCE.getMetapath();

        // create permenant categories (.default, .trash, etc) if not exists.
        createPermanentCategories();

        // get the Path from ape.getMetaPath
        Path newCategory = ape instanceof AbstractNote
                ? ape.getParentCategory().getMetaPath().getPath()
                : ape.getMetaPath().getPath();

        // early dismissal:
        if (newCategory.equals(mpr)
                || !newCategory.startsWith(mpr)
                || Files.exists(newCategory)) {
            return ape;
        }
        
        // AsidePathElements avoid creation of invalid permanent directories.
        // for example:
        //      new Category(new MetaPath(Paths.get(metaPathRoot, ".default"))) is fine,
        //  but new Category(new MetaPath(Paths.get(metaPathRoot, ".default", ".subdirectory))) is modified to:
        //      new Category(new MetaPath(Paths.get(metaPathRoot, ".subdirectory")))
        // so we don't have to modify paths at this level.
        
        // create category
        Path v_newCategory = new ViewPath(new MetaPath(newCategory)).getPath();
        try {
            Files.createDirectories(newCategory); // MetaPath
            Files.createDirectories(v_newCategory); // ViewPath
        } catch (IOException e) {
            System.out.println("Create.NEW_CATEGORY failed \n" + e.getMessage());
        }
        return ape;
    }),
    NEW_NOTE (ape -> {
        System.out.println("Create.NEW_NOTE");
        return ape;
    });

    // class boilerplate:
    private final CrudOps<AsidePathElement,AsidePathElement> fops;

    // default category metapath:
    private static final Category defaultCategory = new Category(new MetaPath(Paths.get(".default")));
    private static final Category trashCategory = new Category(new MetaPath(Paths.get(".trash")));

    Create(CrudOps<AsidePathElement,AsidePathElement> fops) {
        this.fops = fops;
    }

    public AsidePathElement execute(AsidePathElement ape) {
        return fops.execute(ape);
    }
    public static boolean defaultCategoryExists() {
        boolean mpisdir = Files.isDirectory(defaultCategory.getMetaPath().getPath());
        boolean vpisdir = Files.isDirectory(defaultCategory.getViewPath().getPath());
        return mpisdir && vpisdir;
    }
    public static boolean trashCategoryExists() {
        boolean mpisdir = Files.isDirectory(trashCategory.getMetaPath().getPath());
        boolean vpisdir = Files.isDirectory(trashCategory.getViewPath().getPath());
        return mpisdir && vpisdir;
    }

    public static void createDefaultCategory() {
        if (!defaultCategoryExists()) {
            // create the metapath directory.
            Path mpath = defaultCategory.getMetaPath().getPath();
            Path vpath  = defaultCategory.getViewPath().getPath();
            try {
                Files.createDirectories(mpath);
                Files.createDirectories(vpath);
            } catch (IOException e){
                System.err.println("IOException in Create.createDefaultCategory().\n" + e.getMessage());
            }
        }
    }
    public static void createTrashCategory() {
        if (!trashCategoryExists()) {
            Path mpath = trashCategory.getMetaPath().getPath();
            Path vpath  = trashCategory.getViewPath().getPath();
            try {
                Files.createDirectories(mpath);
                Files.createDirectories(vpath);
            } catch (IOException e) {
                System.err.println("IOException in Create.createTrashCategory().\n" + e.getMessage());
            }
        }
    }
    public static void createPermanentCategories() {
        createDefaultCategory();
        createTrashCategory();
    }
}
