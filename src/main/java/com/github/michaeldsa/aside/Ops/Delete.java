package com.github.michaeldsa.aside.Ops;

import com.github.michaeldsa.aside.AsidePathElement.*;
import com.github.michaeldsa.aside.AsideUtils;
import com.github.michaeldsa.aside.Initialization.CurrentCategory;
import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.FileTraversal.Traversers;
import com.github.michaeldsa.aside.Testing.Test;
import com.github.michaeldsa.aside.Validation.ValidateAsidePath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public enum Delete implements CrudOps<AsidePathElement, AsidePathElement> {


    ALL_CATEGORIES (ape -> {
    /* recursively wipes out all categories and their contents, deleting
    all of them, including contents of DEFAULT, but not the DEFAULT parent,
    which is a permanent category. Does not operate on BIBLIOGRAPHY or
    DISCARDED. */
        MetaPath startingPoint;
        MetaPath default_category = new MetaPath(RestrictedLists.getMetaPathDefaultDirectoryName());
        if (ape instanceof Category) {
            startingPoint = new MetaPath();
        } else {
            System.err.println("Delete.ALL_CATEGORIES: can operate only on Category types \narg type provided: " + ape.getClass().getSimpleName());
            return ape;
        }

        if (!ape.getMetaPath().equals(default_category)) {
            System.err.println("Delete.ALL_CATEGORIES: Category.metaPath (starting point) must equal: " + default_category + "\nstartingPoint provided: " + ape.getMetaPath());
            return ape;
        }

        try {
            Traversers.deleteCategory(startingPoint).traverse_and_purgeViewPathOrphans();
        } catch (IOException e) {
            System.err.println("Delete.ALL_CATEGORIES: IOException \n" + e.getMessage());
            e.printStackTrace();
        }

        return ape;
    }),
    BIBLIOGRAPHY (ape -> {
    // delete a single Bibilography within a BibliographyCategory. depends on DeleteCategory.
        MetaPath startingPoint;
        if (ape instanceof Bibliography) {
            startingPoint = ape.getMetaPath();
        } else {
            System.err.println("Delete.BIBLIOGRAPHY: can operate only on Note types \narg type provided: " + ape.getClass().getSimpleName());
            return ape;
        }

        if (Files.notExists(startingPoint.getPath())) {
            System.err.println("Delete.BIBLIOGRAPHY: file does not exist: " + startingPoint);
            return ape;
        }

        if (!ValidateAsidePath.BIBLIOGRAPHY.test(startingPoint)) {
            System.err.println("Delete.BIBLIOGRAPHY: Invalid MetaPath: " + startingPoint);
            return ape;
        }

        try {
            Traversers.deleteBibligoraphyCategory(startingPoint).traverse_and_purgeViewPathOrphans();
        } catch (IOException e ) {
            System.err.println("Delete.BIBLIOGRAPHY: IOException \n" + e.getMessage());
        }

        return ape;


    }),
    BIBLIOGRAPHY_CATEGORY (ape -> {
    /* recursively deletes contents of BIBLIOGRAPHY, but not the BIBLIOGRAPHY
    parent, which is a permanent category */
        MetaPath startingPoint;
        MetaPath bibliography_category = new MetaPath(RestrictedLists.getMetaPathBibliographyDirectoryName());
        if (ape instanceof BibliographyCategory) {
            startingPoint = ape.getMetaPath();
        } else {
            System.err.println("Delete.BIBLIOGRAPHY_CATEGORY: can operate only on BibiographyCategory types \narg type provided: " + ape.getClass().getSimpleName());
            return ape;
        }

        if (!startingPoint.startsWith(bibliography_category)) {
            System.err.println("Delete.BIBLIOGRAPHY_CATEGORY: BibliographyCategory.metaPath (starting point) must start with: " + bibliography_category + "\nstartingPoint provided: " + ape.getMetaPath());
        }

        try {
            Traversers.deleteBibligoraphyCategory(startingPoint).traverse_and_purgeViewPathOrphans();
        } catch (IOException e ) {
            System.err.println("Delete.BIBLIOGRAPHY_CATEGORY: IOException \n" + e.getMessage());
        }

        return ape;
    }),
    CATEGORY (ape -> {
    /* recursively deletes any category and it's contents, except DEFAULT,
    which is a permanent category, or multiple child categories of
    AsidePathRoot (see ALL_CATEGORIES) */
        MetaPath startingPoint;
        MetaPath default_category = new MetaPath(RestrictedLists.getMetaPathDefaultDirectoryName());
        if ((ape instanceof Category)) {
            startingPoint = ape.getMetaPath();
        } else {
            System.err.println("Delete.CATEGORY: can operate only on Category types \narg type provided: " + ape.getClass().getSimpleName());
            return ape;
        }
        Test.echo("Delete.CATEGORY1: startingPoint: " + startingPoint);
        if (Files.notExists(startingPoint.getPath())) {
            System.err.println("Delete.CATEGORY: file does not exist: " + startingPoint.getPath());
            return ape;
        }

        if (startingPoint.equals(default_category)) {
            System.err.println("Delete.CATEGORY: use Delete.DEFAULT_CATEGORY to recursively delete all notes in DEFAULT.");
            return ape;
        }

        try {
            Traversers.deleteCategory(startingPoint).traverse_and_purgeViewPathOrphans();
        } catch (IOException e) {
            System.err.println("Delete.CATEGORY: IOException \n" + e.getMessage());
        }

        return ape;
    }),
    DEFAULT_CATEGORY (ape -> {
    // deletes all contents of DEFAULT, but not the DEFAULT parent, which is a permanent category.
        MetaPath startingPoint;
        MetaPath default_category = new MetaPath(RestrictedLists.getMetaPathDefaultDirectoryName());
        if (ape instanceof Category) {
            startingPoint = ape.getMetaPath();
        } else {
            System.err.println("Delete.DEFAULT_CATEGORY: can operate only on Category types \narg type provided: " + ape.getClass().getSimpleName());
            return ape;
        }

        if (!startingPoint.equals(default_category)) {
            System.err.println("Delete.DEFAULT_CATEGORY: Category.metaPath (starting point) must equal: " + default_category);
            return ape;
        }

        try {
            Traversers.deleteCategory(startingPoint).traverse_and_purgeViewPathOrphans();
        } catch (IOException e) {
            System.err.println("Delete.DEFAULT_CATEGORY: IOException \n" + e.getMessage());
        }
        return ape;
    }),
    DISCARDED_CATEGORY (ape -> {
        // deletes all contents of DISCARDED, but not the DISCARDED parent, which is a permanent category.
        MetaPath startingPoint;
        MetaPath discarded_category = new MetaPath(RestrictedLists.getMetaPathDiscardedDirectoryName());
        if (ape instanceof DiscardedCategory) {
            startingPoint = ape.getMetaPath();
        } else {
            System.err.println("Delete.DISCARDED_CATEGORY: can operate only on DiscardedCategory types \narg type provided: " + ape.getClass().getSimpleName());
            return ape;
        }

        if (!startingPoint.equals(discarded_category)) {
            System.err.println("Delete.DISCARDED_CATEGORY: DiscardedCategory.metaPath (starting point) must equal: " + discarded_category);
            return ape;
        }

        try {
            Traversers.deleteDiscardedCategory().traverse_and_purgeViewPathOrphans();
        } catch (IOException e) {
            System.err.println("Delete.DISCARDED_CATEGORY: IOException \n" + e.getMessage());
        }
        return ape;
    }),
    DISCARDED_ELEMENT (ape -> {
    // delete a single note within a Category. depends on DeleteCategory.
        MetaPath startingPoint;
        if (ape instanceof DiscardedNote || ape instanceof DiscardedBibliography) {
            startingPoint = ape.getMetaPath();
        } else {
            System.err.println("Delete.DISCARDED_ELEMENT: can operate only on DiscardedNote or DiscardedBibliography types \narg type provided: " + ape.getClass().getSimpleName());
            return ape;
        }

        if (Files.notExists(startingPoint.getPath())) {
            System.err.println("Delete.DISCARDED_ELEMENT: file does not exist: " + startingPoint);
            return ape;
        }

        if (!(ValidateAsidePath.DISCARDED_NOTE.test(startingPoint) || ValidateAsidePath.DISCARDED_BIBLIOGRAPHY.test(startingPoint))) {
            System.err.println("Delete.DISCARDED_ELEMENT: Invalid MetaPath: " + startingPoint);
            return ape;
        }

        try {
            Traversers.deleteDiscardedCategory(startingPoint).traverse_and_purgeViewPathOrphans();
        } catch (IOException e ) {
            System.err.println("Delete.DISCARDED_ELEMENT: IOException \n" + e.getMessage());
        }

        return ape;

    }),
    NOTE (ape -> {
    // delete a single note within a Category. depends on DeleteCategory.
        MetaPath startingPoint;
        if (ape instanceof Note) {
            startingPoint = ape.getMetaPath();
        } else {
            System.err.println("Delete.NOTE: can operate only on Note types \narg type provided: " + ape.getClass().getSimpleName());
            return ape;
        }

        if (Files.notExists(startingPoint.getPath())) {
            System.err.println("Delete.NOTE: file does not exist: " + startingPoint);
            return ape;
        }

        if (!ValidateAsidePath.NOTE.test(startingPoint)) {
            System.err.println("Delete.NOTE: Invalid MetaPath: " + startingPoint);
            return ape;
        }

        try {
            Traversers.deleteCategory(startingPoint).traverse_and_purgeViewPathOrphans();
        } catch (IOException e ) {
            System.err.println("Delete.NOTE: IOException \n" + e.getMessage());
        }

        return ape;
    });



    private final CrudOps<AsidePathElement, AsidePathElement> fops;
    Delete(CrudOps<AsidePathElement, AsidePathElement> fops) {
        this.fops = fops;
    }
    @Override
    public AsidePathElement execute(AsidePathElement ape) {
        return fops.execute(ape);
    }
}
