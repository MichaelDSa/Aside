package com.github.michaeldsa.aside;

// return the short options of the command. The user can scatter short
// options anyhere in the command, and ShortOptionFilter will collect them all.
public class ShortOptionsFilter implements InputFilter{

    public String filter(String input) {

        String[] inputArray = input.split(" ");
        StringBuilder sb = new StringBuilder();

        for (String s : inputArray) {
            // scan for short options, avoid long options:
            if ((s.startsWith("-")) && (s.charAt(1) != '-')) {
                String option = s.substring(1);
                sb.append(option);
            }
        }
        return sb.toString();
    }
}
