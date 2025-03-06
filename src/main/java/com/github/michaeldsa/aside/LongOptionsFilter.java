package com.github.michaeldsa.aside;

public class LongOptionsFilter implements InputFilter{
    public String filter(String input) {
        // remove operation from input:
        String command = InputFilters.commandFilter().filter(input);

        return " [LongOptionsFilter has not been written]";
    }
}
