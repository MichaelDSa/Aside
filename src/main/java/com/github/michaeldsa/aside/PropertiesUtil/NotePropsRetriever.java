package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePathElement.Note;


public class NotePropsRetriever extends NotePropsUtil {

    public NotePropsRetriever() {
        super();
    }


    public void retrieve(Note note) {
        // load file data into properties
        loadPropertiesFile(note.getMetaPath().getPath());

        // assign note fields from properties data
        note.setTitle(getPropAsString(title_k))
                .setContent(getPropAsString(content_k))
                .setTo(getPropAsHashSet(to_k))
                .setFrom(getPropAsHashSet(from_k))
                .setTags(getPropAsHashSet(tags_k))
                .setBibliographies(getPropAsHashSet(bibliographies_k));

        // clear properties, because access point is a static instance of this.
        properties.clear();
    }

}
