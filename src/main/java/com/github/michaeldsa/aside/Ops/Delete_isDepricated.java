package com.github.michaeldsa.aside.Ops;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.AsideUtils;
import com.github.michaeldsa.aside.CurrentCategory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public interface Delete_isDepricated<T,R> extends CrudOps<T,R> {



    enum Alg implements Delete_isDepricated<MetaPath,MetaPath> {
        DELETE_NOTE (m -> {
            System.out.println("DELETE_NOTE" + m);
            return m;
        }),
        DELETE_CATEGORY (m -> {
            System.out.println("DELETE_CATEGORY" + m);
            return m;
        }),
        CATEGORY_TEST (m -> {
            final CurrentCategory cc = CurrentCategory.INSTANCE;
            Path mpPath = cc.getCurrentMetaPath().getPath().resolve(m.getPath());
            Path vpPath = new ViewPath(m).getPath();
            MetaPath metaPath = AsideUtils.asMetaPath(mpPath);

            if(m == cc.getCurrentMetaPath()) {
                System.out.println("CURRENT CATEGORY");
                return null;
            }
            if(!AsideUtils.isCategory(cc.getCurrentMetaPath().resolve(m))) {
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

            if(AsideUtils.isCategory(metaPath)) {
                System.err.printf("CATEGORY_TEST: something went wrong. Category still exists: %s%n", metaPath);
            }
            return metaPath;
        }) ;

        private final Delete_isDepricated<MetaPath,MetaPath> delete;

        Alg(Delete_isDepricated<MetaPath,MetaPath> delete) {
            this.delete = delete;
        }

        @Override
        public MetaPath execute(MetaPath metaPath) {
            return this.delete.execute(metaPath);
        }
    }
}


