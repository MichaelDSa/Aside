package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.AsidePathElement.DiscardedNote;

import java.nio.file.Paths;

public class DiscardedNoteRetriever extends DiscardedNotePropertiesUtil {

    protected DiscardedNoteRetriever() {
        super();
    }

    public void retrieve(DiscardedNote dn) {
        properties.clear();

        // load properties file into properties field
        loadPropertiesFile(dn.getMetaPath().getPath());

        // assign DiscardedNote fields from properties data
        dn.setOriginalMetaPath(new MetaPath(Paths.get(getPropAsString(originalMetaPath_k))))
                .setOriginalViewPath(new ViewPath(Paths.get(getPropAsString(originalViewPath_k))))
                .setMessage(getPropAsString(message_k))
                .setTitle(getPropAsString(title_k))
                .setContent(getPropAsString(content_k))
                .setTo(getPropAsHashSet(to_k))
                .setFrom(getPropAsHashSet(from_k))
                .setTags(getPropAsHashSet(tags_k))
                .setBibliographies(getPropAsHashSet(bibliographies_k));

    }

}
