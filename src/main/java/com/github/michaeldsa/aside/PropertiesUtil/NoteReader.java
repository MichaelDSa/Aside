package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePathElement.MutableNote;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;

public class NoteReader extends NotePropertiesUtil{
    /*
    NoteReader reads from an existing properties file whose parent
    is a MetaPath directory. It assigns all found values to the
    MutableNote in the constructor parameter.
     */

    public NoteReader(MutableNote mn) {
        m_path = mn.getMetaPath().getPath();
        v_path = mn.getViewPath().getPath();
        properties = getFileProperties(m_path);
        mn.setTitle(getTitleProp())
                .setContent(getContentProp())
                .setTo(getToProp())
                .setFrom(getFromProp())
                .setTags(getTagsProp());
    }

    private String getTitleProp() {
        String val = properties.getProperty(title_n);
        if (val == null) {
            val = "";
        }
        return val;
    }
    private String getContentProp() {
        String val = properties.getProperty(content_n);
        if (val == null) {
            val = "";
        }
        return val;
    }
    private HashSet<String> getToProp() {
        return toHashSet(properties.getProperty(to_n));
    }
    private HashSet<String> getFromProp() {
        return toHashSet(properties.getProperty(from_n));
    }
    private HashSet<String> getTagsProp() {
        return toHashSet(properties.getProperty(tags_n));
    }

}
