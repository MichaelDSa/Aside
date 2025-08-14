package com.github.michaeldsa.aside;

import javax.sound.midi.MetaEventListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public enum Deleters implements Deleter<MetaPath> {
    CATEGORY_TEST {
        final CurrentCategory cc = CurrentCategory.INSTANCE;

        @Override
        public MetaPath delete(MetaPath mp) {

            Path mpPath = cc.getCurrentMetaPath().getPath().resolve(mp.getPath());
            Path vpPath = new ViewPath(mp).getPath();
            MetaPath metaPath = AsideUtils.asMetaPath(mpPath);

            if(mp == cc.getCurrentMetaPath()) {
                System.out.println("CURRENT CATEGORY");
                return null;
            }
            if(!AsideUtils.isCategory(cc.getCurrentMetaPath().resolve(mp))) {
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
        }

    }
}
