package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.Pretty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;

public abstract class DiscardedElementPropertiesUtil extends NotePropertiesUtil {
    // property names:
    protected final String warning_n = "warning";
    protected final String message_n = "message";
    protected final String originalMetaPath_n = "original_metapath";
    protected final String originalViewPath_n = "original_viewPath";

    // lists
    protected ArrayList<String> discardedElementProperties = new ArrayList<>(Arrays.asList(filename_n, warning_n, message_n, originalMetaPath_n, originalViewPath_n, title_n, content_n, to_n, from_n, tags_n));
    protected ArrayList<String> discardedElementStringProperties = new ArrayList<>(Arrays.asList(filename_n, warning_n, message_n, originalMetaPath_n, originalViewPath_n, title_n, content_n));
    protected ArrayList<String> discardedElementHashSetProperties = new ArrayList<>(Arrays.asList(to_n, from_n, tags_n));


    protected String formatViewPathDiscardedElement(Properties properties) {
        String filename = getPropAsString(properties, filename_n);
        String warning = Pretty.format(getPropAsString(properties, warning_n), 80);
        String message = Pretty.format(getPropAsString(properties, message_n), 80);
        String title = Pretty.format(getPropAsString(properties, title_n), 80);
        String content = Pretty.format(getPropAsString(properties, content_n), 80);
        String originalMetaPath = Pretty.format(getPropAsString(properties, originalMetaPath_n), 80);
        String originalViewPath = Pretty.format(getPropAsString(properties, originalViewPath_n), 80);
        String to = Pretty.format(properties.getProperty(to_n), 80);
        String from = Pretty.format(properties.getProperty(from_n), 80);
        String tags = Pretty.format(properties.getProperty(tags_n), 80);


        String nl = "\n";
        filename += nl + warning + nl;

        if(!message.isBlank()) {
            message = "MESSAGE:" + nl + message + "-".repeat(80) + nl.repeat(2);
        }
        if(!title.isBlank()) {
            title = "TITLE:" + nl + title + nl;
        }
        if(!content.isBlank()) {
            content = "CONTENT:" + nl + content + "-".repeat(80) + nl.repeat(2);
        }
        if(!originalMetaPath.isBlank()) {
            originalMetaPath = "ORIGINAL_METAPATH:" + nl + originalMetaPath + nl;
        }
        if(!originalViewPath.isBlank()) {
            originalViewPath = "ORIGINAL_VIEWPATH:" + nl + originalViewPath + "-".repeat(80) + nl.repeat(2);
        }
        if(!to.isBlank()) {
            to = "TO:" + nl + to + nl;
        }
        if(!from.isBlank()) {
            from = "FROM:" + nl + from + nl;
        }
        if(!tags.isBlank()) {
            tags = "TAGS:" + nl + tags + nl;
        }

        return filename + message + title + content + originalMetaPath + originalViewPath + to + from + tags;
    }

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

    // convert String objects retrieved from a Properties file to a HashSet<String>
    protected HashSet<String> stringToHashSet(String str) {
        String[] sarr;
        if (discardedElementHashSetProperties.contains(str)) {
            str = removeListChars(str);
            sarr = str.split(" ");
        } else {
            return new HashSet<>();
        }
        return new HashSet<>(Arrays.asList(sarr));
    }
}
