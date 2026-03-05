package com.github.michaeldsa.aside.FileTraversal;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Set;

public abstract class Traverser {
    protected Path startingPoint;
    protected Set<FileVisitOption> options;
    protected int depth;

    // abstract method chaining setters:
    abstract Traverser setStartingPoint(MetaPath startingPoint);
    abstract Traverser setStartingPoint(ViewPath startingPoint);
    abstract Traverser setFileVisitOptions(Set<FileVisitOption> options);
    abstract Traverser setDepth(int depth);

    public FileVisitResult _preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }
    public FileVisitResult _visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }
    public FileVisitResult _postVisitDirectory(Path dir, IOException ex) throws IOException {
        return FileVisitResult.CONTINUE;
    }
    public FileVisitResult _visitFileFailed(Path file, IOException ex)throws IOException {
        System.err.println(Traverser.class.getName() + " _visitFileFailed() \n" + ex.getMessage());
        return FileVisitResult.CONTINUE;
    }

    // method chaining ideas
//    default Traverser traverseFirst(Traverser traverser, AsidePathElement ape, Set<FileVisitOption> options, int depth) {
//        traverser.traverse(ape, options, depth);
//        return this;
//    }
//    default void travserseNext(Traverser traverser, AsidePathElement ape, Set<FileVisitOption> options, int depth) {
//        traverser.traverse(ape, options, depth);
//    }

//    public void traverse() throws IOException {
//        traverse(Collections.emptySet(), Integer.MAX_VALUE);
//    }
    public void traverse() throws IOException {

        Files.walkFileTree(startingPoint, options, depth, new SimpleFileVisitor<>(){
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                return _preVisitDirectory(dir, attrs);
            }
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                return _visitFile(file, attrs);
            }
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException ex) throws IOException {
                return _postVisitDirectory(dir, ex);
            }
            @Override
            public FileVisitResult visitFileFailed(Path file, IOException ex) throws IOException  {
                return _visitFileFailed(file, ex);
            }
        });
    }

}
