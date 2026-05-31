package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePathElement.DiscardedNote;

import java.util.Arrays;
import java.util.HashSet;

public class DiscardedNotePropertiesUtil extends PropertiesUtil {
    // all property key names:
    protected final String warning_k = "warning";
    protected final String message_k = "message";
    protected final String originalFileType_k = "original_filetype";
    protected final String originalMetaPath_k = "original_metaPath";
    protected final String originalViewPath_k = "original_viewPath";
    protected final String title_k = "title";
    protected final String content_k = "content";
    protected final String to_k = "to";
    protected final String from_k = "from";
    protected final String tags_k = "tags";
    protected final String bibliographies_k = "bib";

    protected DiscardedNotePropertiesUtil() {
        super();
        // control sets:
        stringPropertiesKeysSubset = new HashSet<>(Arrays.asList(warning_k, message_k, originalMetaPath_k, originalViewPath_k, title_k, content_k));
        hashSetStringPropertiesKeysSubset = new HashSet<>(Arrays.asList(to_k, from_k, tags_k, bibliographies_k));
    }

    protected void setProperties(DiscardedNote dn) {

        properties.clear();

        // define propertiesMap keys & values:
        properties.setProperty(warning_k, emptyIfNull(dn.getWarning()));
        properties.setProperty(message_k, emptyIfNull(dn.getMessage()));
        properties.setProperty(originalFileType_k, emptyIfNull(dn.getFileTypeName()));
        properties.setProperty(originalMetaPath_k, emptyIfNull(dn.getOriginalMetaPath().toString()));
        properties.setProperty(originalViewPath_k, emptyIfNull(dn.getOriginalViewPath().toString()));
        properties.setProperty(title_k, emptyIfNull(dn.getTitle()));
        properties.setProperty(content_k, emptyIfNull(dn.getContent()));
        properties.setProperty(to_k, hashSetToString(dn.getTo()));
        properties.setProperty(from_k, hashSetToString(dn.getFrom()));
        properties.setProperty(tags_k, hashSetToString(dn.getTags()));
        properties.setProperty(bibliographies_k, hashSetToString(dn.getBibliographies()));


    }

}
