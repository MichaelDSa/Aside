package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePathElement.AbstractNote;
import com.github.michaeldsa.aside.AsidePathElement.DiscardedElement;
import com.github.michaeldsa.aside.AsidePathElement.MutableNote;

public class PropUtils {
    // reader classes
    private static final NoteReader note_r = new NoteReader();
    private static final DiscardedElementReader discarded_r = new DiscardedElementReader();
    // writer classes
    private static final NoteWriter note_w = new NoteWriter();
    private static final DiscardedElementWriter discarded_w = new DiscardedElementWriter();

    // readers:
    public static void readNote(MutableNote mn) {
        note_r.read(mn);
    }
    public static void readDiscardedElement(DiscardedElement de) {
        discarded_r.read(de);
    }

    // writers (MetaPath):
    public static void writeNote(AbstractNote abstractNote) {
        note_w.write(abstractNote);
    }
    public static void writeDiscardedElement(DiscardedElement de) {
        discarded_w.write(de);
    }

    // writers (ViewPath):
    public static void writeNoteViewPath(AbstractNote abstractNote) {
        note_w.writeToViewPath(abstractNote);
    }
    public static void writeDiescardedElementViewPath(DiscardedElement de) {
        discarded_w.writeToViewPath(de);
    }
}
