package com.github.michaeldsa.aside.FileTraversal;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.AsidePathElement.RestrictedLists;
import com.github.michaeldsa.aside.Initialization.RootPaths;
import com.github.michaeldsa.aside.Testing.Test;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.Set;

/* Recursively delete any Category including any of its children, while avoiding deletion of permanent categories. */
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
        boolean agreement                                                                                            // agreement. file may be deleted on the condition that:
                =  !file.startsWith(new MetaPath(RestrictedLists.getMetaPathDiscardedDirectoryName()).getPath())     // file must not start with the DISCARDED permanent category
                && !file.startsWith(new MetaPath(RestrictedLists.getMetaPathBibliographyDirectoryName()).getPath()); // file must not start with the BIBLIOGRAPHY permanent category
        if(agreement) {
            Files.delete(file);
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult _postVisitDirectory(Path dir, IOException ex) throws IOException {
        // do not delete permanent directories such as .DEFAULT or DISCARDED.
        boolean agreement                                           // agreement. dir may be deleted on the condition that:
                =  !RestrictedLists.isPermanentDirectory(dir)       // dir must not be a permanent category
                && !dir.equals(RootPaths.INSTANCE.getMetapath());   // dir must not equal MetaPathRoot
        if (agreement) {
            Files.delete(dir);
        }
        return FileVisitResult.CONTINUE;
    }

    public void traverse_and_purgeViewPathOrphans() throws IOException{
        this.traverse();
        PurgeViewPathOrphans pvo = new PurgeViewPathOrphans(new ViewPath(new MetaPath(this.startingPoint)));
        Test.echo("pvo startingPoint: " + pvo.startingPoint);
        pvo.traverse();
    }

}
