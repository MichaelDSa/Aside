package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePathElement.Note;


public class NoteRetriever extends NotePropertiesUtil {

    protected NoteRetriever() {
        super();
    }


    public void retrieve(Note note) {
        // clear properties because object used is pretty much a singleton
        properties.clear();

        // load file data into properties
        loadPropertiesFile(note.getMetaPath().getPath());

        // assign note fields from properties data
        note.setTitle(getPropAsString(title_k))
                .setContent(getPropAsString(content_k))
                .setTo(getPropAsHashSet(to_k))
                .setFrom(getPropAsHashSet(from_k))
                .setTags(getPropAsHashSet(tags_k))
                .setBibliographies(getPropAsHashSet(bibliographies_k));

    }

}
