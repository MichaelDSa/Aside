package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePathElement.AbstractNote;
import com.github.michaeldsa.aside.AsidePathElement.DiscardedNote;
import com.github.michaeldsa.aside.AsidePathElement.Note;

public class PropUtils {
    // reader classes
    private static final NoteRetriever note_r = new NoteRetriever();
    private static final DiscardedNoteRetriever discarded_r = new DiscardedNoteRetriever();
    // writer classes
    private static final NoteWriter note_w = new NoteWriter();
    private static final DiscardedNoteWriter discarded_w = new DiscardedNoteWriter();

    // readers:
    public static void retrieveNote(Note mn) {
        note_r.retrieve(mn);
    }
    public static void retrieveDiscardedNote(DiscardedNote de) {
        discarded_r.retrieve(de);
    }

    // writers (MetaPath & ViewPath):
    public static void writeNote(AbstractNote abstractNote) {
        note_w.write(abstractNote);
    }
    public static void writeDiscardedNote(DiscardedNote de) {
        discarded_w.write(de);
    }

    // writers (ViewPath only):
    public static void writeNote_ViewPath(AbstractNote abstractNote) {
        note_w.writeToViewPath(abstractNote);
    }
    public static void writeDiscardedNote_ViewPath(DiscardedNote de) {
        discarded_w.writeToViewPath(de);
    }
}
