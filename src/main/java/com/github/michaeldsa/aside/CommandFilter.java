package com.github.michaeldsa.aside;

import java.util.Arrays;

// return the whole, command of the operation
// without the short or long options
public class CommandFilter implements InputFilter{
//    public CommandFilter() { }
    public String filter(String input) {
        String[] inputArray = input.split(" ");
        String[] rawCommand = Arrays.copyOfRange(inputArray, 1, inputArray.length);
        StringBuilder sb = new StringBuilder();

        for (String s : rawCommand) {
            if (!s.startsWith("-")){
                sb.append(s).append(" ");
            }
        }
        return sb.toString().trim();
    }
}
