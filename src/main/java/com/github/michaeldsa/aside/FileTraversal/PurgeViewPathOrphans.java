package com.github.michaeldsa.aside.FileTraversal;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.Set;

public class PurgeViewPathOrphans extends Traverser {
    /*
    Traverses ViewPath from specified starting point; deletes files
    that do not have a corresponding MetaPath directory or file. This
    Traverser has access to all categories and permanent dirs.
     */
    public PurgeViewPathOrphans() {
        this.startingPoint = new ViewPath().getPath();
        this.options = Collections.emptySet();
        this.depth = Integer.MAX_VALUE;
    }
    public PurgeViewPathOrphans(ViewPath startingPoint) {
        this.startingPoint = startingPoint.getPath();
        this.options = Collections.emptySet();
        this.depth = Integer.MAX_VALUE;
    }
    public PurgeViewPathOrphans(MetaPath startingPoint) {
        this.startingPoint = new ViewPath(startingPoint).getPath();
        this.options = Collections.emptySet();
        this.depth = Integer.MAX_VALUE;
    }

    @Override
    public PurgeViewPathOrphans setStartingPoint(MetaPath metaPath) {
        this.startingPoint = new ViewPath(metaPath).getPath();
        return this;
    }
    @Override
    public PurgeViewPathOrphans setStartingPoint(ViewPath startingPoint) {
        this.startingPoint = startingPoint.getPath();
        return this;
    }
    @Override
    public PurgeViewPathOrphans setFileVisitOptions(Set<FileVisitOption> options) {
        this.options = options;
        return this;
    }
    @Override
    public PurgeViewPathOrphans setDepth(int depth) {
        this.depth = depth;
        return this;
    }



    @Override
    public FileVisitResult _postVisitDirectory(Path dir, IOException ex) throws IOException {
        // get the MetaPath counterpart as a Path
        Path mdir = new MetaPath(new ViewPath(dir)).getPath();

        // delete if mdir counterpart does not exist.
        if (Files.notExists(mdir)) {
            Files.delete(dir);
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult _visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Path mfile = new MetaPath(new ViewPath(file)).getPath();
        if (Files.notExists(mfile)) {
            Files.delete(file);
        }
        return FileVisitResult.CONTINUE;
    }

}
