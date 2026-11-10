package com.github.michaeldsa.aside.FileTraversal;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.Initialization.RootPaths;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Set;

public class Delete extends Traverser{


    @Override
    Traverser setStartingPoint(MetaPath startingPoint) {
        this.startingPoint = startingPoint.getPath();
        return this;
    }

    @Override
    Traverser setStartingPoint(ViewPath startingPoint) {
        this.startingPoint = new MetaPath(startingPoint).getPath();
        return this;
    }

    @Override
    Traverser setFileVisitOptions(Set<FileVisitOption> options) {
        this.options = options;
        return this;
    }

    @Override
    Traverser setDepth(int depth) {
        this.depth = depth;
        return this;
    }

    private boolean startingPointIsRoot() {
        return startingPoint.equals(RootPaths.INSTANCE.getMetapath());
    }

    @Override
    public FileVisitResult _visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        /*
        * make sure:
        *            1) starting point is not asidePathRoot
        *            2) file does not start with DISCARDED
        *            3) file does not start with BIBLIOGRPAHY*/

        return FileVisitResult.CONTINUE;
    }
    public FileVisitResult _postVisitDirectory(Path dir, IOException ex) throws IOException {

        return FileVisitResult.CONTINUE;
    }
}
