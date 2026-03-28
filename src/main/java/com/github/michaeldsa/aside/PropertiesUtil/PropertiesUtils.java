package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePathElement.DiscardedElement;
import com.github.michaeldsa.aside.AsidePathElement.MutableNote;

public class PropertiesUtils {

    // readers:
    public static void noteReader(MutableNote mn) {
        new NoteWriter(mn);
    }
    public static void discardedElementReader(DiscardedElement de) {
        new DiscardedElementReader(de);
    }

    // writers:
    public static void noteWriter(MutableNote mn) {
        new NoteWriter(mn);
    }
    public static void discardedElementWriter(DiscardedElement de) {
        new DiscardedElementWriter(de);
    }
}
