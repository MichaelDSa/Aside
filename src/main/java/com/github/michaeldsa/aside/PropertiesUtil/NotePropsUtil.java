package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePathElement.AbstractNote;

import java.util.Arrays;
import java.util.HashSet;

public abstract class NotePropsUtil extends PropertiesUtil{
    // property key names:
    protected final String filename_k = "filename";
    protected final String title_k = "title";
    protected final String content_k = "content";
    protected final String to_k = "to";
    protected final String from_k = "from";
    protected final String tags_k = "tags";
    protected final String bibliographies_k = "bib";

    // property values:
    protected String filename_v;
    protected String title_v;
    protected String content_v;
    protected String to_v;
    protected String from_v;
    protected String tags_v;
    protected String bibliographies_v;

    protected NotePropsUtil() {
        super();
        allPropertiesKeySet = new HashSet<>(Arrays.asList(filename_k, title_k, content_k, to_k, from_k, tags_k, bibliographies_k));
        stringPropertiesKeysSubset = new HashSet<>(Arrays.asList(filename_k, title_k, content_k));
        hashSetPropertiesKeysSubset = new HashSet<>(Arrays.asList(to_k, from_k, tags_k, bibliographies_k));
    }

    protected void setProperties(AbstractNote note) {
        filename_v = emptyIfNull(note.getMetaPath().getPath().getFileName().toString());
        title_v = emptyIfNull(note.getTitle());
        content_v = emptyIfNull(note.getContent());
        to_v = hashSetToString(note.getTo());
        from_v = hashSetToString(note.getFrom());
        tags_v = hashSetToString(note.getTags());
        bibliographies_v = hashSetToString(note.getBibliographies());

        properties.clear();

        properties.setProperty(filename_k, filename_v);
        properties.setProperty(title_k, title_v);
        properties.setProperty(content_k, content_v);
        properties.setProperty(to_k, to_v);
        properties.setProperty(from_k, from_v);
        properties.setProperty(tags_k, tags_v);
        properties.setProperty(bibliographies_k, bibliographies_v);
    }
}
