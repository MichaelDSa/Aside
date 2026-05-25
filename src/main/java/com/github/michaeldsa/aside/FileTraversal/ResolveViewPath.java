package com.github.michaeldsa.aside.FileTraversal;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.AsidePathElement.DiscardedNote;
import com.github.michaeldsa.aside.AsidePathElement.Note;
import com.github.michaeldsa.aside.AsidePathElement.RestrictedLists;
import com.github.michaeldsa.aside.PropertiesUtil.PropUtils;
import com.github.michaeldsa.aside.Initialization.RootPaths;
import com.github.michaeldsa.aside.Validation.ValidatePath;

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
    // set the width of the ViewPath .txt file

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
    public ResolveViewPath setWidth(int width) {
        return this;
    }

    public FileVisitResult _preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        // Does dir have a valid category name?
        if (ValidatePath.CATEGORY_NAME.test(dir)) {

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
                file.getParent().equals(RootPaths.INSTANCE.getMetapath()) ||
                !Files.isRegularFile(file) ||
                !ValidatePath.NOTE_NAME.test(file)
        ) {
            return FileVisitResult.CONTINUE;
        }

        // if the file parent is .DISCARDED:
        Path discarded = RestrictedLists.getDiscardedCategory().getMetaPath().getPath();

        if (file.startsWith(discarded)) {
            DiscardedNote de = new DiscardedNote(new MetaPath(file));
            PropUtils.retrieveDiscardedNote(de);
            PropUtils.writeDiscardedNote_ViewPath(de);
        } else {
            // if the file parent is not .DISCARDED
            Note mn = new Note(new MetaPath(file));
            PropUtils.retrieveNote(mn);
            PropUtils.writeNote_ViewPath(mn);
        }
        return FileVisitResult.CONTINUE;
    }

}
