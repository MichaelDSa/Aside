package com.github.michaeldsa.aside.Ops;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.AsidePathElement.*;
import com.github.michaeldsa.aside.PropertiesUtil.PropUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public enum Create implements CrudOps<AsidePathElement, AsidePathElement> {

    CATEGORY(ape-> {
    // create Category (a directory in the MetaPath and corresponding ViewPath), if not exists
        // ape must be a Category
        if (!(ape instanceof Category)) {
            System.err.println("Create.CATEGORY: Parameter must be Category or AbstractNote instance\n");
            return ape;
        }
        // get the Paths
        Path m_newCategory = ape.getMetaPath().getPath();
        Path v_newCategory = new ViewPath(new MetaPath(m_newCategory)).getPath();

        // create permanent categories (.default, .trash, etc) if not exists.
        Create.createPermanentCategories();

        // create category if not exists
        createCustomCategory(m_newCategory, v_newCategory, "Create.CATEGORY failed\n");

        return new Category((Category) ape);
    }),
    NOTE(ape -> {

        Path m_note = ape.getMetaPath().getPath();

        if ( !(ape instanceof Note) ) {
            System.err.println("Create.NOTE: Parameter must be AbstractNote subclass instance\n");
            return ape;
        }

        if (Files.exists(m_note)) {
            System.err.println("Create.NOTE: Note or file already exists!");
            return new Note((Note) ape);
        }

        // consider placing this in the UI instead of here.
        Create.createPermanentCategories();

        // if The category does not exist, abort.
        if (!Files.exists(m_note.getParent())) {
            String cat_name = new ViewPath(ape.getMetaPath()).getPath().getParent().getFileName().toString();
            System.out.println("Category does not exist: " + cat_name);
            return new Note((Note) ape);
        }

        PropUtils.writeNote( (AbstractNote) ape );

        return new Note((Note) ape);
    }),
    DISCARDED_NOTE(ape -> {
        if (!(ape instanceof DiscardedNote)) {
            System.err.println("Create.DISCARDED_NOTE: Parameter must be a DiscardedNote instance\n");
            return ape;
        }

        Create.createPermanentCategories();

        if (Files.exists(ape.getMetaPath().getPath())) {
            System.err.println("Create.DISCARDED_NOTE: file already exists!");
            return new DiscardedNote((DiscardedNote) ape);
        }
        PropUtils.writeDiscardedNote( (DiscardedNote) ape );
        return new DiscardedNote((DiscardedNote) ape);
    }),
    DISCARDED_BIBLIOGRAPHY(ape -> {
        if (!(ape instanceof DiscardedBibliography)) {
            System.err.println("Create.DISCARDED_BIBLIOGRAPHY: Parameter must be a DiscardedBibliography instance\n");
            return ape;
        }

        Create.createPermanentCategories();

        if (Files.exists(ape.getMetaPath().getPath())) {
            System.err.println("Create.DISCARDED_BIBLIOGRAHY: file already exists!");
            return new DiscardedBibliography((DiscardedBibliography) ape);
        }
        PropUtils.writeDiscardedBibliography( (DiscardedBibliography) ape );
        return new DiscardedBibliography((DiscardedBibliography) ape);
    }),
    BIBLIOGRAPHY_CATEGORY(ape -> {
        Path m_bibCat = ape.getMetaPath().getPath();
        Path v_bibCat = ape.getViewPath().getPath();

        if (!(ape instanceof BibliographyCategory)) {
            System.err.println("Create.BIBLIOGRAPHY_CATEGORY: Parameter must be a BibliographyCategory instance\n");
            return ape;
        }

        Create.createPermanentCategories();

        if (Files.exists(m_bibCat)) {
            System.err.println("Create.BIBLIOGRAPHY_CATEGORY: File already exists!");
            return new BibliographyCategory((BibliographyCategory) ape);
        }

        createCustomCategory(m_bibCat, v_bibCat, "Create.BIBLIOGRAPHY_CATEGRY failed\n");
        return new BibliographyCategory((BibliographyCategory) ape);
    }),
    BIBLIOGRAPHY(ape -> {
        if (!(ape instanceof Bibliography)) {
            System.err.println("Create.BIBLIOGRAPHY: Parameter must be a Bibliography instance\n");
            return ape;
        }

        Create.createPermanentCategories();

        if (Files.exists(ape.getMetaPath().getPath())) {
            System.err.println("Create.BIBLIOGRAPHY: file already exists!");
            return new Bibliography((Bibliography) ape);
        }

        PropUtils.writeBibliography( (Bibliography) ape );

        return new Bibliography((Bibliography) ape);
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
    private static boolean bibliographyCategoryExists() {
        boolean mpisdir = Files.isDirectory(bibliographyCategory.getMetaPath().getPath());
        boolean vpisdir = Files.isDirectory(bibliographyCategory.getViewPath().getPath());
        return mpisdir && vpisdir;
    }
    private static boolean defaultCategoryExists() {
        boolean mpisdir = Files.isDirectory(defaultCategory.getMetaPath().getPath());
        boolean vpisdir = Files.isDirectory(defaultCategory.getViewPath().getPath());
        return mpisdir && vpisdir;
    }
    private static boolean discardedElementDirectoryExists() {
        boolean mpisdir = Files.isDirectory(discardedCategory.getMetaPath().getPath());
        boolean vpisdir = Files.isDirectory(discardedCategory.getViewPath().getPath());
        return mpisdir && vpisdir;
    }

    public static void createDefaultCategory() {
        if (!defaultCategoryExists()) {
            // create DEFAULT
            createCustomCategory(
                defaultCategory.getMetaPath().getPath(),
                defaultCategory.getViewPath().getPath(),
                "IOException in Create.createDefaultCategory().\n" );
        }
    }
    public static void createDiscardedCategory() {
        if (!discardedElementDirectoryExists()) {
            // create DISCARDED
            createCustomCategory(
                    discardedCategory.getMetaPath().getPath(),
                    discardedCategory.getViewPath().getPath(),
                    "IOException in Create.createDiscardedCategory().\n" );
        }
    }
    public static void createBibliographyCategory() {
        if (!bibliographyCategoryExists()) {
            // create BIBLIOGRAPHY
            createCustomCategory(
                    bibliographyCategory.getMetaPath().getPath(),
                    bibliographyCategory.getViewPath().getPath(),
                    "IOException in Create.createBibliographyCategory().\n" );
        }
    }
    private static void createCustomCategory(Path mpath, Path vpath, String error_msg) {
        try {
            Files.createDirectories(mpath);
            Files.createDirectories(vpath);
        } catch (IOException e) {
            System.err.println(error_msg + e.getMessage());
        }
    }
    public static void createPermanentCategories() {
        createDefaultCategory();
        createDiscardedCategory();
        createBibliographyCategory();
    }
}
