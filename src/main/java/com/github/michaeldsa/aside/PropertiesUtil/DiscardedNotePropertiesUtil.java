package com.github.michaeldsa.aside.PropertiesUtil;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;

public abstract class DiscardedNotePropertiesUtil extends NotePropertiesUtil {
    // property names:
    protected final String warning_n = "warning";
    protected final String message_n = "message";
    protected final String originalMetaPath_n = "original_metapath";
    protected final String originalViewPath_n = "original_viewPath";

    // lists
    protected ArrayList<String> discardedElementProperties = new ArrayList<>(Arrays.asList(filename_n, warning_n, message_n, originalMetaPath_n, originalViewPath_n, title_n, content_n, to_n, from_n, tags_n));
    protected ArrayList<String> discardedElementStringProperties = new ArrayList<>(Arrays.asList(filename_n, warning_n, message_n, originalMetaPath_n, originalViewPath_n, title_n, content_n));
    protected ArrayList<String> discardedElementHashSetProperties = new ArrayList<>(Arrays.asList(to_n, from_n, tags_n));


     // get properties that should be saved as String in a MutableNote or DiscardedElement
    protected String getPropAsString(Properties properties, String prop) {
        String val = "";
        if (discardedElementStringProperties.contains(prop)) {
            String test = properties.getProperty(prop);
            if (test != null) {
                val = test;
            }
        } else {
            System.err.println("NotePropertiesUtil.getPropAsString(): prop parameter not found in noteStringProperties: " + prop);
        }
        return val;
    }

    // get properties that should be saved as HashSet<String> in a MutableNote or DiscardedElement
    protected HashSet<String> getPropAsHashSet(Properties properties, String prop) {
        HashSet<String> val = new HashSet<>();
        if (discardedElementHashSetProperties.contains(prop)) {
            HashSet<String> test = stringToHashSet(properties.getProperty(prop));
            if (test != null) {
                val = test;
            }
        }
        return val;
    }
}
