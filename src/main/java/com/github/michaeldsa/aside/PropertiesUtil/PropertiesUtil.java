package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.AsidePathElement.Author;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;

public abstract class PropertiesUtil {
    public static String delimiter = "\u001f"; // looks like: ^_ or: US
    protected Properties properties;

    // Control Sets. To be assigned in abstract sub-classes.
    protected HashSet<String> stringPropertiesKeysSubset;
    protected HashSet<String> hashSetStringPropertiesKeysSubset;
    protected HashSet<String> hashSetIntegerPropertiesKeysSubset;

    protected PropertiesUtil() {
        properties = new Properties();
    }

    protected String authorsArrayFormattedString(ArrayList<Author> authors) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < authors.size(); i++) {
            if (i < authors.size() - 1) {
                sb.append(authors.get(i).getConfigFormattedString()).append(delimiter);
            } else {
                sb.append(authors.get(i).getConfigFormattedString());
            }
        }
        return sb.toString();
    }

    protected void loadPropertiesFile(Path path) {
        if (Files.exists(path)) {
            try (BufferedReader is = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
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
        if (hashSetStringPropertiesKeysSubset.contains(prop)) {
            val = stringToHashSet(properties.getProperty(prop, ""));
        } else {
            System.err.println("PropertiesUtil.getPropAsHashSet(): prop parameter not found in hashSetStringPropertiesKeysSubset: " + prop);
        }
        return val;
    }

    // get properties that should be saved as HashSet<Integer> from a Bibliography
    protected HashSet<Integer> getPropAsIntegerHashSet(String prop) {
        HashSet<Integer> integers = new HashSet<>();
        if(hashSetIntegerPropertiesKeysSubset.contains(prop)) {
            HashSet<String> strings = stringToHashSet(properties.getProperty(prop, ""));
            for(String s : strings) {
                integers.add(Integer.parseInt(s));
            }
        }
        return integers;
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
        if (hs == null || hs.isEmpty()) {
            return "";
        }
        return String.join(delimiter, hs);
    }
    protected String hashSetIntegerToString(HashSet<Integer> hs) {
        HashSet<String> strings = new HashSet<>();
        for (Integer i : hs) {
            strings.add(i.toString());
        }
        return hashSetToString(strings);
    }

    protected ArrayList<Author> parseAuthorsToList(String configFormattedString) {
        String[] formattedStrings = configFormattedString.split(delimiter);
        ArrayList<Author> authors = new ArrayList<>();
        for (String s : formattedStrings) {
            authors.add(new Author(s));
        }
        return authors;
    }

    // convert String objects retrieved from a Properties file to a HashSet<String>
    protected HashSet<String> stringToHashSet(String str) {

        // will fail if not all properties exist in file. if so, use:
        // if (str == null || str.isBlank()) { ...
        if (str.isBlank()) {
            return new HashSet<>();
        }

        String[] str_arr = str.split(delimiter);

        return new HashSet<>(Arrays.asList(str_arr));
    }

    // write a properties file
    protected void writeProperties(Path m_path) {
        try (BufferedWriter out = Files.newBufferedWriter(m_path, StandardCharsets.UTF_8)) {
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


}
