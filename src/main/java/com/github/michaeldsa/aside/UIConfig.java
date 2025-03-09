package com.github.michaeldsa.aside;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class UIConfig {
    private final Path home = Paths.get(System.getProperty("user.home"));
    private String parentDir;

    public void ui(){

        if (document_folders.size() > 1) {
            System.out.printf("Set home directory to: %s?%n ", document_folders.getLast());
            System.out.println("Alternative folders found");
            for (int i = 0; i < document_folders.size(); i++) {
                System.out.printf("%d: %s%n", i, document_folders.get(i));
            }
        } else if (document_folders.size() == 1) {
            System.out.printf("Set home directory to: %s%n? ", document_folders.get(0));
        } else {
            System.out.println("In which directory would you like to save the Aside home folder? Enter full path: ");
        }
    }
    public List<Path> findSuggestions(){
        return findSuggestions("documents");
    }
    public List<Path> findSuggestions(String choice) {

        // find $XDG_DOCUMENTS in user.home, ignoring case.
        List<Path> document_folders;
        try (Stream<Path> pathStream = Files.find(
                home,
                3,
                ((path, basicFileAttributes) ->
                        path.getFileName().toString().equalsIgnoreCase(choice)))
        ){
            document_folders = pathStream.toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return document_folders;
    }
}
