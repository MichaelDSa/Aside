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

            // test Delete.CATEGORY:
            // 1. create category with nested directories and .txt files.
            // 2. resolveViewPath
            // 3. create Category of one of the nested categoryies
            // 4. use Delete.CATEGORY on that Category.
            // 5. try the same thing on an AbstractNote type.

            // test for correct path:
            Path del = Paths.get(".default", ".subcategory", ".260302_1732_07.txt");
            MetaPath deleteIt = new MetaPath(del);
            Category delCat = new Category(deleteIt);
            MutableNote delMN = new MutableNote(delCat);
            MutableNote delMN2 = new MutableNote(deleteIt);

            System.out.println("Path to delete: " + deleteIt.getPath());
            System.out.println("MutableNote delMN: " + delMN2.getMetaPath());

            // now delete everything starting from .nine, inclusive
//            Delete.CATEGORY.execute(delMN);
//            Update.updateViewPath();
            // It worked. I need to try it with an MutableNote
            // It worked with MutableNote.


            // test Category: filterMetaPathElements(), filterViewPathElements() in Ctegory constructors.
            MetaPath mp = new MetaPath(Paths.get(".default", ".subcategory", ".subcategory1", ".subcategory2"));
            ViewPath vp = new ViewPath(Paths.get("default", "subcategory", "subcategory1", "subcategory2", "260302_1732_07.txt"));
            ViewPath vp1 = new ViewPath(Paths.get("default", "260302_1732_07.txt"));
            ViewPath vp2 = new ViewPath(Paths.get("DEFAULT"));

            Category meta = new Category(mp);
            Category view = new Category(vp2);
//            System.out.println("meta:   " + meta.getMetaPath());
//            System.out.println("view:   " + view.getViewPath());
            // The test worked, avoiding duplicates of permanent categories.

            MutableNote mnvp = new MutableNote(vp);
            MutableNote mnvp1 = new MutableNote(vp2);

            System.out.println("mnvp.getMetaPath(): " + mnvp.getMetaPath());
            System.out.println("mnvp.getViewPath(): " + mnvp.getViewPath());
            System.out.println("mnvp1.getMetaPath(): " + mnvp1.getMetaPath());
            System.out.println("mnvp1.getViewPath(): " + view.getViewPath());




        }
    }
}
