package com.github.michaeldsa.aside;

import com.github.michaeldsa.aside.UserInput.Input;

import java.util.Scanner;

public class UIInvoker {
    Invoker invoker = new Invoker();

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

                Command exec = () -> {
                    System.out.println(command);
                    return false;
                };
                Command undo =() -> {
                    System.out.println("undo: " + command);
                    return true;
                };

                switch (operation) {
                    case "add" -> invoker.execute(exec, undo);
                    case "undo" -> invoker.undo();
                    case "redo" -> invoker.redo();
                }

            }
        }
    }
}
