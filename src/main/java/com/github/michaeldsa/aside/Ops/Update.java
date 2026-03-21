package com.github.michaeldsa.aside.Ops;

import com.github.michaeldsa.aside.AsidePathElement.AbstractNote;
import com.github.michaeldsa.aside.AsidePathElement.AsidePathElement;
import com.github.michaeldsa.aside.FileTraversal.Traversers;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Properties;
import java.util.Set;

public enum Update implements CrudOps<AsidePathElement,AsidePathElement> {

    CATEGORY (ape -> {
        return ape;
    }),
    WRITE_NOTE_METADATA(ape -> {
        if (!(ape instanceof AbstractNote)) {
            return ape;
        }

        // define and set each property:
        Properties props = new Properties();

        // set title property
        String title = ((AbstractNote) ape).getTitle();
        if (title == null) {
            title = "";
        }
        props.setProperty("title", title);

        // set content property
        String content = ((AbstractNote) ape).getContent();
        if (content == null) {
            content = "";
        }
        props.setProperty("content", content);

        // set to property
        Set<String> to = ((AbstractNote) ape).getTo();
        if (to == null) {
            to = Collections.emptySet();
        }
        props.setProperty("to", to.toString());

        // set from property
        Set<String> from = ((AbstractNote) ape).getFrom();
        if (from == null) {
            from = Collections.emptySet();
        }
        props.setProperty("from", from.toString());

        // set tags property
        Set<String> tags = ((AbstractNote) ape).getTags();
        if (tags == null) {
            tags = Collections.emptySet();
        }
        props.setProperty("tags", tags.toString());

        // save the properties to the file:
        Path m_note = ape.getMetaPath().getPath();
        try (OutputStream out = Files.newOutputStream(m_note)) {
            props.store(out, null);
            Traversers.resolveViewPath(ape.getViewPath()).traverse();
        } catch (IOException e) {
            System.err.println("Create.NOTE failed: " + e.getMessage());
        }

        return ape;
    }),

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
