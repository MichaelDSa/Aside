package com.github.michaeldsa.aside.FileTraversal;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.AsidePathElement.RestrictedLists;
import com.github.michaeldsa.aside.RootPaths;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.Set;

public class DeleteCategory extends Traverser {
    public DeleteCategory() {
        this.startingPoint = RootPaths.INSTANCE.getMetapath();
        this.options = Collections.emptySet();
        this.depth = Integer.MAX_VALUE;
    }

    public DeleteCategory(MetaPath startingPoint) {
        this.startingPoint = startingPoint.getPath();
        this.options = Collections.emptySet();
        this.depth = Integer.MAX_VALUE;

    }
    public DeleteCategory(ViewPath startingPoint) {
        this.startingPoint = new MetaPath(startingPoint).getPath();
        this.options = Collections.emptySet();
        this.depth = Integer.MAX_VALUE;

    }

    @Override
    public DeleteCategory setStartingPoint(MetaPath startingPoint) {
        this.startingPoint = startingPoint.getPath();
        return this;
    }

    @Override
    public DeleteCategory setStartingPoint(ViewPath startingPoint) {
        this.startingPoint = new MetaPath(startingPoint).getPath();
        return this;
    }

    @Override
    public DeleteCategory setFileVisitOptions(Set<FileVisitOption> options) {
        this.options = options;
        return this;
    }

    @Override
    public DeleteCategory setDepth(int depth) {
        this.depth = depth;
        return this;
    }

    // traversal methods:
    @Override
    public FileVisitResult _visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Files.delete(file);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult _postVisitDirectory(Path dir, IOException ex) throws IOException {
        // do not delete permanent directories such as .default or default.
        if (!RestrictedLists.isPermanentDirectory(dir)) {
            Files.delete(dir);
        }
        return FileVisitResult.CONTINUE;
    }

//    @Override
//    public FileVisitResult _visitFileFailed(Path file, IOException ex) {
//        System.err.println("DeleteCategory._visitFileFailed() IOException: " + file + "\n" + ex.getMessage());
//        return FileVisitResult.CONTINUE;
//    }
}
