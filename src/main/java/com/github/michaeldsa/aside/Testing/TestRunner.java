package com.github.michaeldsa.aside.Testing;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.AsidePathElement.AsidePathElement;
import com.github.michaeldsa.aside.AsidePathElement.Category;
import com.github.michaeldsa.aside.AsidePathElement.MutableNote;
import com.github.michaeldsa.aside.FileTraversal.TestTraverser;
import com.github.michaeldsa.aside.FileTraversal.Traversers;
import com.github.michaeldsa.aside.Ops.Create;
import com.github.michaeldsa.aside.Ops.Delete;
import com.github.michaeldsa.aside.Ops.NewCreate;
import com.github.michaeldsa.aside.Ops.Update;
import com.github.michaeldsa.aside.RootPaths;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class TestRunner {
    public static class MutableNoteTests {
        public static void run() {
            // run test methods here

            MetaPath mpRoot = new MetaPath(RootPaths.INSTANCE.getMetapath());

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

            // Path parent vs subclass:

            Path slasher = Paths.get("first/second/third/");
            Path axleizer = Paths.get("first/second/third");
            // the above values are the same.
            System.out.println(slasher);
            System.out.println(axleizer);

            // parents of both paths are the same.
            System.out.println(slasher.getParent());
            System.out.println(axleizer.getParent());

            // search lambda:
            Test.searchLambda();

            // getRootDirs():
            Test.getRootDirs();

            // TestTraverser:
            System.out.println("\nTEST TRAVERSER: TestTraverser");
            Set<FileVisitOption> options = Collections.singleton(FileVisitOption.FOLLOW_LINKS);
            TestTraverser tt = new TestTraverser()
                    .setStartingPoint(mpRoot)
                    .setFileVisitOptions(options);

            try {
                tt.traverse();
            } catch (IOException e) {
                System.out.println("TestRunner: tt.traverse() failed: \n" + e.getMessage());
            }
            List<AsidePathElement> elements = tt.getList();
            for (AsidePathElement asidePathElement : elements) {
                System.out.println(asidePathElement);
            }

            // ResolveViewPath
//            Test.resolveViewPath();

            // Create.createDefaultCategory()
//            Test.createDefaultCategory();

            //Traversers.purgeViewPathOrphans().traverse()
//            Test.purgeViewPathOrphans();

            // Create.NEW_CATEGORY
//            Test.createCategory();

            // Create.createPermanentCategories()
//            Test.createPermanentCategories();

            // Create.NOTE. This Ops strategy depends on Update.WRITE_NOTE_METADATA
//            Test.createNote();

            // DiscardedElement test:
//            Test.discardedElement();







        }
    }
}
