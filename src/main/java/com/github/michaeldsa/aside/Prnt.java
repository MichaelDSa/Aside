package com.github.michaeldsa.aside;

public interface Prnt {

    static void width80ch(String string) {
        width(string, 80);
    }
    static void width(String string, int width) {
        String regex = String.format("(.{1,%d})(\\s+|$)", width);
        string = string.replaceAll(regex, "$1%n");
        System.out.printf(string);
    }
}
