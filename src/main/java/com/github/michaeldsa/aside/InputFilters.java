package com.github.michaeldsa.aside;

// Factory for InputFilter objects
public class InputFilters {

    public static InputFilter commandFilter(){
        return new CommandFilter();
    }
    public static InputFilter longOptionsFilter(){
        return new LongOptionsFilter();
    }
    public static InputFilter operationFilter(){
        return new OperationFilter();
    }
    public static InputFilter shortOptionsFilter(){
        return new ShortOptionsFilter();
    }

}
