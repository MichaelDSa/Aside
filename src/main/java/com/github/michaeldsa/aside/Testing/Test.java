package com.github.michaeldsa.aside.Testing;
// I don't have time to learn unit testing,
// so I'm writing this class to test stuff.

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.AsidePathElement.Category;
import com.github.michaeldsa.aside.AsidePathElement.ImmutableNote;
import com.github.michaeldsa.aside.AsidePathElement.MutableNote;
import com.github.michaeldsa.aside.CurrentCategory;
import com.github.michaeldsa.aside.PathKeeper;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public static class MutableNoteTest {

        // Test constructors:
        public static void testMutableNoteConstructors() {
            MutableNote mn_category = new MutableNote(cc.getCurrentCategory());
            MutableNote mn_MetaPath = new MutableNote(cc.getCurrentMetaPath());
            MutableNote mn_ViewPath = new MutableNote(cc.getCurrentViewPath());
            MutableNote mn_MutableNote = new MutableNote(mn_category);
            System.out.println("constructor using Category:");
            System.out.println(mn_category);
            System.out.println("instantiate using MetaPath:");
            System.out.println(mn_MetaPath);
            System.out.println("instantiate using ViewPath:");
            System.out.println(mn_ViewPath);
            System.out.println("instantiatde using MutableNote:");
            System.out.println(mn_MutableNote);
        }
        // Test .toString()
        public static void testMutableNoteToString(MutableNote mn) {
            String heading = mn.hasPreviousState() ? "Print MutableNote with prevState: " : "Print MutableNote:";
            System.out.printf("%n'%s'%n", heading);
            System.out.println(mn);
        }
        // Test print methods:
        public static void testMutableNotePrintTitle(MutableNote mn) {
            System.out.printf("%n'%s'%n", "printTitle():");
            mn.printTitle();
        }
        public static void testMutableNotePrintContent(MutableNote mn) {
            System.out.printf("%n'%s'%n", "printContent():");
            mn.printContent();
        }
        public static void testMutableNotePrintTo(MutableNote mn) {
            System.out.printf("%n'%s'%n", "printTo():");
            mn.printTo();
        }
        public static void testMutableNotePrintFrom(MutableNote mn) {
            System.out.printf("%n'%s'%n", "printFrom():");
            mn.printFrom();
        }
        public static void testMutableNotePrintTags(MutableNote mn) {
            System.out.printf("%n'%s'%n", "printTags():");
            mn.printTags();
        }
        public static void testMutableNoteprintNote(MutableNote mn) {
            System.out.printf("%n'%s'%n", "printNote()");
            mn.printNote();
        }
        public static void testMutableNotePrintNoteAll(MutableNote mn) {
            System.out.printf("%n'%s'%n", "printNoteAll()");
            mn.printNoteAll();
        }
        // Test getters from AsidePathElement:
        public static void testGetMetaPath(MutableNote mn) {
            MetaPath mp = mn.getMetaPath();
            System.out.printf("%n%s%n     %s%n","MutableNote.getMetaPath():", mp);
        }
        public static void testGetViewPath(MutableNote mn) {
            ViewPath vp = mn.getViewPath();
            System.out.printf("%n%s%n     %s%n", "MubableNote.getVeiwPath():",vp);
        }
        // Test getters, setters & utilities for parent category:
        public static void testGetCategory(MutableNote mn) {
            Category c = mn.getCategory();
            System.out.printf("%n%s%n     %s%n", "MutableNote.getCategory():", c);
        }
        public static void testGetParentMetaPath(MutableNote mn) {
            MetaPath mp = mn.getParentMetaPath();
            System.out.printf("%n%s%n     %s%n","MutableNote.getParentMetaPath();", mp);
        }
        public static void testGetParentViewPath(MutableNote mn) {
            ViewPath vp = mn.getParentViewPath();
            System.out.printf("%n%s%n     %s%n", "MutableNote.getParentViewPath():", vp);
        }
        public static void testSetCategory(MutableNote mn) {
            MutableNote m = new MutableNote(mn);
            Path p = Paths.get(".test", ".path");
            Category c = new Category(new MetaPath(p));
            System.out.printf("%n%s%n     %s%n", "BEFORE MutableNote.setCategory():", m.getCategory());
            m.setCategory(c);
            System.out.printf("%s%n     %s%n", "AFTER MutableNote.setCategory():", m.getCategory());
            System.out.printf("%s%n     %s%n     %s%n", "Also - getMetaPath() and getViewPath():", m.getMetaPath(), m.getViewPath());
        }
        public static void testParentsMatch(MutableNote mn) {
            MutableNote m = new MutableNote(mn);
            System.out.println("\nMutableNote.parent is not assigned:");
            testParentsMatchPrinter(m);

            MetaPath mp = m.getParentMetaPath();
            m.setCategory(new Category(mp));
            System.out.println("\nMutableNote.parent is assigned (parent is assigned to metaPath):");
            testParentsMatchPrinter(m);

            m.setCategory(new Category(new MetaPath(Paths.get(".test", ".path"))));
            System.out.println("\nMutableNote.Patent is assigned to different parent:");
            testParentsMatchPrinter(m);

        }
        public static void testParentsMatchPrinter(MutableNote mn) {
            System.out.printf("%s%n     %s: %s%n     %s: %s%n     %s: %s%n         %s: %s%n"
                    ,"MutableNote.parentsMatch()"
                    ,"MetaPath parent:"
                    ,mn.getMetaPath()
                    ,"ViewPath parent:"
                    ,mn.getViewPath()
                    ,"Category parent:"
                    ,mn.getCategory()
                    ,".parentsMatch():"
                    ,mn.parentsMatch()
            );
        }
        public static void testGetMetaPathNoteName(MutableNote mn) {
            MetaPath mname = mn.getMetaPathNoteName();
            System.out.printf("%n%s%n     %s%n", "MutableNote.getMetaPathNoteName():", mname);
        }
        public static void testGetViewPathNoteName(MutableNote mn) {
            ViewPath vname = mn.getViewPathNoteName();
            System.out.printf("%n%s%n     %s%n", "MutableNote.getViewPathNoteName():", vname);
        }
        public static void testGetTo(MutableNote mn) {
            Set<String> reference = mn.getTo();
            System.out.println("\nMutableNote.getTo():");
            System.out.printf("%s: %s%n%s: %s%n","new reference: ", reference, "mn.getTo(): ", mn.getTo());
            reference.add("invalid_addition");
            System.out.println("reference.equals(mn.getTo()) after adding element to new reference: " + reference.equals(mn.getTo()));
            System.out.println("NOTE: MutableNote.to should not mutate by reference.");
        }
        public static void testGetFrom(MutableNote mn) {
            Set<String> reference = mn.getFrom();
            System.out.println("\nMutableNote.getFrom():");
            System.out.printf("%s: %s%n%s: %s%n","new reference: ", reference, "mn.getFrom(): ", mn.getFrom());
            reference.add("invalid_addition");
            System.out.println("reference.equals(mn.getFrom()) after adding element from new: " + reference.equals(mn.getFrom()));
            System.out.println("NOTE: MutableNote.from should not mutate by reference.");
        }
        public static void testGetTags(MutableNote mn) {
            Set<String> reference = mn.getTags();
            System.out.println("\nMutableNote.getTags():");
            System.out.printf("%s: %s%n%s: %s%n","new reference: ", reference, "mn.getTags(): ", mn.getTags());
            reference.add("invalid_addition");
            System.out.println("reference.equals(mn.getTags()) after adding element from new: " + reference.equals(mn.getTags()));
            System.out.println("NOTE: MutableNote.tags should not mutate by reference.");
        }

        public static void testGetTitle(MutableNote mn) {
            String title = mn.getTitle();
            System.out.printf("%n%s%n     %s: %s%n", "MutableNote.getTitle():", "mn.getTitle() returns", title);
        }
        public static void testGetContent(MutableNote mn) {
            String content = mn.getContent();
            System.out.printf("%n%s%n     %s: %s%n", "MutableNote.getContent():", "mn.getContent() returns", content);
        }
        public static void testMethodChainingSetters() {
            System.out.println("\nTEST METHOD CHAINGIN SETTERS: .setTo, .setFrom, .setTags, .setTitle, .setContent");

            System.out.println("Assigning new MutableNote, `chainsetter`...");
            MutableNote chainsetter = new MutableNote(Test.cc.getCurrentCategory());

            // prepare Sets & Strings to assign
            System.out.println("Assigning variables for to, from, tags, title, content instance variables...");
            Set<String> to = new HashSet<>(Arrays.asList("000000_0000_00.txt", "111111_1111_11.txt", "222222_2222_22.txt", "BAD ELEMENT"));
            Set<String> from = new HashSet<>(Arrays.asList("333333_3333_33.txt", "444444_4444_44.txt","BAD ELEMENT"));
            Set<String> tags = new HashSet<>(Arrays.asList("tag1", "tag2", "tag3", ".BAD ELEMENT", "000000_0000_00.txt"));
            String title = "Chainsetter title";
            String content = "Chainsetter content";
            System.out.println("local var, `to`: " + to);
            System.out.println("local var, `from`: " + from);
            System.out.println("local var, `tags`: " + tags);
            System.out.println("local var, `title`: " + title);
            System.out.println("local var, `content`: " + content);

            // use method chaining to assign above vars to MutableNote counterparts:
            System.out.println("using method-chaining setters to assign instances (like a builder would)...");
            chainsetter
                    .setTo(to)
                    .setFrom(from)
                    .setTags(tags)
                    .setTitle(title)
                    .setContent(content);
            System.out.printf("%s: %s%n", ".toString() of chainsetter", chainsetter);
            System.out.println("chainsetter.getTitle(): " + chainsetter.getTitle());
            System.out.println("chainsetter.getContent(): " + chainsetter.getContent());
            System.out.println("chainsetter.getTo(): " + chainsetter.getTo());
            System.out.println("chainsetter.getFrom(): " + chainsetter.getFrom());
            System.out.println("chainsetter.getTags(): " + chainsetter.getTags());

            // reassign all title, content, to, from, tags:
            // first create new variables
            System.out.println("\ncreating and assigning new variables to replace title, content, to, from, tags...");
            String replace_title = "Replacement title";
            String replace_content = "Replacement content";
            Set<String> replace_to = new HashSet<>(Arrays.asList("123456_1234_12.txt", "654321_4321_21.txt"));
            Set<String> replace_from = new HashSet<>(Arrays.asList("234567_2345_23.txt", "765432_5432_32.txt"));
            Set<String> replace_tags = new HashSet<>(Arrays.asList("tag10", "tag11", "tag12"));

            System.out.println("local var, `replace_title`: " +replace_title);
            System.out.println("local var, `replace_content`: " + replace_content);
            System.out.println("local var, `replace_to`: " + replace_to);
            System.out.println("local var, `replace_from`: " + replace_from);
            System.out.println("local var, `replace_tags`: " + replace_tags);

            // reassign via method chaining setters:
            System.out.println("\nReassigning title, content, to, from, tags via method chaining setters...");
            chainsetter
                    .setTitle(replace_title)
                    .setContent(replace_content)
                    .setTo(replace_to)
                    .setFrom(replace_from)
                    .setTags(replace_tags);
            System.out.printf("%s: %s%n", ".toString() of chainsetter", chainsetter);
            System.out.println("chainsetter.getTitle(): " + chainsetter.getTitle());
            System.out.println("chainsetter.getContent(): " + chainsetter.getContent());
            System.out.println("chainsetter.getTo(): " + chainsetter.getTo());
            System.out.println("chainsetter.getFrom(): " + chainsetter.getFrom());
            System.out.println("chainsetter.getTags(): " + chainsetter.getTags());

            // CONCLUSION STATEMENT:
            System.out.println("""
                    CONCLUSION: \
                    
                    - All bulk setters of Set<String> types work;\
                    
                    - method chaining works;\
                    
                    - all elements are filtered;\
                    
                    - setTitle() & setContent() work as expected;\
                    
                    - all setters replace corresponding fields""");

        }

        public static void testAddTo(MutableNote mn) {
            MutableNote m = new MutableNote(mn);
            // adding single element:
            System.out.println("\nTEST .addTo(String...):");
            System.out.println("TEST: ADD SINGLE ELEMENT...");
            System.out.println("Orginal contents of m.to:" + m.getTo());
            System.out.println("Adding '000000_0000_00.txt' to m...");
            m.addTo("000000_0000_00.txt");
            System.out.println("new contents of m.to:" + m.getTo());

            // adding multiple elements with multiple args
            System.out.println("\nAdding multiple elements using varargs...");
            System.out.println("Adding '111111_1111_11.txt' and '222222_2222_22.txt' to m...");
            m.addTo("111111_1111_11.txt", "222222_2222_22.txt");
            System.out.println("new contents of m.to:" + m.getTo());

            // adding a whole set
            System.out.println("\nTest .addTo(Set<String> set):");
            System.out.println("m.to current state: " + m.getTo());
            Set<String> set = new HashSet<>(Arrays.asList("333333_3333_33_.txt", "444444_4444_44.txt", "555555_5555_55.txt", "121212_1212_123.txt", "another_bad_element"));
            System.out.println("Adding new set:");
            System.out.println(set);
            System.out.println("Observe that some elements are invalid.");
            System.out.println("Adding set to m...");
            m.addTo(set);
            System.out.println("new contents of m.to: " + m.getTo());
        }

        public static void testAddFrom(MutableNote mn) {
            MutableNote m = new MutableNote(mn);
            String from1 = "000000_0000_00.txt";
            String from2 = "111111_1111_11.txt";
            String from3 = ".222222_2222_22.txt";
            String badFrom1 = "bad element";
            String badFrom2 = "..999999_9999_99.txt";
            Set<String> set = new HashSet<>(Arrays.asList(from1, from2, from3, badFrom1, badFrom2));

            System.out.println("\nTEST .addFrom(String...):");
            System.out.println("m.from current state: " + m.getFrom());
            System.out.println("Adding elements " + from1 + ", " + from2 + ", " + from3 + ", " + badFrom1 + ", " + badFrom2 + " to m.from...");
            m.addFrom(from1, from2, from3, badFrom1, badFrom2);
            System.out.println("new contents of m.from: " + m.getFrom());

            System.out.println("\nTEST .addFrom(Set<String> set):");
            MutableNote m1 = new MutableNote(mn);
            System.out.println("\nnew MutableNote, m1.from current state: " + m1.getFrom());
            System.out.println("Adding set: '" + set + "' to m1.from...");
            m1.addFrom(set);
            System.out.println("new contents of m1.from: " + m1.getFrom());
        }

        public static void testAddTags(MutableNote mn) {
            MutableNote m = new MutableNote(mn);
            String tag1 = "tag1";
            String tag2 = "tag2";
            String tag3 = "tag3";
            String tag4 = ".invalid";
            String tag5 = "000000_0000_00.txt";

            System.out.println("\nTEST .addTags(String...):");
            System.out.println("m.tag current state: " + m.getTags());
            System.out.println("Adding elements, 'tag1', 'tag2', 'tag3', '.invalid', and '000000_0000_00.txt' to m.tag...");
            m.addTags(tag1, tag2, tag3, tag4, tag5);
            System.out.println("New state of m.tag: " + m.getTags());

            System.out.println("\nTEST .addTags(Set<String> set):");
            MutableNote m1 = new MutableNote(mn);
            System.out.println("new MutableNote, m1.tag current state: " + m1.getTags());
            System.out.println("Adding elements, 'tag1', 'tag2', 'tag3', '.invalid', and '000000_0000_00.txt' to m1.tag...");
            Set<String> set = new HashSet<>(Arrays.asList(tag1, tag2, tag3, tag4, tag5));
            m1.addTags(set);
            System.out.println("New state of m1.tag: " + m1.getTags());
        }

        public static void testRemoveTo(MutableNote mn) {
            MutableNote m = new MutableNote(mn);
            String to1 = "000000_0000_00.txt";
            String to2 = "100000_1000_01.txt";
            String to3 = "200000_2000_02.txt";
            String to4 = "300000_3000_03.txt";
            String to5 = "400000_4000_04.txt";
            String to6 = "500000_5000_05.txt";
            String to7 = "600000_6000_06.txt";
            String to8 = "..123456_0000_00.txt";
            String to9 = "bad-element";
            Set<String> set = new HashSet<>(Arrays.asList(to1,to2,to3,to4,to5,to6,to7,to8,to9));
            m.addTo(set);
            System.out.println("\nTEST .removeTo(String...):");
            System.out.println("Testing single element removal:");
            System.out.println("m.to current state: " + m.getTo());
            System.out.println("removing single element, '000000_0000_00.txt' from m.to...");
            m.removeTo(to1);
            System.out.println("m.to current state: " + m.getTo());

            System.out.println("\nTesting varargs multi-element removal:");
            System.out.println("m.to current state: " + m.getTo());
            System.out.println("removing elements, '100000_1000_01.txt', '200000_2000_02.txt' & '300000_3000_03.txt' from m.to...");
            m.removeTo(to2,to3,to4);
            System.out.println("m.to current state: " + m.getTo());

            System.out.println("\nTesting removal by Set<String>:");
            System.out.println("resetting m.to to orginal state...");
            m.addTo(set);
            System.out.println("m.to current state: " + m.getTo());
            System.out.println("removing from m.to, the follwing set: " + set);
            m.removeTo(set);
            System.out.println("m.to current state: " + m.getTo());
        }

        public static void testRemoveFrom(MutableNote mn) {
            MutableNote m = new MutableNote(mn);
            String from1 = "000000_0000_00.txt";
            String from2 = "100000_1000_01.txt";
            String from3 = "200000_2000_02.txt";
            String from4 = "300000_3000_03.txt";
            String from5 = "400000_4000_04.txt";
            String from6 = "500000_5000_05.txt";
            String from7 = "600000_6000_06.txt";
            String from8 = "..123456_0000_00.txt";
            String from9 = "bad-element";
            Set<String> set = new HashSet<>(Arrays.asList(from1,from2,from3,from4,from5,from6,from7,from8,from9));

            System.out.println("\nTEST .removeFrom(String...):");
            System.out.println("existing data in m.from: " + m.getFrom());
            System.out.println("adding to m.from, this set: " + set);
            m.addFrom(set);
            System.out.println("m.from current state: " + m.getFrom());

            System.out.println("\nTesting single element removal: ");
            System.out.println("removing element, " + from7 + " from m.from...");
            m.removeFrom(from7);
            System.out.println("m.from current state: " + m.getFrom());

            System.out.println("\nTesting varargs multi-element removal: ");
            System.out.println("removing elements, " + from1 + ",  " + from2 + ", " + from3 + " " + "from m.from...");
            m.removeFrom(from1,from2,from3);
            System.out.println("m.from current state: " + m.getFrom());

            System.out.println("\nTesting removal by Set<String>:");
            System.out.println("resetting m.from to original state...");
            m.addFrom(set);
            System.out.println("m.from current state: " + m.getFrom());
            System.out.println("removing from m.from, the following set: " + set);
            m.removeFrom(set);
            System.out.println("m.from current state: " + m.getFrom());
        }

        public static void testRemoveTags(MutableNote mn) {
            MutableNote m = new MutableNote(mn);
            String tag1 = "tag1";
            String tag2 = "tag2";
            String tag3 = "tag3";
            String tag4 = " tag4";
            String tag5 = ".tag5";
            String tag6 = "000000_0000_00.txt";
            Set<String> set = new HashSet<>(Arrays.asList(tag1,tag2,tag3,tag4,tag5,tag6));

            System.out.println("\nTEST .removetags(String...):");
            System.out.println("Adding to m.tags this set: " + set);
            m.addTags(set);
            System.out.println("m.tags current state: " + m.getTags());

            System.out.println("\nTesting single element removal: ");
            System.out.println("removing element, " + tag1 + " from m.tags...");
            m.removeTags(tag1);
            System.out.println("m.tags current state: " + m.getTags());

            System.out.println("\nTesting varargs multi-element removal: ");
            System.out.println("removing elements, " + tag2 + ", " + tag3 + " from m.tags...");
            m.removeTags(tag2,tag3);
            System.out.println("m.tags current state: " + m.getTags());

            System.out.println("\nTesting removal by Set<String>:");
            System.out.println("resetting m.tags to original state...");
            m.addTags(set);
            System.out.println("m.tags current state: " + m.getTags());
            System.out.println("removing from m.tags the following set: " + set);
            m.removeTags(set);
            System.out.println("m.tags current state: " + m.getTags());
        }

        public static void testHasPreviousState(MutableNote mn) {
            MutableNote m = new MutableNote(mn);
            System.out.println("\nTEST .hasPreviousState():");
            System.out.println("mn.hasPreviousState() should return false.");
            System.out.println("\tmn.hasPreviousState(): " + mn.hasPreviousState());
            System.out.println("m.hasPreviousState() should return true.");
            System.out.println("\tm.hasPreviousState(): " + m.hasPreviousState());
        }

        public static void testGetPreviousState(MutableNote mn) {
            // get previous state of arg:
            ImmutableNote in = mn.getPreviousState();

            // arg, mn does not have a previous state. Using the arg
            // as a builder for a MutableNote will create a previous
            // state. The previous state is saved as an ImmutableNote.
            MutableNote m = new MutableNote(mn);
            ImmutableNote in1 = m.getPreviousState();

            System.out.println("\nTEST .getPreviousState():");
            System.out.println("mn.getPreviousState() should return a null reference:");
            System.out.println("\tmn.getPreviousState(): " + in);
            System.out.println("m.getPreviousState() should return an ImmutableNote reference:");
            System.out.println("\tm.getPreviousState(): " + in1);
        }

        public static void testGetAsImmutableNote(MutableNote mn) {
            ImmutableNote in = mn.getAsImmutableNote();
            System.out.println("\nTEST .getAsImmutableNote() - factory method:");
            System.out.println("ImmutableNote in = man.getAsImmutableNote()");
            System.out.println("System.out.println(in): ");
            System.out.println("\t" + in);
        }

        // Test to_contains(), from_contains() and tags_contains()
        public static void testX_contains(MutableNote mn) {
            // create a separate instance version of mn
            MutableNote m = new MutableNote(mn);

            // data to be added to new instance:
            String[ ] to = {"000000_0000_00.txt", "111111_1111_11.txt", "222222_2222_22.txt", "333333_3333_33.txt", "444444_4444_44.txt"};
            String[ ] from = {"555555_5555_55.txt", "666666_6666_66.txt", "777777_7777_77.txt", "888888_8888_88.txt"};
            String[ ] tags = {"tag1", "tag2", "tag3", "tag4", "tag5"};

            Set<String> toSet = new HashSet<>(Arrays.asList(to));
            Set<String> fromSet = new HashSet<>(Arrays.asList(from));
            Set<String> tagSet = new HashSet<>(Arrays.asList(tags));

            m.addTo(toSet).addFrom(fromSet).addTags(tagSet);

            // Tests:
            System.out.println("\nTEST to_contains(String), from_contains(String), tags_contains(String):");
            System.out.println("\nto_contains(String) tests:");
            System.out.println("\tm.to current state: " + m.getTo());
            System.out.println("\t\tm.to contains '" + to[1] + "': " + m.to_contains(to[1]));
            System.out.println("\t\tm.to contains '" + to[4] + "': " + m.to_contains(to[4]));
            System.out.println("\t\tm.to contains 'bad element' " + m.to_contains("bad element"));

            System.out.println("\nfrom_contains(String) tests:");
            System.out.println("\tm.from current state: " + m.getFrom());
            System.out.println("\t\tm.from contains '" + from[0] + "': " + m.from_contains(from[0]));
            System.out.println("\t\tm.from contains '" + from[3] + "': " + m.from_contains(from[3]));
            System.out.println("\t\tm.from contains 'bad element' " + m.from_contains("bad element"));

            System.out.println("\ntags_contains(String) tests:");
            System.out.println("\tm.tags current state: " + m.getTags());
            System.out.println("\t\tm.tags contains '" + tags[0] + "': " + m.tags_contains(tags[0]));
            System.out.println("\t\tm.tags contains '" + tags[3] + "': " + m.tags_contains(tags[3]));
            System.out.println("\t\tm.tags contains '.badelement' " + m.tags_contains("bad element"));
        }

        // test prependToTitle(), appendToTitle(), prependToContent(), appendToContent():
        public static void testPrependAppendToTitleContent(MutableNote mn) {
            MutableNote m = new MutableNote(mn);
            String prepend = "PREPENDED STRING";
            String append = "APPENDED STRING";

            //test
            System.out.println("\nTEST prependToTitle(String):");
            System.out.println("\tm.title current state:\n\t\t " + m.getTitle());
            m.prependToTitle(prepend);
            System.out.println("\tm.title after prepending string, '" + prepend + "':\n\t\t " + m.getTitle());

            System.out.println("\nTEST appendToTitle(String):");
            System.out.println("\tm.title current state:\n\t\t" + m.getTitle());
            m.appendToTitle(append);
            System.out.println("\tm.title after appending string, '" + append + "':\n\t\t" + m.getTitle());

            System.out.println("\nTEST prependToContent(String):");
            System.out.println("\tm.content current state:\n\t\t" + m.getContent());
            m.prependToContent(prepend);
            System.out.println("\tm.content after prepending string, '" + prepend + "':\n\t\t" + m.getContent());

            System.out.println("\nTEST appendToContent(String):");
            System.out.println("\tm.content current state:\n\t\t" + m.getContent());
            m.appendToContent(append);
            System.out.println("\tm.content after appending string, '" + append + "':\n\t\t" + m.getContent());
        }

        // equals, hashcode, toString:
        public static void testEqualsAndHashCode(MutableNote mn) {
            MutableNote m1 = new MutableNote(mn);
            MutableNote m2 = new MutableNote(mn);

            // test equals and hashcode
            System.out.println("\nTEST equals:");
            System.out.println("\tMutableNote m1 & m1 current states:\n\t\tm1:" + m1 + "\n\t\tm2:" + m2);
            System.out.println("\n\tm1.equals(m2): " + (m1.equals(m2)));
            System.out.printf("\t\thashcodes: %s (m1) %s (m2)%n",  m1.hashCode(), m2.hashCode());
            System.out.println("\tm2.equals(m1): " + (m2.equals(m1)));
            System.out.printf("\t\thashcodes: %s (m2) %s (m1)%n",  m2.hashCode(), m1.hashCode());
            System.out.println("\tmn.equals(m1): " + (mn.equals(m1)));
            System.out.printf("\t\thashcodes: %s (mn) %s (m1)%n",  mn.hashCode(), m1.hashCode());
            System.out.println("\tm1.equals(mn): " + (m1.equals(mn)));
            System.out.printf("\t\thashcodes: %s (m1) %s (mn)%n",  m1.hashCode(), mn.hashCode());
            System.out.println("\tmn.equals(m2): " + (mn.equals(m2)));
            System.out.printf("\t\thashcodes: %s (mn) %s (m2)%n",  mn.hashCode(), m2.hashCode());
            System.out.println("\tm2.equals(mn): " + (m2.equals(mn)));
            System.out.printf("\t\thashcodes: %s (m2) %s (mn)%n",  m2.hashCode(), mn.hashCode());

            System.out.println("\n\tAltering m2.to...");
            String addTo = "000000_0000_00.txt";
            m2.addTo(addTo);
            System.out.println("\tm1.to current state:\n\t\t" + m1.getTo());
            System.out.println("\tm2.to current state:\n\t\t" + m2.getTo());
            System.out.printf("\t\thashcodes: %s (m1) %s (m2)%n",  m1.hashCode(), m2.hashCode());
            System.out.println("\tm1.equals(m2): " + (m1.equals(m2)));
            System.out.println("\tm2.equals(m1): " + (m2.equals(m1)));
            System.out.println("\tAltering m1.to so that it's the same as m2.to...");
            m1.setTo(m2.getTo());
            System.out.println("\tm1.to current state:\n\t\t" + m1.getTo());
            System.out.println("\tm2.to current state:\n\t\t" + m2.getTo());
            System.out.printf("\t\thashcodes: %s (m1) %s (m2)%n",  m1.hashCode(), m2.hashCode());
            System.out.println("\tm1.equals(m2): " + (m1.equals(m2)));
            System.out.println("\tm2.equals(m1): " + (m2.equals(m1)));

            System.out.println("\n\t.equals() tests may be more extensive, but would take a lot of time.");

            //toString tests:
            System.out.println("\nTest MutableNote.toString():");
            System.out.println("\tm1.toString(): " + m1.toString());
            System.out.println("\tm2.toString(): " + m2.toString());
        }


        // QUESTIONS:
        // Can content field mutate by reference?
        public static void can_content_mutate_by_reference() {
            MutableNote m = new MutableNote(Test.cc.getCurrentCategory())
                    .setContent("content");

            System.out.println("\n--> QUESTION: CAN CONTENT MUTATE BY REFERENCE?");
            System.out.printf("%s: %s", "Original content of m", m.getContent());
            String append = m.getContent();
            append += " appended_content";
            System.out.printf("%n%s %s","content assigned to `append`; content appended to `append`. append = ", append);
            System.out.printf("%n%s: %s%n","Value of m.getContent()", m.getContent());
        }

        // Can the Set<String> fields: to, from, tags mutate by reference?
        public static void can_Set_fields_mutate_by_reference() {
            MutableNote m = new MutableNote(Test.cc.getCurrentCategory())
                    .addTo("250101_0000_00.txt", "250101_0001_20.txt");

            System.out.println("\n--> QUESTION: CAN SET FIELDS MUTATE BY REFERENCE?");
            System.out.println("                - Test performed only on MutableNote.to");
            System.out.printf("%s: %s","original Set<String>, MutableNote.to: ", m.getTo());
            Set<String> refSet = m.getTo();
            System.out.printf("%n%s: %s","Set, `refSet` == m.getTo(): ", refSet.equals(m.getTo()));
            refSet.add("250202_2222_22.txt");
            System.out.printf("%n%s: %s%n%s: %s","Value added to `refSet`. Value: ", refSet, "value of m.getTo(): ", m.getTo());
            System.out.printf("%n%s: %s%n","refSet == m.getTo(): ", refSet.equals(m.getTo()));
        }
    }


}