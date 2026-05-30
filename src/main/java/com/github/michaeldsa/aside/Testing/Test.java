package com.github.michaeldsa.aside.Testing;
// I don't have time to learn unit testing,
// so I'm writing this class to test stuff.

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.AsidePathElement.*;
import com.github.michaeldsa.aside.Initialization.CurrentCategory;
import com.github.michaeldsa.aside.FileTraversal.Traversers;
import com.github.michaeldsa.aside.Ops.Create;
import com.github.michaeldsa.aside.PathKeeper;
import com.github.michaeldsa.aside.Pretty;
import com.github.michaeldsa.aside.PropertiesUtil.NoteRetriever;
import com.github.michaeldsa.aside.PropertiesUtil.NoteWriter;
import com.github.michaeldsa.aside.PropertiesUtil.PropUtils;
import com.github.michaeldsa.aside.Search.Search;
import com.github.michaeldsa.aside.Settings.Settings;
import com.github.michaeldsa.aside.Settings.TempSettings;
import com.github.michaeldsa.aside.Validation.ValidatePath;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {
    public static PathKeeper pk = PathKeeper.INSTANCE;
    public static CurrentCategory cc = CurrentCategory.INSTANCE;




    public static void numberOfPathElements(){
        System.out.printf("Number of elements in `home_directory`: %s %s%n", pk.getHome_directory().getNameCount(), pk.getHome_directory() );
        System.out.printf("Number of elements in      `meta_home`: %s %s%n", pk.getMeta_home().getNameCount(), pk.getMeta_home() );
    }

    public static void metaPathDefaultConstructor() {
        MetaPath home = new MetaPath();
        System.out.println(home);
    }

    public static void metaPathNonDefaultConstructor() {
        MetaPath noHome = new MetaPath(Paths.get(".one", ".two"));
        System.out.println(noHome);
    }

    public static void metaPathValidationNoDots() {
        metaPathValidationTryCatch(Paths.get("one", "two"), "element names have no dots");
    }

    public static void metaPathValidationRestrictedNames(String ...addString) {
        String[] array = {
                ".meta",
                ".META",
                ".view",
                ".VIEW",
                ".ASIDE_HOME",
                ".aside_home",
                ".Aside_home",
                ".aSide_home",
                ".trash",
                ".TRASH"
        };
        List<String> names = Arrays.asList(array);
        names.addAll(Arrays.asList(addString));
        for(String name : names){
            metaPathValidationTryCatch(Paths.get(name), "element may not be named " + name);
        }
    }

    public static void metaPathValidationSlashes() {
        Path fwdSl = Paths.get("/");
        Path doubleFw = Paths.get("//");
        Path bckSl =  Paths.get("\\");
        Path sysSl = Paths.get(File.separator);
        Path[] paths = {fwdSl, doubleFw, bckSl, sysSl};
        int num = 0;
        for(Path p : paths){
            metaPathValidationTryCatch(p, num++ + p.toString());
        }
    }

    public static void metaPathValidationPaths(Path ...paths) {
        for(int i = 0; i < paths.length; i++){
            metaPathValidationTryCatch(paths[i], i + " " + paths[i].toAbsolutePath().normalize());
        }
    }

    public static void metaPathValidationTryCatch(Path path, String msg) {
        try {
            MetaPath illegal = new MetaPath(path);
        } catch (IllegalArgumentException e) {
            System.err.println("Caught Exception: IllegalArgumentException. " + msg);
            return;
        }
        System.out.println("IllegalArgumentException not caught. " + msg);
    }

    public static boolean validatePath_CN(Path path) {
        return ValidatePath.ALL_ELEMENTS.test(path);
    }
    public static boolean validatePath_C(Path path) {
        return ValidatePath.CATEGORY_NAME.test(path);
    }
    public static boolean validatePath_N(Path path) {
        return ValidatePath.NOTE_NAME.test(path);
    }

    public static void searchLambda() {
        String h = System.getProperty("user.home");
        Path home = Paths.get(h);
        String searchTerm = "documents";
        int depth = 5;
        Search lambda = (s) -> {
            try (Stream<Path> stream = Files.find(
                    home,
                    depth,
                    ((path, baf) ->
                            path.getFileName().toString().equalsIgnoreCase(s)))
                    ){

                return stream.collect(Collectors.toList());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            return new ArrayList<>();
        };
        ArrayList<Path> ls = new ArrayList<>();
        ls.addAll(lambda.search("documents"));
        ls.addAll(lambda.search("projects"));
        ls.addAll(lambda.search("pictures"));
        ArrayList<Path> lssearch = new ArrayList<>(ls.stream().sorted().toList());

        for(Path p : lssearch) {
            System.out.println(p);
        }
    }

    public static void getRootDirs() {
        Iterable<Path> paths = FileSystems.getDefault().getRootDirectories();
        for(Path path : paths) {
            System.err.println(path);
        }
    }

    // Traversers:
    public static void resolveViewPath() {
        MetaPath mr = new MetaPath(); // metapath root
        try {
            Traversers.resolveViewPath()
                    .setStartingPoint(mr) // not necessary as default is metaPath root.
                    .setWidth(80)
                    .traverse();
        } catch (IOException e) {
            System.out.println("Test.resolveViewPath() failed \n" + e.getMessage());
        }
    }

    public static void createDefaultCategory() {
        Create.createDefaultCategory();
    }

    public static void purgeViewPathOrphans() {
        try {
            Traversers.purgeViewPathOrphans()
                    .traverse();
        } catch (IOException e) {
            System.err.println("Test.purgeViewPathOrphans() failed \n" + e.getMessage());
        }
    }

    public static void createPermanentCategories(){
        Create.createPermanentCategories();
    }

    public static void createCategory() {
        Note in1 = new Note(new MetaPath(Paths.get(".Default", ".one", ".two")));
        Note in2 = new Note(new MetaPath(Paths.get(".zero",".one", ".two", ".three")));
        Category c1 = new Category(new MetaPath(Paths.get(".Discarded", ".one", ".two", ".three")));
        Category c2 = new Category(new MetaPath(Paths.get(".zero", ".one", ".two", ".three")));
        Note noCat = new Note(new MetaPath());
        AsidePathElement[] array = {in1, in2, c1, c2, noCat};
        for (AsidePathElement ape : array) {
            Create.CATEGORY.execute(ape);
        }
    }

    public static void createNote() {
        // define note
        Category fictionalCategory = new Category(new MetaPath(Paths.get(".FictionalCategory")));
        Note note1 = new Note(new MetaPath())
                .setTitle("Test Create.NOTE & Update.WRITE_NOTE_METADATA")
                .setContent(Pretty.format(
                        "This is a re-test of Create.NOTE. The strategy has been updated, and does not depend on Update.WRITE_NOTE_METADATA. A new package fulfills writing all Notes and DiscardedElements. It is called PropertiesUtil. Create.NOTE utilizes the factory method, ProUtils.writeNote().",
                        80 ))
                .setTo(new HashSet<>(Arrays.asList("to1", "toJ", "to3")))
                .setFrom(new HashSet<>(Arrays.asList("from1", "from2", "from3")))
                .setTags(new HashSet<>(Arrays.asList("tag1", "tag2", "tag3")));

        Note note2 = new Note(fictionalCategory) // should fail if .FictionalCategory does not exist.
                .setTitle("Test Create.NOTE & Update.WRITE_NOTE_METADATA")
                .setContent(Pretty.format(
                        "This is a re-test of Create.NOTE. The strategy has been updated, and does not depend on Update.WRITE_NOTE_METADATA. A new package fulfills writing all Notes and DiscardedElements. It is called PropertiesUtil. Create.NOTE utilizes the factory method, ProUtils.writeNote().",
                        80 ))
                .setTo(new HashSet<>(Arrays.asList("to1", "toJ", "to3")))
                .setFrom(new HashSet<>(Arrays.asList("from1", "from2", "from3")))
                .setTags(new HashSet<>(Arrays.asList("tag1", "tag2", "tag3")));
        // create note. Create.NOTE depends on Update.WRITE_NOTE_METADATA.
        Create.NOTE.execute(note2);
    }

    public static void discardedElement() {
        System.out.println("\nTest discardedElement:\n");
        // test that DiscardedElement instantiates with no parameters.
        System.out.println("DiscardedElement de = new DiscardedElement()");
        // update: default constructor no longer valid
//        DiscardedElement de = new DiscardedElement();
//        discardedElementHelper(de);

        // Constructor test: Category
        // update: Category no longer valid constructor parameter.
        System.out.println("\nDiscardedElement cat_discard = new DiscardedElement(new Category(new MetaPath(Paths.get(\"category1\", \"category2\"));");
//        discardedElementHelper(new DiscardedElement(new Category(new MetaPath(Paths.get(".category1", ".category2")))));

        // Constructor test: MetaPath
        System.out.println("\nDiscardedElement m_discard = new DiscardedElement(new MetaPath(Paths.get(\".cat1\", \".cat2\", \".260322_0100_00.txt\"));");
        discardedElementHelper(new DiscardedNote(new MetaPath(Paths.get(".cat1", ".cat2", ".260322_0100_00.txt"))));

        // Constructor test: ViewPath
        System.out.println("\nDiscardedElement m_discard = new DiscardedElement(new ViewPath(Pathsget(\"cat1\", \"cat2\", \"260322_0100_00.txt\"));");
        discardedElementHelper(new DiscardedNote(new ViewPath(Paths.get("cat1", "cat2", "260322_0100_00.txt"))));

        // Constructor test: MutableNote
        System.out.println("\nDiscardedElement mn = new DiscardedElement new MutableNote...");
        Note mn = new Note(new MetaPath(Paths.get(".cat1", ".cat2", ".260322_0100_00.txt")))
                .setTitle("DiscardedElement test MutableNote constructor")
                .setContent("some content")
                .setTo(new HashSet<>(Arrays.asList("to1", "to2", "to3")));
        discardedElementHelper(new DiscardedNote(mn));




    }
    private static void discardedElementHelper(DiscardedNote de) {
        // test booleans (should both be false)
        // these boolean methods have been removed
//        System.out.println("isCategory: " + de.isCategory());
//        System.out.println("isNote: " + de.isNote());
        // test getMetaPath() & getViewPath():
        System.out.println("getMetaPath(): " + de.getMetaPath());
        System.out.println("getViewPath(): " + de.getViewPath());
        // test getOriginalMetaPath() & getOriginalViewPath().
        // these methods have been removed.
//        System.out.println("origninal MetaPath: " + de.getNewLocationMetaPath());
//        System.out.println("origninal ViewPath: " + de.getNewLocationViewPath());
    }

    public static Note propUtils_mn = new Note(new MetaPath(Paths.get(".PropUtils", ".260426_1049_00.txt")))
        .setTitle("Test.propUtils_writeNote()")
        .setContent("This is the content of a MutableNote. The lenght of the comment must be long enough to simulate a real note. People who use Aside ntoes will be reasearching various disciplines and will be interested in the zettelkasten method of note taking and note organization. The software used must be reliable. It must made with care, and is to be used as a vehicle for the zettlekasten system of note taking")
        .setTo(new HashSet<>(Arrays.asList("260417_1723_00.txt", "260417_1724_00.txt", "260417_1725_00.txt", "260417_1726_00.txt", "260417_1727_00.txt", "260417_1728_00.txt", "260417_1729_00.txt")))
        .setFrom(new HashSet<>(Arrays.asList("260417_1725_00.txt", "260417_1726_00.txt", "260417_1727_00.txt", "260417_1728_00.txt","260417_1729_00.txt", "260417_1730_00.txt", "260417_1731_00.txt", "260417_1732_00.txt")))
        .setTags(new HashSet<>(Arrays.asList("tag1",  "tag2", "tag3")));

    public static void propUtils_writeNote() {
//        Category c = new Category(new MetaPath(Paths.get(".PropUtils")));
//        Create.CATEGORY.execute(c);
        PropUtils.writeNote(propUtils_mn);
    }
    public static DiscardedNote propUtils_de = new DiscardedNote(propUtils_mn).setMessage("this is a test of the DiscardedElement message");
    public static void propUtils_writeDiscardedElement() {
        PropUtils.writeDiscardedNote(propUtils_de);
    }

    public static void propUtils_writeNoteViewPath() {
        Note mn = new Note(new MetaPath(Paths.get(".PropUtils", ".260420_1059_01.txt")))
                .setTitle("Test.propUtils_writeNoteViewPath()")
                .setContent("This is a test of propUtils.writeNote_ViewPath(). The sonctent has to simulate the length a real user would write in the process of writing zettlekasten notes for a research paper or a thesis. Ideally a user would be using Aside while researching, and would not call this method, but a higher level abstraction in the form of a command-line UI system. I hope to provide an elegant yet evergreen and portable solution")
                .setTo(new HashSet<>(Arrays.asList("260417_1723_00.txt", "260417_1724_00.txt", "260417_1725_00.txt", "260417_1726_00.txt", "260417_1727_00.txt", "260417_1728_00.txt", "260417_1729_00.txt")))
                .setFrom(new HashSet<>(Arrays.asList("260417_1725_00.txt", "260417_1726_00.txt", "260417_1727_00.txt", "260417_1728_00.txt","260417_1729_00.txt", "260417_1730_00.txt", "260417_1731_00.txt", "260417_1732_00.txt")))
                .setTags(new HashSet<>(Arrays.asList("tag1",  "tag2", "tag3")));

        // mn should now be written to the the ViewPath, and not to the MetaPath.
        PropUtils.writeNote_ViewPath(mn);
        // successful.
    }

    public static void propUtils_writeDiscardedElementViewPath() {
        Note mn = new Note(new MetaPath(Paths.get(".PropUtils", ".260420_1059_02.txt")))
                .setTitle("Test.propUtils_writeNoteViewPath()")
                .setContent("This is a test of propUtils.writeNote_ViewPath(). The sonctent has to simulate the length a real user would write in the process of writing zettlekasten notes for a research paper or a thesis. Ideally a user would be using Aside while researching, and would not call this method, but a higher level abstraction in the form of a command-line UI system. I hope to provide an elegant yet evergreen and portable solution")
                .setTo(new HashSet<>(Arrays.asList("260417_1723_00.txt", "260417_1724_00.txt", "260417_1725_00.txt", "260417_1726_00.txt", "260417_1727_00.txt", "260417_1728_00.txt", "260417_1729_00.txt")))
                .setFrom(new HashSet<>(Arrays.asList("260417_1725_00.txt", "260417_1726_00.txt", "260417_1727_00.txt", "260417_1728_00.txt","260417_1729_00.txt", "260417_1730_00.txt", "260417_1731_00.txt", "260417_1732_00.txt")))
                .setTags(new HashSet<>(Arrays.asList("tag1",  "tag2", "tag3")));
        DiscardedNote de = new DiscardedNote(mn).setMessage("This is a test of propUtils.writeDiscardedNote_ViewPath(). The intention is to test this method to see if MutableNote propUtils_mn can be converted into a discarded element, and written to the ViewPath.");
        // write to ViewPath:
        PropUtils.writeDiscardedNote_ViewPath(de);
        // successful
    }

    public static void propUtils_readNote() {
        Note mn = new Note(new MetaPath(Paths.get(".PropUtils", ".260417_1722_20.txt")));
        // stdout before PropUtils.readNote(mn):
        System.out.println("BEFORE:\n" + mn);
        PropUtils.retrieveNote(mn);
        System.out.println("AFTER:\n" + mn);
    }

    public static void propUtils_readDiscardedElement() {
        Note mn = new Note(new MetaPath(Paths.get(".PropUtils", ".260417_1722_20.txt")));
        DiscardedNote de = new DiscardedNote(mn).setMessage("This is a test of propUtils_readDiscardedElement()");
        System.out.println("BEFORE:\n" + de);
        PropUtils.retrieveDiscardedNote(de);
        System.out.println("AFTER:\n" + de);
    }

    public static void abstractNote_anti_redundant_naming() {
        // create several notes at once to see if names are duplicated:
        System.out.println("ANTI-REDUNDANT FILE NAMING:");
        Category anti_redundant = new Category(new MetaPath(Paths.get(".anti_redundant")));
        Category anti_redundant2 = new Category(new MetaPath(Paths.get(".anti_redundant2")));
        Create.CATEGORY.execute(anti_redundant);
        Create.CATEGORY.execute(anti_redundant2);

        /* Write a note from the future. generateNewNoteName() should
        avoid duplicating these filenames: */
        Note mn0 = new Note(new MetaPath(Paths.get(".260426_1822_00.txt")))
                .setTitle("Note from future");
        PropUtils.writeNote(mn0);
        Note mn00 = new Note(new MetaPath(Paths.get(".260426_1822_04.txt")))
                .setTitle("Note from future");
        PropUtils.writeNote(mn00);
        Note mn1 = new Note(anti_redundant)
                .setTitle("Test.abstractNote_anti_redundant_naming() Note1")
                .setContent("Testing AbstractNote.generateNoteName() redundancy");
        System.out.println("mn1: " + mn1.getMetaPath());

        Note mn2 = new Note(anti_redundant2)
                .setTitle("Test.abstractNote_anti_redundant_naming() Note2")
                .setContent("Testing AbstractNote.generateNoteName() redundancy");
        System.out.println("mn2: " + mn2.getMetaPath());

        Note mn3 = new Note(anti_redundant)
                .setTitle("Test.abstractNote_anti_redundant_naming() Note3")
                .setContent("Testing AbstractNote.generateNoteName() redundancy");
        System.out.println("mn3: " + mn3.getMetaPath());

        Note mn4 = new Note(anti_redundant2)
                .setTitle("Test.abstractNote_anti_redundant_naming() Note4")
                .setContent("Testing AbstractNote.generateNoteName() redundancy");
        System.out.println("mn4: " + mn4.getMetaPath());

        Note mn5 = new Note(anti_redundant)
                .setTitle("Test.abstractNote_anti_redundant_naming() Note5")
                .setContent("Testing AbstractNote.generateNoteName() redundancy");
        System.out.println("mn5: " + mn5.getMetaPath());

        Note mn6 = new Note(anti_redundant)
                .setTitle("Test.abstractNote_anti_redundant_naming() Note6")
                .setContent("Testing AbstractNote.generateNoteName() redundancy");
        System.out.println("mn6: " + mn6.getMetaPath());

        Note mn7 = new Note(anti_redundant)
                .setTitle("Test.abstractNote_anti_redundant_naming() Note7")
                .setContent("Testing AbstractNote.generateNoteName() redundancy");
        System.out.println("mn7: " + mn7.getMetaPath());

        Note mn8 = new Note(anti_redundant)
                .setTitle("Test.abstractNote_anti_redundant_naming() Note8")
                .setContent("Testing AbstractNote.generateNoteName() redundancy");
        System.out.println("mn8: " + mn8.getMetaPath());

        Note mn9 = new Note(anti_redundant)
                .setTitle("Test.abstractNote_anti_redundant_naming() Note9")
                .setContent("Testing AbstractNote.generateNoteName() redundancy");
        System.out.println("mn9: " + mn9.getMetaPath());

        Note mn10 = new Note(anti_redundant)
                .setTitle("Test.abstractNote_anti_redundant_naming() Note10")
                .setContent("Testing AbstractNote.generateNoteName() redundancy");
        System.out.println("mn10: " + mn10.getMetaPath());
        // its not necessary to create them.
//        Create.NOTE.execute(mn1);
//        Create.NOTE.execute(mn2);
//        Create.NOTE.execute(mn3);
//        Create.NOTE.execute(mn4);
    }

    public static void settings_LineWidth() {
        System.out.print("\nDefault Settings.getLineWidth().stdout(): ");
        System.out.println(Settings.getLineWidth().stdout());
        System.out.print("Default Settings.getLineWidth().file(): ");
        System.out.println(Settings.getLineWidth().file());
        System.out.println("Does the settings File exists? " + Settings.getLineWidth().settingsFileExists());
        System.out.println("\nChanging settings...");
        Settings.getLineWidth().setStdout(80);
        System.out.println("new Settings.getLineWidth().stdout(): " + Settings.getLineWidth().stdout());
        System.out.println("Settings file exists: " + Settings.getLineWidth().settingsFileExists());
        Settings.getLineWidth().setFile(80);
        System.out.println("new Settings.getLineWidth().file(): " + Settings.getLineWidth().file());
        System.out.println("Settings file exists: " + Settings.getLineWidth().settingsFileExists());
    }

    public static void setting_TempSettings() {
        System.out.println("\nTempSettings Tests:");
        System.out.println("create and set any property:");
        TempSettings.set("favorite.note", "260503_2114_12.txt");
        System.out.println("Set favorite.note to 260503_2114_12.txt " + TempSettings.get("favorite.note", null));
        System.out.println("use .setInt()...");
        TempSettings.setInt("linewidth", 81);
        System.out.println("linewidth=" + TempSettings.get("linewidth"));
        System.out.println("getInt(linewidth): " + TempSettings.getInt("linewidth"));
//        TempSettings.setPersist("newkey", "miney");
        TempSettings.persist();
    }

    public static void notePropsWriterTest() {
        // test the new class, NotePropsWriter
        Note n = new Note(new MetaPath(Paths.get(".PropUtils")))
                .setTitle("test noteProprsWriterTest. May 26, 2026")
                .setContent("This is supposed to be a test for the new note writer, called 'NotePropsWriter', which will be changed to 'NoteWriter', The old NoteWriter will be deleted.")
                .addTo(".260526_1830_26.txt",".260526_1830_27.txt",".260526_1830_28.txt",".260526_1830_29.txt",".260526_1830_30.txt",".260526_1830_31.txt",".260526_1830_32.txt",".260526_1830_33.txt",".260526_1830_34.txt")
                .addFrom(".260526_1830_33.txt",".260526_1830_34.txt",".260526_1830_35.txt",".260526_1830_26.txt",".260526_1830_27.txt",".260526_1830_28.txt")
                .addTags("Dune", "Harry Potter", "Fux.25")
                .addBibliographies(".b260526_1830_39.txt");
//        NoteWriter npr = new NoteWriter();
        /* update: PropsUtils now uses NotePropsWriter;
        NotePropsWriter has been renamed to NoteWriter. */
        PropUtils.writeNote(n);
    }

    public static void notePropsRetrieverTest() {
        // Test the new class, NotePropsRetriever
        Note n = new Note(new MetaPath(Paths.get(".PropUtils", ".260528_0009_26.txt")));
//        NoteRetriever npr = new NoteRetriever();
        /* updated: PropUtils now uses NotePropsRetriever;
        NotePropsRetriever has been renamed to NoteRetriever. */
        PropUtils.retrieveNote(n);
        System.out.println(Pretty.formatNote4ViewPath(n, Settings.getLineWidth().file()));
        System.out.println(n);
    }

    public static void utfTest() {
        // testing how \u220e and \u001f look:
        System.out.println("\nUTF CHARACTERS: ");
        System.out.println("\\u220e: ∎");
        System.out.println("\\u001f: \u001f");
        System.out.println("\\u2591: \u2591"); // ░
        System.out.println("\\u2592: \u2592"); // ▒
        System.out.println("\\u2593: \u2593"); // ▓
        System.out.println("\\u2588: \u2588"); // █
        System.out.println("\\u2584: \u2584"); // ▄
        System.out.println("\\u2580: \u2580"); // ▀
        System.out.println("\\u25A0: \u25A0"); // ■
        System.out.println("\\u2AD8: \u2AD8"); // ⫘
        System.out.println("\\u25FC: \u25FC"); // ◼
        System.out.println("\\u2663: \u2663"); // ♣
        System.out.println("\\u2580: \u2580"); // ▀

    }

    public static void authorTest() {
        Author author1 = new Author("lastName", "prefix", "firstName", "middlename1 middlename2", "initials", "postNominals");
        System.out.println("Author1: " + author1.getDisplayName());
        System.out.println("Author1, propertiesFormattedString: " + author1.getPropertiesDelimitedString());

        Author author2 = new Author("Osho", "", "", "", "", "");
        System.out.println("Author2: " + author2.getDisplayName());
        System.out.println("Author2, propertiesFormattedString: " + author2.getPropertiesDelimitedString());

        Author author3 = new Author("Barathian", "King", "Geoffery", "", "", "The Cruel");
        System.out.println("Author3: " + author3.getDisplayName());
        System.out.println("Author3, propertiesFormattedString: " + author3.getPropertiesDelimitedString());

        Author kingGeoffery = new Author(author3.getPropertiesDelimitedString());
        System.out.println("kingGeoffery: " + kingGeoffery.getDisplayName());
        System.out.println("kingGeoffery, propertiesFormattedString: " + kingGeoffery.getPropertiesDelimitedString());

        Author tombstone = new Author("tombstone");
        System.out.println("tombstone: " + tombstone.getDisplayName());
        System.out.println("tombstone, propertiesFormattedString: " + tombstone.getPropertiesDelimitedString());

    }


}