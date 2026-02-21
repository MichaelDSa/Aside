package com.github.michaeldsa.aside.Testing;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.AsidePathElement.Category;
import com.github.michaeldsa.aside.Ops.Create;
import com.github.michaeldsa.aside.Ops.NewCreate;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TestRunner {
    public static class MutableNoteTests {
        public static void run() {
            // run test methods here

            // testing for Validattion.ValidatePath
            boolean is_cat_or_note = false;
            boolean is_cat = false;
            boolean is_note = false;
            String category_name = ".default/.new_books/.Hobbes";
            String note_name = "default/260202_0800_43/new_books/Hobbes/260101_0759_42.txt";
            Path category = new MetaPath(Paths.get(category_name)).getPath();
            Path note = new ViewPath(Paths.get(note_name)).getPath();

            is_cat_or_note = Test.validatePath_CN(category);
            is_cat = Test.validatePath_CN(category);
            is_note = Test.validatePath_N(note);

            System.out.println("\nPath " + category_name + " has a valid category or note name: " + is_cat_or_note);
            System.out.println("Path " + category_name + " has a valid Category name: " + is_cat);
            System.out.println("Path " + note_name + " has a valid note name: " + is_note);


        }
    }
}
