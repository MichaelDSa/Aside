package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePathElement.DiscardedElement;
import com.github.michaeldsa.aside.AsidePathElement.MutableNote;

public class PropUtils {
    private static final NoteReader note_r = new NoteReader();
    private static final DiscardedElementReader discarded_r = new DiscardedElementReader();

    // readers:
    public static void noteReader(MutableNote mn) {
        note_r.read(mn);
    }
    public static void discardedElementReader(DiscardedElement de) {
        discarded_r.read(de);
    }

    // writers:
    public static void noteWriter(MutableNote mn) {
        new NoteWriter(mn);
    }
    public static void discardedElementWriter(DiscardedElement de) {
        new DiscardedElementWriter(de);
    }
}
