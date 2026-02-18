package com.github.michaeldsa.aside.Ops;

import com.github.michaeldsa.aside.AsidePathElement.Category;
import com.github.michaeldsa.aside.AsideUtils;
import com.github.michaeldsa.aside.CurrentCategory;
import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.AsidePathElement.AsidePathElement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public enum Delete implements CrudOps<AsidePathElement, AsidePathElement> {


    NOTE (ape -> {
        System.out.println("DELETE_NOTE" + ape);
        return ape;
    }),
    DELETE_CATEGORY (ape -> {
        System.out.println("DELETE_CATEGORY" + ape);
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
