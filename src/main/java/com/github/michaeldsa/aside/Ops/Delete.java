package com.github.michaeldsa.aside.Ops;

import com.github.michaeldsa.aside.AsidePathElement.*;
import com.github.michaeldsa.aside.AsideUtils;
import com.github.michaeldsa.aside.Initialization.CurrentCategory;
import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.FileTraversal.Traversers;
import com.github.michaeldsa.aside.Testing.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public enum Delete implements CrudOps<AsidePathElement, AsidePathElement> {


    NOTE (ape -> {
        System.out.println("DELETE_NOTE" + ape);
        return ape;
    }),
    CATEGORY (ape -> {
        MetaPath startingPoint;
        MetaPath default_category = new MetaPath(RestrictedLists.getMetaPathDefaultDirectoryName());
        if ((ape instanceof Category)) {
            startingPoint = ape.getMetaPath();
        } else {
            System.err.println("Delete.CATEGORY: can operate only on Category types \narg provided: " + ape.getClass().getSimpleName());
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
        MetaPath startingPoint;
        MetaPath default_category = new MetaPath(RestrictedLists.getMetaPathDefaultDirectoryName());
        if (ape instanceof Category c) {
            startingPoint = c.getMetaPath();
        } else {
            System.err.println("Delete.DEFAULT_CATEGORY: can operate only on Category types \narg provided: " + ape.getClass().getSimpleName());
            return ape;
        }

        if (!startingPoint.equals(default_category)) {
            System.err.println("Delete.DEFAULT_CATEGORY: Category.metaPath must equal: " + default_category);
            return ape;
        }

        try {
            Traversers.deleteCategory(startingPoint).traverse_and_purgeViewPathOrphans();
        } catch (IOException e) {
            System.err.println("Delete.DEFAULT_CATEGORY: IOException \n" + e.getMessage());
        }
        return ape;
    }) ;



    private final CrudOps<AsidePathElement, AsidePathElement> fops;
    Delete(CrudOps<AsidePathElement, AsidePathElement> fops) {
        this.fops = fops;
    }
    @Override
    public AsidePathElement execute(AsidePathElement ape) {
        return fops.execute(ape);
    }
}
