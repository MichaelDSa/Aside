package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePathElement.MutableNote;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Properties;

public class NoteWriter extends NotePropertiesUtil{

    public NoteWriter(MutableNote mn) {
        m_path = mn.getMetaPath().getPath();
        v_path = mn.getViewPath().getPath();
        properties = new Properties();
        properties.setProperty(title_n, mn.getTitle());
        properties.setProperty(content_n, mn.getContent());
        properties.setProperty(to_n, super.hashSetToString(mn.getTo()));
        properties.setProperty(from_n, super.hashSetToString(mn.getFrom()));
        properties.setProperty(tags_n, super.hashSetToString(mn.getTags()));
        writeProperties();
    }



}
