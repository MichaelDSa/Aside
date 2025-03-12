package com.github.michaeldsa.aside;

import java.io.Console;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class UIConfig {
    private Path parentAsideHome;
    private final List<Path> suggestions;

    public UIConfig(){
        suggestions = SearchFor.mockHomeUserDirectories().search("documents");
        // parentAsideHome is assigned interactively with ui().
    }

    public void ui(){

        // greet & prep user
        System.out.printf("%nWelcome to Aside.%n");
        Prnt.width80ch("This is the configuration UI. Aside wants to create a designated folder in which to store all notes, metadata, configuration files and more. This folder will be called 'Aside_home'. Please choose a directory in which the Aside_home folder will live. You may enter your choice below.");
        Prnt.width80ch("%nHint: '~/' not accepted. Use `/home/[username]/Documents`, not `~/Documents`");

        if(!suggestions.isEmpty()) {
            System.out.printf("%nHere are some suggested directories...%n");

            for(int i = 0; i < suggestions.size(); i++){
                System.out.printf("%d) %s%n", i+1, suggestions.get(i).toAbsolutePath());
            }

            Prnt.width80ch("Select a number from the above menu, enter a full path, or 'exit' to quit:");
        } else {
            Prnt.width80ch("Enter directory below or 'exit' to quit. (suggestion: chose a documents folder):");
        }

        parentAsideHome = consoleGetPath(": ");
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
        int count = 1;

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
                if(Files.exists(chosen) && Files.isDirectory(chosen)){

                    return chosen;

                } else {
                    System.out.println("Path is invalid. Does not exist, or is not a directory.");
                }
            }
            if(count++ % 3 == 0){
                System.out.println("'exit' to quit.");
            }
        }
    }

    public Path getParentAsideHome() {return parentAsideHome;}

}
