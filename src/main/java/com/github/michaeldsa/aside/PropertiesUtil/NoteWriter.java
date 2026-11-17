package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePathElement.Note;
import com.github.michaeldsa.aside.Pretty;
import com.github.michaeldsa.aside.Settings.Settings;

class NoteWriter extends NotePropertiesUtil {

    protected NoteWriter() {
        super();
    }

    public void write(Note note) {
        setProperties(note);
        writeProperties(note.getMetaPath().getPath());
        writeToViewPath(note);
    }

    public void writeToViewPath(Note note) {
        setProperties(note);
        writeViewPath(note.getMetaPath().getPath(), Pretty.formatNote4ViewPath(note, Settings.lineWidths().getFileLW()));
    }
}
