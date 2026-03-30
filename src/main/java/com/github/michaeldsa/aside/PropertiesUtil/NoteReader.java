package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePathElement.MutableNote;

import java.util.HashSet;
import java.util.Properties;

public class NoteReader extends NotePropertiesUtil{
    /*
    NoteReader reads from an existing properties file whose parent
    is a MetaPath directory. It assigns all found values to the
    MutableNote in the constructor parameter.
     */

    public NoteReader() {
        properties = new Properties();
    }

    private String getTitleProp(Properties properties) {
        return getPropAsString(properties, title_n);
    }
    private String getContentProp(Properties properties) {
        return getPropAsString(properties, content_n);
    }
    private HashSet<String> getToProp(Properties properties) {
        return getPropAsHashSet(properties, to_n);
    }
    private HashSet<String> getFromProp(Properties properties) {
        return getPropAsHashSet(properties, from_n);
    }
    private HashSet<String> getTagsProp(Properties properties) {
        return getPropAsHashSet(properties, tags_n);
    }

    public void read(MutableNote mn) {
        loadPropertiesFile(properties, mn.getMetaPath().getPath());
        mn.setTitle(getTitleProp(properties))
                .setContent(getContentProp(properties))
                .setTo(getToProp(properties))
                .setFrom(getFromProp(properties))
                .setTags(getTagsProp(properties));
    }

}
