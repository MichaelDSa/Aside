package com.github.michaeldsa.aside;

// return the operation - usually the first word of the user input
public class OperationFilter implements InputFilter {

//    public OperationFilter() {}
    public String filter(String input){
        String[] inputArray = input.split(" ");
        // inputArray[0] is the operation
        return inputArray[0];

    }
}
