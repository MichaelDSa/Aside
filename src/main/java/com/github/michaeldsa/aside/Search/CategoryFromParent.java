package com.github.michaeldsa.aside.Search;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryFromParent implements Search {
    private final Path startFrom;

    public CategoryFromParent(Path parent) {
        this.startFrom = parent;
    }
    public List<Path> search(String searchTerm) {
        List<Path> results = search(startFrom, searchTerm, Integer.MAX_VALUE);
        return results.stream().sorted().filter(Files::isDirectory).collect(Collectors.toList());
    }
}
