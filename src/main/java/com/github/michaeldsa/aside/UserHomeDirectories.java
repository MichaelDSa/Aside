package com.github.michaeldsa.aside;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class UserHomeDirectories implements Search {
    private final Path userHome = Paths.get(System.getProperty("user.home"));

    public List<Path> search(String searchTerm) {
        List<Path> results = search(userHome, searchTerm, 3);
        return results.stream().sorted().filter(Files::isDirectory).collect(Collectors.toList());
    }
}
