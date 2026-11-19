package com.github.michaeldsa.aside.Testing.CommandPatternTest;

import com.github.michaeldsa.aside.UserInput.Input;

import java.util.Scanner;

public class UIInvoker {
    Invoker1 invoker1 = new Invoker1();

    public void invokeUI() {

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.print("cmd: ");
            String userInput = scanner.nextLine();

            if (userInput.equals("exit")) {
                running = false;
            } else {
                Input input = new Input(userInput);

                String operation = input.getOperation();
                String command = input.getCommand();

                Cmd exec = () -> {
                    System.out.println(command);
                    return false;
                };
                Cmd undo =() -> {
                    System.out.println("undo: " + command);
                    return true;
                };

                switch (operation) {
                    case "add" -> invoker1.execute(exec, undo);
                    case "undo" -> invoker1.undo();
                    case "redo" -> invoker1.redo();
                }

            }
        }
    }
}
