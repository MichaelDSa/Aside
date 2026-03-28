package com.github.michaeldsa.aside.PropertiesUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;

public abstract class NotePropertiesUtil {

    // properties object
    protected Properties properties;

    // Path data:
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
    protected void writeProperties() {
        try (OutputStream os = Files.newOutputStream(m_path)) {
            properties.store(os, "");
        } catch (IOException e) {
            System.err.println("NoteWriter.writeProperties(): IOException. path: " + m_path);
        }
    }
    protected HashSet<String> toHashSet(String str) {
        if (str == null) {
            return new HashSet<>();
        }
        str = replaceListChars(str);

        String[] sarr = str.split(" ");

        return new HashSet<>(Arrays.asList(sarr));
    }

    protected String hashSetToString(HashSet<String> hs) {
        return replaceListChars(hs.toString());

    }

    private String replaceListChars(String str) {
        return str.replace("[", "")
                .replace("]","")
                .replace(",","");
    }

}
