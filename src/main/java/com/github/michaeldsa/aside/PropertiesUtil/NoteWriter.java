package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePathElement.MutableNote;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Properties;

public class NoteWriter extends NotePropertiesUtil{

    public NoteWriter() {
        properties = new Properties();
    }

    private String emptyIfNull(String str) {
        return str == null ? "" : str;
    }
    public void write(MutableNote mn) {
        String title = emptyIfNull(mn.getTitle());
        String content = emptyIfNull(mn.getContent());
        String to = hashSetToString(mn.getTo());
        String from = hashSetToString(mn.getFrom());
        String tags = hashSetToString(mn.getTags());

        properties.setProperty(title_n, title);
        properties.setProperty(content_n, content);
        properties.setProperty(to_n, to);
        properties.setProperty(from_n, from);
        properties.setProperty(tags_n, tags);
        writeProperties(properties, mn.getMetaPath().getPath());

    }



}
