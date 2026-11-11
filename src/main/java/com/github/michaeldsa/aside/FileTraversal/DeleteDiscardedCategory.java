package com.github.michaeldsa.aside.FileTraversal;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.AsidePathElement.RestrictedLists;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.Set;

/* Recursively delete elements in DISCARDED, without deleting it's parent */
public class DeleteDiscardedCategory extends Traverser {
    private final Path discardedCategoryParent = new MetaPath(RestrictedLists.getMetaPathDiscardedDirectoryName()).getPath();

    public DeleteDiscardedCategory() {
        this.startingPoint = discardedCategoryParent;
        this.options = Collections.emptySet();
        this.depth = Integer.MAX_VALUE;
    }

    public DeleteDiscardedCategory(MetaPath startingPoint) {
        this.startingPoint = startingPoint.getPath();
        this.options = Collections.emptySet();
        this.depth = Integer.MAX_VALUE;

    }
    public DeleteDiscardedCategory(ViewPath startingPoint) {
        this.startingPoint = new MetaPath(startingPoint).getPath();
        this.options = Collections.emptySet();
        this.depth = Integer.MAX_VALUE;

    }


    @Override
    public DeleteDiscardedCategory setStartingPoint(MetaPath startingPoint) {
        this.startingPoint = startingPoint.getPath();
        return this;
    }

    @Override
    public DeleteDiscardedCategory setStartingPoint(ViewPath startingPoint) {
        this.startingPoint = new MetaPath(startingPoint).getPath();
        return this;
    }

    @Override
    public DeleteDiscardedCategory setFileVisitOptions(Set<FileVisitOption> options) {
        this.options = options;
        return this;
    }

    @Override
    public DeleteDiscardedCategory setDepth(int depth) {
        this.depth = depth;
        return this;
    }

    @Override
    public FileVisitResult _visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (file.startsWith(discardedCategoryParent)) {
            Files.delete(file);
            return FileVisitResult.CONTINUE;
        }
        return FileVisitResult.TERMINATE;
    }
    public FileVisitResult _postVisitDirectory(Path dir, IOException ex) throws IOException {
        if (dir.startsWith(discardedCategoryParent) && !RestrictedLists.isPermanentDirectory(dir)) {
            Files.delete(dir);
            return FileVisitResult.CONTINUE;
        }
        return FileVisitResult.TERMINATE;
    }

    public void traverse_and_purgeViewPathOrphans() throws IOException{
        this.traverse();
        PurgeViewPathOrphans pvo = new PurgeViewPathOrphans(new ViewPath(new MetaPath(this.startingPoint)));
        pvo.traverse();
    }
}
