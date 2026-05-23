package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePathElement.AbstractNote;
import com.github.michaeldsa.aside.AsidePathElement.DiscardedNote;
import com.github.michaeldsa.aside.AsidePathElement.MutableNote;

public class PropUtils {
    // reader classes
    private static final NoteRetriever note_r = new NoteRetriever();
    private static final DiscardedElementRetriever discarded_r = new DiscardedElementRetriever();
    // writer classes
    private static final NoteWriter note_w = new NoteWriter();
    private static final DiscardedElementWriter discarded_w = new DiscardedElementWriter();

    // readers:
    public static void retrieveNote(MutableNote mn) {
        note_r.retrieve(mn);
    }
    public static void retrieveDiscardedElement(DiscardedNote de) {
        discarded_r.retrieve(de);
    }

    // writers (MetaPath & ViewPath):
    public static void writeNote(AbstractNote abstractNote) {
        note_w.write(abstractNote);
    }
    public static void writeDiscardedElement(DiscardedNote de) {
        discarded_w.write(de);
    }

    // writers (ViewPath only):
    public static void writeNote_ViewPath(AbstractNote abstractNote) {
        note_w.writeToViewPath(abstractNote);
    }
    public static void writeDiscardedElement_ViewPath(DiscardedNote de) {
        discarded_w.writeToViewPath(de);
    }
}
