package com.github.michaeldsa.aside.FileTraversal;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.AsidePathElement.RestrictedLists;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.Set;

/* Recursively delete any element in BIBLIOGRAPHY, without deleting it's parent */
public class DeleteBibliographyCategory extends Traverser{
    private final Path bibliographyCategoryParent = new MetaPath(RestrictedLists.getMetaPathBibliographyDirectoryName()).getPath();

    public DeleteBibliographyCategory() {
        this.startingPoint = bibliographyCategoryParent;
        this.options = Collections.emptySet();
        this.depth = Integer.MAX_VALUE;
    }

    public DeleteBibliographyCategory(MetaPath startingPoint) {
        this.startingPoint = startingPoint.getPath();
        this.options = Collections.emptySet();
        this.depth = Integer.MAX_VALUE;

    }
    public DeleteBibliographyCategory(ViewPath startingPoint) {
        this.startingPoint = new MetaPath(startingPoint).getPath();
        this.options = Collections.emptySet();
        this.depth = Integer.MAX_VALUE;

    }


    @Override
    public DeleteBibliographyCategory setStartingPoint(MetaPath startingPoint) {
        this.startingPoint = startingPoint.getPath();
        return this;
    }

    @Override
    public DeleteBibliographyCategory setStartingPoint(ViewPath startingPoint) {
        this.startingPoint =  new MetaPath(startingPoint).getPath();
        return this;
    }

    @Override
    public DeleteBibliographyCategory setFileVisitOptions(Set<FileVisitOption> options) {
        this.options = options;
        return this;
    }

    @Override
    public DeleteBibliographyCategory setDepth(int depth) {
        this.depth = depth;
        return this;
    }

    @Override
    public FileVisitResult _visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (file.startsWith(bibliographyCategoryParent)) {
            Files.delete(file);
            return FileVisitResult.CONTINUE;
        }
        return FileVisitResult.TERMINATE;
    }
    public FileVisitResult _postVisitDirectory(Path dir, IOException ex) throws IOException {
        if (dir.startsWith(bibliographyCategoryParent) && !RestrictedLists.isPermanentDirectory(dir)) {
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
