package com.github.michaeldsa.aside.PropertiesUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;

public abstract class NotePropertiesUtil {

    // properties object
    // note: is this necessary? can it just be passed as method parameter?
    protected Properties properties;

    // Path data:
    // note: consider just method parameterizing these. They can be gotten from the AsidePathElement.
    protected Path m_path;
    protected Path v_path;

    // Note data:
    protected String title;
    protected String content;
    protected HashSet<String> to;
    protected HashSet<String> from;
    protected HashSet<String> tags;


    // property names:
    protected final String title_n = "title";
    protected final String content_n = "content";
    protected final String to_n = "to";
    protected final String from_n = "from";
    protected final String tags_n = "tags";

    protected ArrayList<String> noteProperties = new ArrayList<>(Arrays.asList(title_n, content_n, to_n, from_n, tags_n));
    protected ArrayList<String> noteStringProperties = new ArrayList<>(Arrays.asList(title_n, content_n));
    protected ArrayList<String> noteHashSetProperties = new ArrayList<>(Arrays.asList(to_n, from_n, tags_n));

    protected Properties getFileProperties(Path path) {
        Properties p = new Properties();
        if (Files.exists(path)) {
            try (InputStream is = Files.newInputStream(path)) {
                p.load(is);
            } catch (IOException e) {
                System.err.println("NotePropertiesUtil.readNoteData(): IOException: " + m_path);
            }
        }
        return p;
    }

    // get properties that should be saved as HashSet<String> in a MutableNote or DiscardedElement
    protected HashSet<String> getPropAsHashSet(Properties properties, String prop) {
        HashSet<String> val = new HashSet<>();
        if (noteHashSetProperties.contains(prop)) {
            HashSet<String> test = stringToHashSet(properties.getProperty(prop));
            if (test != null) {
                val = test;
            }
        }
        return val;
    }

    // get properties that should be saved as String in a MutableNote or DiscardedElement
    protected String getPropAsString(Properties properties, String prop) {
        String val = "";
        if (noteStringProperties.contains(prop)) {
            String test = properties.getProperty(prop);
            if (test != null) {
                val = test;
            }
        } else {
            System.err.println("DiscardedElementReader: getStringProp() found invalid property: " + prop);
        }
        return val;
    }

    // format a HashSet<String> to be saved in a properties file
    protected String hashSetToString(HashSet<String> hs) {
        return removeListChars(hs.toString());
    }

    // convert String objects retrieved from a Properties file to a HashSet<String>
    protected HashSet<String> stringToHashSet(String str) {
        String[] sarr;
        if (noteHashSetProperties.contains(str)) {
            str = removeListChars(str);
            sarr = str.split(" ");
        } else {
            return new HashSet<>();
        }
        return new HashSet<>(Arrays.asList(sarr));
    }

    // write a properties file
    protected void writeProperties(Properties properties, Path m_path) {
        try (OutputStream os = Files.newOutputStream(m_path)) {
            properties.store(os, "");
        } catch (IOException e) {
            System.err.println("NoteWriter.writeProperties(): IOException. path: " + m_path);
        }
    }

    // remove characters typically found in stdout when printing a List or Set.
    private String removeListChars(String str) {
        return str.replace("[", "")
                .replace("]","")
                .replace(",","");
    }

}
