package com.github.michaeldsa.aside.FileTraversal;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.AsidePathElement.*;
import com.github.michaeldsa.aside.PropertiesUtil.PropUtils;
import com.github.michaeldsa.aside.Initialization.RootPaths;
import com.github.michaeldsa.aside.Validation.ValidateAsidePath;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.Set;

/*
This class traverses the MetaPath, and recursively copies
all qualifying directories and .txt files to the ViewPath
counterpart.
 */
public class ResolveViewPath extends Traverser{

    public ResolveViewPath() {
        this.startingPoint = new MetaPath().getPath(); // root metapath
        this.options = Collections.emptySet();
        this.depth = Integer.MAX_VALUE;
    }
    public ResolveViewPath(MetaPath startingPoint) {
        this.startingPoint = startingPoint.getPath(); // root metapath
        this.options = Collections.emptySet();
        this.depth = Integer.MAX_VALUE;
    }
    public ResolveViewPath(ViewPath startingPoint) {
        this.startingPoint = new MetaPath(startingPoint).getPath();
        this.options = Collections.emptySet();
        this.depth = Integer.MAX_VALUE;
    }
    @Override
    public ResolveViewPath setStartingPoint(MetaPath startingPoint) {
        this.startingPoint = startingPoint.getPath();
        return this;
    }
    @Override
    public ResolveViewPath setStartingPoint(ViewPath viewPath) {
        this.startingPoint = new MetaPath(viewPath).getPath();
        return this;
    }
    @Override
    public ResolveViewPath setFileVisitOptions(Set<FileVisitOption> options) {
        this.options = options;
        return this;
    }
    @Override
    public ResolveViewPath setDepth(int depth) {
        this.depth = depth;
        return this;
    }

    public FileVisitResult _preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        // Does dir have a valid category name?
        if (ValidateAsidePath.CATEGORY_NAME.test(new MetaPath(dir))) {

            // ...if so, get its ViewPath counterpart
            Path vpath = new ViewPath(new MetaPath(dir)).getPath();
            if (Files.notExists(vpath)) {
                Files.createDirectory(vpath);
            }
        }


        return FileVisitResult.CONTINUE;
    }

    public FileVisitResult _visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        // early dismissal:
        if (
                file.getParent().equals(RootPaths.INSTANCE.getMetapath()) || // a file may only exist in a directory within asidePath root.
                !Files.isRegularFile(file) ||
                !Files.isHidden(file) // dot must precede filename.
        ) {
            return FileVisitResult.CONTINUE;
        }

        Path discarded = RestrictedLists.getDiscardedCategory().getMetaPath().getPath();
        Path bibliography = RestrictedLists.getBibliographyCategory().getMetaPath().getPath();

        if (file.startsWith(discarded)) {
            /* may either be a DiscardedNote or a DiscardedBibliography */
            if (ValidateAsidePath.DISCARDED_NOTE_NAME.test(new MetaPath(file))) {
                DiscardedNote de = new DiscardedNote(new MetaPath(file));
                PropUtils.retrieveDiscardedNote(de);
                PropUtils.writeDiscardedNote_ViewPath(de);
            } else if (ValidateAsidePath.DISCARDED_BIBLIOGRAPHY_NAME.test(new MetaPath(file))) {
                DiscardedBibliography db = new DiscardedBibliography(new MetaPath(file));
                PropUtils.retrieveDiscardedBibliography(db);
                PropUtils.writeDiscardedBibliography_ViewPath(db);
            }
        } else if (file.startsWith(bibliography)) {
            Bibliography bb = new Bibliography(new MetaPath(file));
            PropUtils.retrieveBibliography(bb);
            PropUtils.writeBibliography_ViewPath(bb);
        } else if (ValidateAsidePath.NOTE_NAME.test(new MetaPath(file))) {
            Note mn = new Note(new MetaPath(file));
            PropUtils.retrieveNote(mn);
            PropUtils.writeNote_ViewPath(mn);
        }
        return FileVisitResult.CONTINUE;
    }

}
