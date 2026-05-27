package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePathElement.AbstractNote;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public abstract class NotePropsUtil extends PropertiesUtil{
    // all property key names:
    protected final String filename_k = "filename";
    protected final String title_k = "title";
    protected final String content_k = "content";
    protected final String to_k = "to";
    protected final String from_k = "from";
    protected final String tags_k = "tags";
    protected final String bibliographies_k = "bib";


    protected NotePropsUtil() {
        super();
        // assignment map:
        propertiesMap = new HashMap<>();

        // control sets:
        stringPropertiesKeysSubset = new HashSet<>(Arrays.asList(filename_k, title_k, content_k));
        hashSetPropertiesKeysSubset = new HashSet<>(Arrays.asList(to_k, from_k, tags_k, bibliographies_k));
    }

    protected void setProperties(AbstractNote note) {

        // define propertiesMap keys and values:
        propertiesMap.put(filename_k, emptyIfNull(note.getMetaPath().getPath().getFileName().toString()));
        propertiesMap.put(title_k, emptyIfNull(note.getTitle()));
        propertiesMap.put(content_k, emptyIfNull(note.getContent()));
        propertiesMap.put(to_k, hashSetToString(note.getTo()));
        propertiesMap.put(from_k, hashSetToString(note.getFrom()));
        propertiesMap.put(tags_k, hashSetToString(note.getTags()));
        propertiesMap.put(bibliographies_k, hashSetToString(note.getBibliographies()));

        properties.clear();

        for(String key : propertiesMap.keySet()) {
            properties.setProperty(key, propertiesMap.get(key));
        }
    }
}
