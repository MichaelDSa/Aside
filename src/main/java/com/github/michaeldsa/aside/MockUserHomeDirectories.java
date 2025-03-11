package com.github.michaeldsa.aside;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class MockUserHomeDirectories implements Search{

    public List<Path> search(String searchTerm){
        Path mockUserHome = Paths.get("MOCK", "user", "home");
        List<Path> results = search(mockUserHome, searchTerm, 3);
        return results.stream().sorted().filter(Files::isDirectory).toList();
    }
}
