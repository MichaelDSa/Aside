package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.Pretty;

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

    // property names:
    protected final String filename_n = "filename";
    protected final String title_n = "title";
    protected final String content_n = "content";
    protected final String to_n = "to";
    protected final String from_n = "from";
    protected final String tags_n = "tags";

    // lists
    protected ArrayList<String> noteProperties = new ArrayList<>(Arrays.asList(filename_n, title_n, content_n, to_n, from_n, tags_n));
    protected ArrayList<String> noteStringProperties = new ArrayList<>(Arrays.asList(filename_n, title_n, content_n));
    protected ArrayList<String> noteHashSetProperties = new ArrayList<>(Arrays.asList(to_n, from_n, tags_n));

    protected void loadPropertiesFile(Properties properties, Path path) {
        if (Files.exists(path)) {
            try (InputStream is = Files.newInputStream(path)) {
                properties.load(is);
            } catch (IOException e) {
                System.err.println("NotePropertiesUtil.loadPropertiesFile(): IOException: " + path);
            }
        }
    }

     protected static String emptyIfNull(String str) {
        return str == null ? "" : str;
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

    // format string for AbstractNote subclass:
    protected String formatViewPathNote(Properties properties) {
        String filename = getPropAsString(properties, filename_n).substring(1);
        String title = Pretty.format(getPropAsString(properties, title_n), 80);
        String content = Pretty.format(getPropAsString(properties, content_n), 80);
        String to = Pretty.format(properties.getProperty(to_n), 80);
        String from = Pretty.format(properties.getProperty(from_n), 80);
        String tags = Pretty.format(properties.getProperty(tags_n), 80);

        String nl = "\n";

        if (!filename.isBlank()) {
            filename += nl + "-".repeat(80) + nl.repeat(2);
        }
        if (!title.isBlank()) {
            title = "TITLE:" + nl + title + nl;
        }
        if (!content.isBlank()) {
            content = "CONTENT:" + nl + content + "-".repeat(80) + nl.repeat(2);
        }
        if (!to.isBlank()) {
            to = "TO:" + nl + to + nl;
        }
        if (!from.isBlank()) {
            from = "FROM:" + nl + from + nl;
        }
        if (!tags.isBlank()) {
            tags = "TAGS:" + nl + tags + nl;
        }

        return filename + title + content + to + from + tags;
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
            System.err.println("NotePropertiesUtil.getPropAsString(): prop parameter not found in noteStringProperties: " + prop);
        }
        return val;
    }

    // format a HashSet<String> to be saved in a properties file
    protected String hashSetToString(HashSet<String> hs) {
        if (hs == null) {
            return "";
        }
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
        try (OutputStream out = Files.newOutputStream(m_path)) {
            properties.store(out, "");
        } catch (IOException e) {
            System.err.println("NotePropertiesUtil.writeProperties(): IOException. path: " + m_path);
        }
    }

    // write ViewPath file
    protected void writeViewPath(Path m_path, String formattedString) {
        Path v_path = new ViewPath(new MetaPath(m_path)).getPath();
        try (OutputStream out = Files.newOutputStream(v_path)) {
            out.write(formattedString.getBytes());
        } catch (IOException e) {
            System.err.println("NotePropertiesUtil.writeViewPath(): caught IOException. path: " + v_path);
        }
    }

    // remove characters typically found in stdout when printing a List or Set.
    protected String removeListChars(String str) {
        return str.replace("[", "")
                .replace("]","")
                .replace(",","");
    }

}
