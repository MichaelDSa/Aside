package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePathElement.AbstractNote;

import java.util.Arrays;
import java.util.HashSet;

public abstract class NotePropertiesUtil extends PropertiesUtil{
    // all property key names:
    protected final String filename_k = "filename";
    protected final String title_k = "title";
    protected final String content_k = "content";
    protected final String to_k = "to";
    protected final String from_k = "from";
    protected final String tags_k = "tags";
    protected final String bibliographies_k = "bib";


    protected NotePropertiesUtil() {
        super();
        // control sets:
        stringPropertiesKeysSubset = new HashSet<>(Arrays.asList(filename_k, title_k, content_k));
        hashSetStringPropertiesKeysSubset = new HashSet<>(Arrays.asList(to_k, from_k, tags_k, bibliographies_k));
    }

    protected void setProperties(AbstractNote note) {

        properties.clear();

        // define propertiesMap keys and values:
        properties.setProperty(filename_k, emptyIfNull(note.getMetaPath().getPath().getFileName().toString()));
        properties.setProperty(title_k, emptyIfNull(note.getTitle()));
        properties.setProperty(content_k, emptyIfNull(note.getContent()));
        properties.setProperty(to_k, hashSetToString(note.getTo()));
        properties.setProperty(from_k, hashSetToString(note.getFrom()));
        properties.setProperty(tags_k, hashSetToString(note.getTags()));
        properties.setProperty(bibliographies_k, hashSetToString(note.getBibliographies()));
    }
}
