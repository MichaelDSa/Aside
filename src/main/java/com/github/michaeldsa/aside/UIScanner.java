package com.github.michaeldsa.aside;

import java.util.Scanner;

public class UIScanner {

    public void start(){
        Scanner scanner = new Scanner(System.in);
        boolean run = true;

        while(run){
            System.out.print("name/of/directory $ ");
            String userInput = scanner.nextLine();

            if(userInput.equals("exit")){
                run = false;
            } else {
                // TEST; this is a test of the Input object and its methods:
                Input input = new Input(userInput);
                System.out.println("input: " + input.getInput());
                System.out.println("Short Options: " + input.getShortOptions());
                System.out.println("Operation: " + input.getOperation());
                System.out.println("Command: " + input.getCommand());
                System.out.println("long Options: " + input.getLongOptions());
            }



            // Here is where we use the Input object which will
            // apply different decoupled strategies to the input.
        }
    }
}
