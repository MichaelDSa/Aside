package com.github.michaeldsa.aside;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Search {

    public List<Path> search(String searchTerm);

    default List<Path> search(Path start, String searchTerm, int depth){

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

}
