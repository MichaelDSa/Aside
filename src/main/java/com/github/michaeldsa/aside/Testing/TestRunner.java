package com.github.michaeldsa.aside.Testing;

import com.github.michaeldsa.aside.AsidePathElement.MutableNote;

public class TestRunner {
    public static class MutableNoteTests {
        public static void run() {
            System.out.printf("%n '%s' %n","MutableNote Tests:");
            System.out.printf("%n'%s'%n", "test empty note:");
            MutableNote mn = new MutableNote(Test.cc.getCurrentCategory());
            Test.MutableNoteTest.testMutableNoteToString(mn); // success
            System.out.printf("%n%s", "test note with assigned fields:");
            mn.setTitle("Test MutableNote")
                    .setContent("Test content MutableNote")
                    .addTo("251212_1025_01.txt")
                    .addFrom("251211_0700_23.txt")
                    .addTags("self", "programming", "project");

            System.out.printf("%n%s%n", "TEST MutableNote CONSTRUCTORS");
            Test.MutableNoteTest.testMutableNoteConstructors();

            System.out.printf("%n%s%n", "VARIOUS PRINT TESTS:");
            Test.MutableNoteTest.testMutableNoteToString(mn);
            Test.MutableNoteTest.testMutableNotePrintTitle(mn);
            Test.MutableNoteTest.testMutableNotePrintContent(mn);
            Test.MutableNoteTest.testMutableNotePrintTo(mn);
            Test.MutableNoteTest.testMutableNotePrintFrom(mn);
            Test.MutableNoteTest.testMutableNotePrintTags(mn);
            Test.MutableNoteTest.testMutableNoteprintNote(mn);
            Test.MutableNoteTest.testMutableNotePrintNoteAll(mn);
            Test.MutableNoteTest.testGetMetaPath(mn);
            Test.MutableNoteTest.testGetViewPath(mn);
            Test.MutableNoteTest.testGetCategory(mn);
            Test.MutableNoteTest.testGetParentMetaPath(mn);
            Test.MutableNoteTest.testGetParentViewPath(mn);
            Test.MutableNoteTest.testSetCategory(mn);
            Test.MutableNoteTest.testParentsMatch(mn);
            Test.MutableNoteTest.testGetMetaPathNoteName(mn);
            Test.MutableNoteTest.testGetViewPathNoteName(mn);
            Test.MutableNoteTest.can_content_mutate_by_reference();
            Test.MutableNoteTest.can_Set_fields_mutate_by_reference();
            Test.MutableNoteTest.testGetTo(mn);
            Test.MutableNoteTest.testGetFrom(mn);
            Test.MutableNoteTest.testGetTags(mn);
            Test.MutableNoteTest.testGetTitle(mn);
            Test.MutableNoteTest.testGetContent(mn);
            Test.MutableNoteTest.testMethodChainingSetters();
            Test.MutableNoteTest.testAddTo(mn);
            Test.MutableNoteTest.testAddFrom(mn);
            Test.MutableNoteTest.testAddTags(mn);
            Test.MutableNoteTest.testRemoveTo(mn);
            Test.MutableNoteTest.testRemoveFrom(mn);
            Test.MutableNoteTest.testRemoveTags(mn);
            Test.MutableNoteTest.testHasPreviousState(mn);
            Test.MutableNoteTest.testGetPreviousState(mn);
            Test.MutableNoteTest.testGetAsImmutableNote(mn);
            Test.MutableNoteTest.testX_contains(mn);
            Test.MutableNoteTest.testPrependAppendToTitleContent(mn);
            Test.MutableNoteTest.testEqualsAndHashCode(mn);
        }
    }
}
