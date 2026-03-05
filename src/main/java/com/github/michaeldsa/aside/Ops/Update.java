package com.github.michaeldsa.aside.Ops;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePathElement.AsidePathElement;
import com.github.michaeldsa.aside.FileTraversal.PurgeViewPathOrphans;
import com.github.michaeldsa.aside.FileTraversal.ResolveViewPath;
import com.github.michaeldsa.aside.FileTraversal.Traverser;
import com.github.michaeldsa.aside.FileTraversal.Traversers;

import java.io.IOException;
import java.util.Collections;

public enum Update implements CrudOps<AsidePathElement,AsidePathElement> {



    METADATA_CONTENT_PREPEND (ape -> {
        System.out.println("METADATA_UPDATE_CONTENT_PREPEND" + ape);
        return ape;
    }),
    METADATA_CONTENT_APPEND (ape -> {
        System.out.println("METADATA_UPDATE_CONTENT_APPEND" + ape);
        return ape;
    }),
    METADATA_CONTENT_TRUNCATE (ape -> {
        System.out.println("METADATA_UPDATE_CONTENT_TRUNCATE" + ape);
        return ape;
    }),
    RESOLVE_CONTENT_META_TO_VIEW (ape -> {
        // update specific ViewPath from ape.getViewPath().
        System.out.println("RESOLVE_CONTENT_META_TO_VIEW" + ape);
        return ape;
    }),
    RESOLVE_CONTENT_VIEW_TO_META (ape -> {
        System.out.println("RESOLVE_CONTENT_VIEW_TO_META" + ape);
        return ape;
    });


    // class boilerplate
    private final CrudOps<AsidePathElement,AsidePathElement> fops;

    Update(CrudOps<AsidePathElement,AsidePathElement> fops) {
        this.fops = fops;
    }

    @Override
    public AsidePathElement execute(AsidePathElement ape) {
        return fops.execute(ape);
    }

    public static void updateViewPath(){
        /*
        recursively update the ViewPath starting from viewpath root,
        ensuring that all qualifying directories and files in MetaPath
        have a corresponding ViewPath dir/file, and any ViewPath 
        dir/file for which there is no corresponding MetaPath element
        is deleted. The goal is to match the MetaPath tree structure.
        However, unqualified dir/files may exist in the MetaPath 
        without being recognized by Aside
         */
        try {
            Traversers.resolveViewPath().traverse();
            Traversers.purgeViewPathOrphans().traverse();
        } catch (IOException e) {
            System.err.println("IOException in Update.updateViewPath().\n" + e.getMessage());
        }
    }
}
