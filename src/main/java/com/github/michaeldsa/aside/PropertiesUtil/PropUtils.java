package com.github.michaeldsa.aside.PropertiesUtil;

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

    // writers:
    public static void writeNote(MutableNote mn) {
        note_w.write(mn);
    }
    public static void writeDiscardedElement(DiscardedElement de) {
        discarded_w.write(de);
    }
}
