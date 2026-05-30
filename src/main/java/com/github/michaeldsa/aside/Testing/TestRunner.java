package com.github.michaeldsa.aside.Testing;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.AsidePathElement.Author;
import com.github.michaeldsa.aside.Initialization.RootPaths;

import java.nio.file.Path;
import java.nio.file.Paths;

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
            // now deleted. It worked. it returned an arrayList of all paths and dirs from a starting point.

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


            // Testing HashSet<String>
//            HashSet<String> hs = new HashSet<>(Arrays.asList("260328_0130_33.txt", "260328_0130_52.txt", "260328_0131_17.txt", "260328_0131_28.txt" ));
//            String hs_string = hs.toString();
//            hs_string = hs_string.substring(1, hs_string.length()-1);
//            String[ ] hs_array = hs_string.split(", ");
//            HashSet<String> hs1 = new HashSet<>(List.of(hs_array));
//
//            System.out.println();
//            System.out.println(hs);
//            System.out.println(hs1);
//
//            // Testing String.replace()
//            String faux_array = "[string1, string2, string3, string4]";
//            System.out.println("\n" + faux_array);
//            faux_array = faux_array.replace("]", "");
//            faux_array = faux_array.replace("[", "");
//            faux_array = faux_array.replace(",", "");
//            System.out.println(faux_array);

//            Test.propUtils_writeNote();
//            Test.propUtils_writeDiscardedElement();
//            Test.propUtils_writeNoteViewPath();
//            Test.propUtils_writeDiscardedElementViewPath();
//            Test.propUtils_readNote();
//            Test.propUtils_readDiscardedElement();

            // Test Create.NOTE... The strategy has been updated.
//            Test.createNote();
            // Create.NOTE works when creating a note in DEFAULT
            // Create.NOTE fails when parent Category does not exist.
            // Success.

            // AbstractNote.generateNewNoteName()
//            Test.abstractNote_anti_redundant_naming();
            // [x] Success: stage 1 - current session anti-redundant naming
            // [x] Success: stage 2 - persistent anti-redundant naming
            //                        - Whole sandbox is checked for duplicate file name.

            // Test Config.getConfigDirectory()
//            System.out.println("\n" + Config.INSTANCE.getConfigDirectory());
            // Success.

            // Test Settings.getLineWidth()
//            Test.settings_LineWidth();

            // Test TempSettings
//            Test.setting_TempSettings();
            // Success

            // DiscardedElement: with metapathroot.
//            MetaPath today = new MetaPath(Paths.get(".DISCARDED/.d260505_1600_02.txt"));
//            DiscardedNote tesde = new DiscardedNote(today);
//            System.out.println(tesde);
//
//
//            DiscardedBibliography db = new DiscardedBibliography(new Bibliography(new MetaPath())
//                    .setTitle("Dune by F.Herbert")
//                    .setYearPublished(1975)
//                    .setAuthors("Frank Herbert")
//                    .addReferences(".260524_2338_07.txt")
//            );
//
//            System.out.println("\n" + db);
            // ABOVE SUCCESSFUL.

            // Quick Test NotePropsRetriever:
//            NotePropsRetriever npr = new NotePropsRetriever();
//            Note npr_note = new Note(new MetaPath(Paths.get(".PropUtils", ".260417_1722_20.txt")));
//            npr.retrieve(npr_note);
//            System.out.println(Pretty.formatNote4ViewPath(npr_note, 80));

            // NotePropsWriter Test:
//            Test.notePropsWriterTest();
            // SUCCESS

            // NotePropsRetriever Test:
//            Test.notePropsRetrieverTest();
            // SUCCESS

            // Author test
//            Test.authorTest();
            // SUCCESS

            // null values written to properties test:
//            Test.nullValuePropsWriterTest();
            // SUCCESS/UNDERSTOOD

            // Test Pretty.formatBibliography4ViewPath
//            Test.bibliography_PropUtils_writeBibliography_ViewPath();
            // SUCCESS

            // Test PropUtils.writeBibliography
//            Test.bibliography_PropUtils_writeBibliography();
            // SUCCESS

            // Test PropUtils.retrieveBibliography
            Test.bibliography_PropUtils_readBibliography();








        }
    }
}
