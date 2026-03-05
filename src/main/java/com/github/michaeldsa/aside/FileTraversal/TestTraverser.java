package com.github.michaeldsa.aside.FileTraversal;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.AsidePathElement.AsidePathElement;
import com.github.michaeldsa.aside.AsidePathElement.Category;
import com.github.michaeldsa.aside.AsidePathElement.MutableNote;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class TestTraverser extends Traverser{
    private final List<AsidePathElement> lape;

    public TestTraverser() {
        this.startingPoint = new MetaPath().getPath();
        this.options = Collections.emptySet();
        this.depth = Integer.MAX_VALUE;
        lape = new ArrayList<>();
    }

    public ArrayList<AsidePathElement> getList() {
        return new ArrayList<>(lape);
    }

    @Override
    public TestTraverser setStartingPoint(MetaPath startingPoint) {
        this.startingPoint = startingPoint.getPath();
        return this;
    }
    @Override
    public TestTraverser setStartingPoint(ViewPath startingPoint) {
        this.startingPoint = startingPoint.getPath();
        return this;
    }
    @Override
    public TestTraverser setFileVisitOptions(Set<FileVisitOption> options) {
        this.options = options;
        return this;
    }
    @Override
    public TestTraverser setDepth(int depth) {
        this.depth = depth;
        return this;
    }

    @Override
    public FileVisitResult _preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        if (Files.isDirectory(dir) && dir.getFileName().toString().startsWith(".")) {
            lape.add(new Category(new MetaPath(dir)));
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult _visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (Files.isRegularFile(file) && file.getFileName().toString().startsWith(".") && file.getFileName().toString().endsWith(".txt")) {
            MutableNote mn = new MutableNote(new MetaPath(file));
            // logic for obtaining information from properties file.
            lape.add(mn);
        }
        return FileVisitResult.CONTINUE;
    }

}
