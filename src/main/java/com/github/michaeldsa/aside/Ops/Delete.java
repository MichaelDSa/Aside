package com.github.michaeldsa.aside.Ops;

import com.github.michaeldsa.aside.AsidePathElement.AbstractNote;
import com.github.michaeldsa.aside.AsidePathElement.Category;
import com.github.michaeldsa.aside.AsideUtils;
import com.github.michaeldsa.aside.CurrentCategory;
import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.AsidePathElement.AsidePathElement;
import com.github.michaeldsa.aside.FileTraversal.Traversers;
import com.github.michaeldsa.aside.RootPaths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public enum Delete implements CrudOps<AsidePathElement, AsidePathElement> {


    NOTE (ape -> {
        System.out.println("DELETE_NOTE" + ape);
        return ape;
    }),
    CATEGORY(ape -> {

        Path mpr = RootPaths.INSTANCE.getMetapath();


        // get path from a Category
        Path startingPoint = ape instanceof AbstractNote
                ? ape.getParentCategory().getMetaPath().getPath()
                : ape.getMetaPath().getPath();


        // early dismissal:
            // if startingPoint equals MetaPath root, return
            // if startingPoint does not exist
            // Note: if a permenant category is deleted, it
            //       will be immediately reinstated.
        if (Files.notExists(startingPoint)
                || startingPoint.equals(mpr)
                || !startingPoint.toString().startsWith(mpr.toString())
        ) {
            return ape;
        }
        // delete MetaPath layer of Categories and contents, then
        // purge all remnants in the ViewPath layer.
        MetaPath m_startingPoint = new MetaPath(startingPoint);
        ViewPath v_startingPoint = new ViewPath(m_startingPoint);
        try {
            Traversers.deleteCategory(m_startingPoint)
                    .traverse();
            Traversers.purgeViewPathOrphans(v_startingPoint)
                    .traverse();
        } catch (IOException e) {
            System.err.println("Delete.CATEGORY: IOException \n" + e.getMessage());
        }

        // Reinstate permenant categories if they don't exist.
        Create.createPermenantCategories();

        return ape;
    }),
    CATEGORY_TEST (ape -> {
        final CurrentCategory cc = CurrentCategory.INSTANCE;
        Path mpPath = cc.getCurrentMetaPath().resolve(ape.getMetaPath()).getPath();
        Path vpPath = new ViewPath(mpPath).getPath();
        Category category = new Category(new MetaPath(mpPath));
//        MetaPath metaPath = AsideUtils.asMetaPath(mpPath);

        if(ape.getMetaPath() == cc.getCurrentCategory().getMetaPath()) {
            System.out.println("CURRENT CATEGORY");
            return null;
        }
        if(!AsideUtils.isCategory(cc.getCurrentCategory().getMetaPath().resolve(ape.getMetaPath()))) {
            System.out.println("NOT A CATEGORY");
            return null;
        }
        if(Files.notExists(mpPath)) {
            System.out.println("FILE NOT EXISTS");
            return null;
        }

        try {
            Files.deleteIfExists(mpPath);
            if(Files.notExists(mpPath)) {
                Files.deleteIfExists(vpPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(AsideUtils.isCategory(category.getMetaPath())) {
            System.err.printf("CATEGORY_TEST: something went wrong. Category still exists: %s%n", category);
        }
        return category;
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
