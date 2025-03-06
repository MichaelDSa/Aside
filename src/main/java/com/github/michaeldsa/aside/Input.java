package com.github.michaeldsa.aside;

// This input helper will have a constructor that takes the user console
// input as a String. It will have a InputFilter instance variable, which
// can be swapped for others of the same type. InputFilter will be an
// interface for algorithms designed to filter the console input in various
// ways, such as to obtain command options, content, operation names, etc
public class Input {
    private final String input;
//    private final String command;

    public Input(String input) {
        this.input = input;
//        this.command = InputFilters.commandFilter().filter(input);
    }

    public String filterInput(InputFilter filter){
        return filter.filter(input);
    }
    public String getCommand(){
        return filterInput(InputFilters.commandFilter());
    }
    public String getInput(){
        return input;
    }
    public String getLongOptions(){
        return filterInput(InputFilters.longOptionsFilter());
    }
    public String getOperation(){
        return filterInput(InputFilters.operationFilter());
    }
    public String getShortOptions(){
        return filterInput(InputFilters.shortOptionsFilter());
    }




}
