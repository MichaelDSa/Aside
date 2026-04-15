package com.github.michaeldsa.aside.Ops;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.AsidePathElement.*;
import com.github.michaeldsa.aside.RootPaths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public enum Create implements CrudOps<AsidePathElement, AsidePathElement> {

    CATEGORY(ape-> {
        // prerequisite: no DiscardedElement objects
        if (ape instanceof DiscardedElement) {
            System.err.println("Create.CATEGORY: Cannot operate on DiscardedElement objects.");
            return ape;
        }
        // get the Path from ape.getMetaPath
        Path m_newCategory = ape instanceof AbstractNote
                ? ape.getParentCategory().getMetaPath().getPath()
                : ape.getMetaPath().getPath();

        // early dismissal:
        Path mpr = RootPaths.INSTANCE.getMetapath();
        if (m_newCategory.equals(mpr)
                || !m_newCategory.startsWith(mpr)
                || Files.exists(m_newCategory)) {
            return ape;
        }

        // create permenant categories (.default, .trash, etc) if not exists.
        Create.createPermanentCategories();

        // AsidePathElements avoid creation of invalid permanent directories.
        // for example:
        //      new Category(new MetaPath(Paths.get(metaPathRoot, ".default"))) is fine,
        //  but new Category(new MetaPath(Paths.get(metaPathRoot, ".default", ".subdirectory))) is modified to:
        //      new Category(new MetaPath(Paths.get(metaPathRoot, ".subdirectory")))
        // so we don't have to modify paths at this level.
        
        // create category
        Path v_newCategory = new ViewPath(new MetaPath(m_newCategory)).getPath();
        try {
            Files.createDirectories(m_newCategory); // MetaPath
            Files.createDirectories(v_newCategory); // ViewPath
        } catch (IOException e) {
            System.err.println("Create.NEW_CATEGORY failed \n" + e.getMessage());
        }
        return ape;
    }),
    NOTE(ape -> {
        // get path of note:
        Path m_note = ape.getMetaPath().getPath();

        // early dismissal:
        if ( !(ape instanceof AbstractNote) || Files.exists(m_note)) {
            return ape;
        }

        // ensure permanent categories exist:
        // consider placing this in the UI instead of here.
        Create.createPermanentCategories();

        // get category path of note & ensure exists:
        // consider omitting this. It can be achieved
        // by the client with compound strategies. i.e.:
        // Create.CATEGORY.andThen(Create.NOTE).execute(ape)
        Path m_category = m_note.getParent();
        Create.CATEGORY.execute(new Category(new MetaPath(m_category)));

        // touch file:
        Path v_note = ape.getViewPath().getPath();
        try {
            Files.createFile(m_note);
            Files.createFile(v_note);
        } catch (IOException e) {
            System.out.println("Create.NOTE failed: \n file exists: \n " + m_note + "\n" + v_note);
        }

        // The note is empty. Update note with ape data:
        Update.WRITE_NOTE_METADATA.execute(ape);


        return ape;
    });

    // class boilerplate:
    private final CrudOps<AsidePathElement,AsidePathElement> fops;

    // default category metapath:
    private static final Category defaultCategory = RestrictedLists.getDefaultCategory();
    private static final DiscardedElement discardedElementDirectory = RestrictedLists.getDiscardedElementDirectory();

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
    public static boolean discardedElementDirectoryExists() {
        boolean mpisdir = Files.isDirectory(discardedElementDirectory.getMetaPath().getPath());
        boolean vpisdir = Files.isDirectory(discardedElementDirectory.getViewPath().getPath());
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
    public static void createDiscardedCategory() {
        if (!discardedElementDirectoryExists()) {
            Path mpath = discardedElementDirectory.getMetaPath().getPath();
            Path vpath  = discardedElementDirectory.getViewPath().getPath();
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
        createDiscardedCategory();
    }
}
