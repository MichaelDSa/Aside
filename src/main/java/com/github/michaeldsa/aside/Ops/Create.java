package com.github.michaeldsa.aside.Ops;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.AsidePathElement.*;
import com.github.michaeldsa.aside.PropertiesUtil.PropUtils;
import com.github.michaeldsa.aside.Initialization.RootPaths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public enum Create implements CrudOps<AsidePathElement, AsidePathElement> {

    CATEGORY(ape-> {
        // prerequisite: parameter must be Category or AbstractNote implementation.
        if (!(ape instanceof Category || ape instanceof AbstractNote)) {
            System.err.println("Create.CATEGORY: Parameter must be Category or AbstractNote implementation");
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
        if ( !(ape instanceof AbstractNote) ) {
            System.err.println("Create.NOTE: Parameter must be AbstractNote implementation");
            return ape;
        }

        if (Files.exists(m_note)) {
            System.err.println("Create.NOTE: Note or file already exists!");
            return ape;
        }

        // ensure permanent categories exist:
        // consider placing this in the UI instead of here.
        Create.createPermanentCategories();

        // if The category does not exist, abort.
        if (!Files.exists(m_note.getParent())) {
            String cat_name = new ViewPath(ape.getMetaPath()).getPath().getParent().getFileName().toString();
            System.out.println("Category does not exist: " + cat_name);
            return ape;
        }

        // write note to MetaPath and ViewPath
        PropUtils.writeNote( (AbstractNote) ape );

        return ape;
    }),
    DISCARDED_NOTE(ape -> {
        if (!(ape instanceof DiscardedNote)) {
            System.err.println("Create.DISCARDED_NOTE: Parameter must be a DiscardedNote instance");
            return ape;
        }

        if (Files.exists(ape.getMetaPath().getPath())) {
            System.err.println("Create.DISCARDED_NOTE: file already exists!");
            return ape;
        }
        PropUtils.writeDiscardedNote( (DiscardedNote) ape );
        return ape;
    }),
    DISCARDED_BIBLIOGRAPHY(ape -> {
        if (!(ape instanceof DiscardedBibliography)) {
            System.err.println("Create.DISCARDED_BIBLIOGRAPHY: Parameter must be a DiscardedBibliography instance");
            return ape;
        }

        if (Files.exists(ape.getMetaPath().getPath())) {
            System.err.println("Create.DISCARDED_BIBLIOGRAHY: file already exists!");
            return ape;
        }
        PropUtils.writeDiscardedBibliography( (DiscardedBibliography) ape );
        return ape;
    }),
    BIBLIOGRAPHY_CATEGORY(ape -> {

        return ape;
    }),
    BIBLIOGRAPHY(ape -> {
        PropUtils.writeBibliography( (Bibliography) ape );
        return ape;
    });

    // class boilerplate:
    private final CrudOps<AsidePathElement,AsidePathElement> fops;

    // default category metapath:
    private static final Category defaultCategory = RestrictedLists.getDefaultCategory();
    private static final DiscardedCategory discardedCategory = RestrictedLists.getDiscardedCategory();
    private static final BibliographyCategory bibliographyCategory = RestrictedLists.getBibliographyCategory();

    Create(CrudOps<AsidePathElement,AsidePathElement> fops) {
        this.fops = fops;
    }

    public AsidePathElement execute(AsidePathElement ape) {
        return fops.execute(ape);
    }
    public static boolean bibliographyCategoryExists() {
        boolean mpisdir = Files.isDirectory(bibliographyCategory.getMetaPath().getPath());
        boolean vpisdir = Files.isDirectory(bibliographyCategory.getViewPath().getPath());
        return mpisdir && vpisdir;
    }
    public static boolean defaultCategoryExists() {
        boolean mpisdir = Files.isDirectory(defaultCategory.getMetaPath().getPath());
        boolean vpisdir = Files.isDirectory(defaultCategory.getViewPath().getPath());
        return mpisdir && vpisdir;
    }
    public static boolean discardedElementDirectoryExists() {
        boolean mpisdir = Files.isDirectory(discardedCategory.getMetaPath().getPath());
        boolean vpisdir = Files.isDirectory(discardedCategory.getViewPath().getPath());
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
            Path mpath = discardedCategory.getMetaPath().getPath();
            Path vpath  = discardedCategory.getViewPath().getPath();
            try {
                Files.createDirectories(mpath);
                Files.createDirectories(vpath);
            } catch (IOException e) {
                System.err.println("IOException in Create.createDiscardedCategory().\n" + e.getMessage());
            }
        }
    }
    public static void createBibliographyCategory() {
        if (!bibliographyCategoryExists()) {
            Path mpath = bibliographyCategory.getMetaPath().getPath();
            Path vpath  = bibliographyCategory.getViewPath().getPath();
            try {
                Files.createDirectories(mpath);
                Files.createDirectories(vpath);
            } catch (IOException e) {
                System.err.println("IOException in Create.createBibliographyCategory().\n" + e.getMessage());
            }
        }
    }
    public static void createPermanentCategories() {
        createDefaultCategory();
        createDiscardedCategory();
        createBibliographyCategory();
    }
}
