package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;

public abstract class PropertiesUtil {
    protected Properties properties;

    // assignment map:
    protected HashMap<String, String> propertiesMap;

    // Control Sets. To be assigned in abstract sub-classes.
    protected HashSet<String> stringPropertiesKeysSubset;
    protected HashSet<String> hashSetPropertiesKeysSubset;

    protected PropertiesUtil() {
        properties = new Properties();
    }

    protected void loadPropertiesFile(Path path) {
        if (Files.exists(path)) {
            try (InputStream is = Files.newInputStream(path)) {
                properties.load(is);
            } catch (IOException e) {
                System.err.println("PropertiesUtil.loadPropertiesFile(): IOException: " + path);
            }
        }
    }

    protected static String emptyIfNull(String str) {
        return str == null ? "" : str;
    }

    // get properties that should be saved as HashSet<String> in a MutableNote or DiscardedElement
    protected HashSet<String> getPropAsHashSet(String prop) {
        HashSet<String> val = new HashSet<>();
        if (hashSetPropertiesKeysSubset.contains(prop)) {
            val = stringToHashSet(properties.getProperty(prop, ""));
        } else {
            System.err.println("PropertiesUtil.getPropAsString(): prop parameter not found in hashSetPropertiesKeysSubset: " + prop);
        }
        return val;
    }

    // get properties that should be saved as String in a MutableNote or DiscardedElement
    protected String getPropAsString(String prop) {
        String val = "";
        if (stringPropertiesKeysSubset.contains(prop)) {
            val = properties.getProperty(prop, "");
        } else {
            System.err.println("PropertiesUtil.getPropAsString(): prop parameter not found in stringPropertiesKeysSubset: " + prop);
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

        // will fail if not all properties exist in file. if so, use:
        // if (str == null || str.isBlank()) { ...
        if (str.isBlank()) {
            return new HashSet<>();
        }

        str = removeListChars(str);
        String[] str_arr = str.split(" ");

        return new HashSet<>(Arrays.asList(str_arr));
    }

    // write a properties file
    protected void writeProperties(Path m_path) {
        try (OutputStream out = Files.newOutputStream(m_path)) {
            properties.store(out, "");
        } catch (IOException e) {
            System.err.println("PropertiesUtil.writeProperties(): IOException. path: " + m_path);
        }
    }

    // write ViewPath file
    protected void writeViewPath(Path m_path, String formattedString) {
        Path v_path = new ViewPath(new MetaPath(m_path)).getPath();
        try (OutputStream out = Files.newOutputStream(v_path)) {
            out.write(formattedString.getBytes());
        } catch (IOException e) {
            System.err.println("PropertiesUtil.writeViewPath(): caught IOException. path: " + v_path);
        }
    }

    // remove characters typically found in stdout when printing a List or Set.
    protected String removeListChars(String str) {
        return str.replace("[", "")
                .replace("]","")
                .replace(",","");
    }

}
