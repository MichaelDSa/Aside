package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePathElement.AbstractNote;
import com.github.michaeldsa.aside.AsidePathElement.MutableNote;
import com.github.michaeldsa.aside.Pretty;

import java.util.Properties;

public class NoteWriter extends NotePropertiesUtil{

    public NoteWriter() {
        properties = new Properties();
    }

    // set properties:
    private void setProperties(AbstractNote abstractNote) {
        String filename = emptyIfNull(abstractNote.getMetaPath().getPath().getFileName().toString());
        String title = emptyIfNull(abstractNote.getTitle());
        String content = emptyIfNull(abstractNote.getContent());
        String to = hashSetToString(abstractNote.getTo());
        String from = hashSetToString(abstractNote.getFrom());
        String tags = hashSetToString(abstractNote.getTags());

        properties.clear();

        properties.setProperty(filename_n, filename);
        properties.setProperty(title_n, title);
        properties.setProperty(content_n, content);
        properties.setProperty(to_n, to);
        properties.setProperty(from_n, from);
        properties.setProperty(tags_n, tags);

    }
    // write data from any AbstractNote subclass to file.
    public void write(AbstractNote abstractNote) {

        // set properties
        setProperties(abstractNote);

        // write to metapath
        writeProperties(properties, abstractNote.getMetaPath().getPath());

        // write to viewpath
//        writeViewPath(abstractNote.getMetaPath().getPath(), formatViewPathNote(properties));
        writeToViewPath(abstractNote);

    }

    public void writeToViewPath(AbstractNote abstractNote) {
        setProperties(abstractNote);
//        writeViewPath(abstractNote.getMetaPath().getPath(), formatViewPathNote(properties));
        writeViewPath(abstractNote.getMetaPath().getPath(), Pretty.formatNote4ViewPath(abstractNote, 80));
    }



}
