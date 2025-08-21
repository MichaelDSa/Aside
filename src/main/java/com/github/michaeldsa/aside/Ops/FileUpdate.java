package com.github.michaeldsa.aside.Ops;

import com.github.michaeldsa.aside.AsidePath.MetaPath;

public enum FileUpdate implements FileOps<MetaPath> {



    METADATA_CONTENT_PREPEND (m -> {
        System.out.println("METADATA_UPDATE_CONTENT_PREPEND" + m);
        return m;
    }),
    METADATA_CONTENT_APPEND (m -> {
        System.out.println("METADATA_UPDATE_CONTENT_APPEND" + m);
        return m;
    }),
    METADATA_CONTENT_TRUNCATE (m -> {
        System.out.println("METADATA_UPDATE_CONTENT_TRUNCATE" + m);
        return m;
    }),
    RESOLVE_CONTENT_META_TO_VIEW (m -> {
        System.out.println("RESOLVE_CONTENT_META_TO_VIEW" + m);
        return m;
    }),
    RESOLVE_CONTENT_VIEW_TO_META (m -> {
        System.out.println("RESOLVE_CONTENT_VIEW_TO_META" + m);
        return m;
    });


    private final FileOps<MetaPath> fops;

    FileUpdate(FileOps<MetaPath> fops) {
        this.fops = fops;
    }

    @Override
    public MetaPath execute(MetaPath m) {
        return fops.execute(m);
    }

}
