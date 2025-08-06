package com.github.michaeldsa.aside;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryFromRoot implements Search {
    private final Path root = RootPaths.INSTANCE.getMetapath();

        public List<Path> search(String searchTerm) {
            List<Path> results = search(root, searchTerm, Integer.MAX_VALUE);
            return results.stream().sorted().filter(Files::isDirectory).collect(Collectors.toList());
        }

}
