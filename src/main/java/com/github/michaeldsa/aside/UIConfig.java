package com.github.michaeldsa.aside;

import java.io.Console;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class UIConfig {
    private Path asideHome;
    private final List<Path> suggestions = SearchFor.mockHomeUserDirectories().search("documents");

    public void ui(){

        // greet & prep user
        Prnt.width80ch("Welcome to Aside. This is the Configuration UI. Here, we will  assign a dedicated path for all notes, metadata, configuration files and more. Please answer a few questions to assign these configuration settings ");
        Prnt.width80ch("Set Aside_home: Which directory would you like to store all Aside notes?");

        if(!suggestions.isEmpty()) {
            System.out.println("Here are some suggested directories...");

            for(int i = 0; i < suggestions.size(); i++){
                System.out.printf("%d) %s%n", i+1, suggestions.get(i).toAbsolutePath());
            }

            System.out.println("Select a number from the above menu, or enter a full path:");
        } else {
            System.out.println("Enter directory below (suggestion: chose a documents folder):");
        }

        asideHome = consoleGetPath(": ");
        if(!Files.isDirectory(asideHome)){
            do {
                asideHome = consoleGetPath(" not a directory: " + asideHome.toString() + " \nTry again or 'exit' to quit");
            } while (!Files.isDirectory(asideHome));
        }
    }

    private boolean canParseInt(String string){
        try {
            Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private Path consoleGetPath(String prompt){
        Console console = System.console();
        String input = "";
        Path chosen;

        while(true){
            input = console.readLine(prompt);
            if (input.equals("exit")){
                System.out.println("Exiting...");
                System.exit(0);
            } else if(!suggestions.isEmpty() && canParseInt(input)){
                int index = Integer.parseInt(input) - 1;
                if(index >= 0 && index < suggestions.size()){

                    return suggestions.get(index).toAbsolutePath();

                } else {
                    System.out.println("choose a number from the menu.");
                }
            } else if (!input.isEmpty()){
                chosen = Paths.get(input);
                if(Files.exists(chosen)){

                    return chosen;

                } else {
                    System.out.println("Path is invalid. Does not exist, or is not a directory.");
                }
            }
        }
    }

    public Path getParentAsideHome() {return asideHome;}

}
