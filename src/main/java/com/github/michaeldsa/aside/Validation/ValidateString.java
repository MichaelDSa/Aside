package com.github.michaeldsa.aside.Validation;

import java.util.function.Predicate;

public enum ValidateString implements Predicate<String> {
    NOTE_NAME (s -> {

        // string should be formatted thus:
        // `[.]xxxxxx_xxxx_xx.txt`.
        // s must have length of 18 or 19
        // s must end with `.txt`.
        if(!(s.length() == 18 || s.length() == 19)){
            return false;
        } else if(!s.endsWith(".txt")) {
            return false;
        }

        if (s.startsWith(".")) {
            s = s.substring(1);
        }

        // remove '.txt' and organize into array split by underscore
        // result should be: {"xxxxxx", "xxxx", "xx"}, where all 'x' can be parsed to int.
        String s_sansExt = s.split("\\.")[0]; // remove extension, '.txt'
        String[] s_sansExt_splitByUnderscore = s_sansExt.split("_");

        // Signs are not allowed. Integer.parseInt() will accept integer signs.
        boolean no_integer_signs = !s.contains("-") && !s.contains("+");

        boolean parts_have_valid_lengths =
                s_sansExt.length() == 14 &&
                s_sansExt_splitByUnderscore.length == 3 &&
                s_sansExt_splitByUnderscore[0].length() == 6 &&
                s_sansExt_splitByUnderscore[1].length() == 4 &&
                s_sansExt_splitByUnderscore[2].length() == 2;

        boolean all_characters_are_in_position =
                s.charAt(6) == '_' &&
                s.charAt(11) == '_' &&
                s.charAt(14) == '.';

        boolean chars_between_underscores_and_ext_are_numerals = true;
        for(String string : s_sansExt_splitByUnderscore) {
            try {
                Integer.parseInt(string);
            } catch(NumberFormatException e) {
                chars_between_underscores_and_ext_are_numerals = false;
                break;
            }
        }


        return no_integer_signs &&
                parts_have_valid_lengths &&
                all_characters_are_in_position &&
                chars_between_underscores_and_ext_are_numerals;
    }),
    CATEGORY_NAME (s -> {
        /*
        category names must:
        - Must not have note format
        - Must not have an extension. May not contain dots except first char.
        - QUESTION: What if user uses non-standard character for directory name? What about non-english characters?
         */
        if(s.startsWith(".")){
            s = s.substring(1);
        }
//        TEST
//        System.out.println(s);
//        System.out.println("no contains dot: " + !s.contains("."));
//        System.out.println("is invalid as notename: " + !ValidateString.NOTE_NAME.test(s));
//        System.out.println("return value: " + (!s.contains(".") && !ValidateString.NOTE_NAME.test(s)) );
        return !s.contains(".") && !ValidateString.NOTE_NAME.test(s);
    }),
    TAG_NAME (s -> {
        /*
        tag names:
        - Must not start with a dot
        - May contain a dot
        - May end with a dot
        - Must not be in NOTE_NAME format
         */
        return !s.startsWith(".") && !ValidateString.NOTE_NAME.test(s);
    }),
    BIBLIOGRAPHY_NAME (s -> {
        /*
        A bibliography filename must start with '.b', or 'b'.  The
        rest of the filename must be a valid note filename.

        Bibliography filename format: `[.]bxxxxxx_xxxx_xx.txt`
        */
        String filename = "";
        if(s.startsWith(".b")) {
            filename = s.substring(2);
        } else if(s.startsWith("b")) {
            filename = s.substring(1);
        } else {
            return false;
        }
        return ValidateString.NOTE_NAME.test(filename);
    }),
    DISCARDED_NOTE_NAME(s -> {
        /*
        A DiscardedNote filename must start with '.d', or
        'd'.  The rest of the filename must be a valid Note filename.

        DiscardedNote filename format: `[.]dxxxxxx_xxxx_xx.txt`
        */
        String filename = "";
        if(s.startsWith(".d")) {
            filename = s.substring(2);
        } else if(s.startsWith("d")) {
            filename = s.substring(1);
        } else {
            return false;
        }
        return ValidateString.NOTE_NAME.test(filename);
    });

    // class boilerplate:
    private final Predicate<String> predicate;

    ValidateString(Predicate<String> pred) {
        this.predicate = pred;
    }

    @Override
    public boolean test(String arg) {
        return predicate.test(arg);
    }

}
