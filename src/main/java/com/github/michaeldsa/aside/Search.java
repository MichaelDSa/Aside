package com.github.michaeldsa.aside;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Search {

    List<Path> search(String searchTerm);

    default List<Path> search1(Path start, String searchTerm, int depth){

        try (Stream<Path> stream = java.nio.file.Files.find(
                start,
                depth,
                ((path, baf) ->
                        path.getFileName().toString().equalsIgnoreCase(searchTerm)))
        ) {

            return stream.collect(Collectors.toList());

        } catch (IOException e) {
            System.err.printf("Search for %s failed: %s%n", searchTerm, e.getMessage());
        }
        return new ArrayList<>();
    }

    default List<Path> search(Path start, String searchTerm, int depth){
        List<Path> walkList = new ArrayList<>();
        Set<FileVisitOption> options = Collections.emptySet();
        try {
            Files.walkFileTree(start, options, depth, new SimpleFileVisitor<>(){
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                   if(dir.getFileName().toString().equalsIgnoreCase(searchTerm) && !walkList.contains(dir)){

                       walkList.add(dir);
                    }
                   return FileVisitResult.CONTINUE;
                }
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if(file.getFileName().toString().equalsIgnoreCase(searchTerm) && !walkList.contains(file)){
                        walkList.add(file);
                    }
                    return FileVisitResult.CONTINUE;
                }
                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

            });
        } catch (IOException e) {
            System.err.printf("Search for %s failed: %s%n", searchTerm, e.getMessage());
        }
        System.out.println(walkList);
        return walkList;
    }

}
