package com.github.michaeldsa.aside.Testing;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePathElement.Category;
import com.github.michaeldsa.aside.Ops.Create;
import com.github.michaeldsa.aside.Ops.NewCreate;

import java.nio.file.Paths;

public class TestRunner {
    public static class MutableNoteTests {
        public static void run() {
            // run test methods here
            System.out.println(Create.NEW_CATEGORY.execute(new Category(new MetaPath(Paths.get("new_jack_category")))));
            System.out.println("\nTesting NewCreate:");
            System.out.println(NewCreate.NEW_CATEGORY.execute(new Category(new MetaPath(Paths.get(".NewCreate_category")))));
        }
    }
}
