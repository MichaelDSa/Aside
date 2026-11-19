package com.github.michaeldsa.aside.Command;

import com.github.michaeldsa.aside.AsidePathElement.Note;

import java.util.Arrays;
import java.util.Scanner;

public class TestUI4Invoker {
    Invoker invoker;
    public TestUI4Invoker() {
        invoker = new Invoker();
    }

    public void start() {
        Commands cmds = new Commands();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.print("cmd+ ");
            String input = scanner.nextLine();
            if (input.equals("exit")) {
                System.out.println("goodbye");
                running = false;
            } else if (!input.isEmpty()){
                String[] words = input.split(" ");
                String command = words[0].startsWith(":")
                        ? words[0].substring(1)
                        : "";
                words = Arrays.copyOfRange(words, 1, words.length);
                switch (command) {
                    case "nn" -> invoker.execute(cmds.createNote(new Note().setTitle(String.join(" ", words))));
                    case "dn" -> invoker.execute(cmds.deleteNote(new Note(words)));
                    case "vn" -> invoker.execute(cmds.viewNote(new Note(words)));
                    case "undo" -> invoker.undo();
                    case "redo" -> invoker.redo();
                    default -> System.err.println("Command not found.\nAvailable commands: nn {title}, dn {ViewPath}, vn {ViewPath}, undo, redo");
                }

            }
        }
    }
}
