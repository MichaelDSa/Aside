package com.github.michaeldsa.aside;

public class Pretty {

    private Pretty() {}

    public static void print(String string) {
        print(string, 80);
    }
    public static void print(String string, int width) {
        String regex = String.format("(.{1,%d})(\\s+|$)", width);
        string = string.replaceAll(regex, "$1%n");
        System.out.printf(string);
    }
    public static String format(String string, int width) {
        if (string != null) {
            String regex = String.format("(.{1,%d})(\\s+|$)", width);
            return string.replaceAll(regex, "$1\n");
        }
        return " ";
    }
}
