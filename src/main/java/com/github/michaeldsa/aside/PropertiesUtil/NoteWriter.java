package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePathElement.AbstractNote;
import com.github.michaeldsa.aside.Pretty;
import com.github.michaeldsa.aside.Settings.Settings;

public class NoteWriter extends NotePropertiesUtil {

    protected NoteWriter() {
        super();
    }

    public void write(AbstractNote note) {
        setProperties(note);
        writeProperties(note.getMetaPath().getPath());
        writeToViewPath(note);
    }

    public void writeToViewPath(AbstractNote note) {
        setProperties(note);
        writeViewPath(note.getMetaPath().getPath(), Pretty.formatNote4ViewPath(note, Settings.getLineWidth().file()));
    }
}
