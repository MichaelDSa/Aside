package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.AsidePathElement.DiscardedBibliography;

import java.nio.file.Paths;

public class DiscardedBibliographyRetriever extends DiscardedBibliographyPropertiesUtil {
    public DiscardedBibliographyRetriever() {
        super();
    }
    public void retrieve(DiscardedBibliography db) {
        properties.clear();
        loadPropertiesFile(db.getMetaPath().getPath());
        db.setMessage(getPropAsString(message_k))
                .setOriginalMetaPath(new MetaPath(Paths.get(getPropAsString(originalMetaPath_k))))
                .setOriginalViewPath(new ViewPath(Paths.get(getPropAsString(originalViewPath_k))))
                .setAuthors(configFormattedStringToAuthorsList(getPropAsString(authors_k)))
                .setTitle(getPropAsString(title_k))
                .setPublishers(stringToHashSet(getPropAsString(publishers_k)))
                .setYearPublished(getPropAsIntegerHashSet(yearPublished_k))
                .setComment(getPropAsString(comment_k))
                .setReferences(getPropAsHashSet(references_k))
                .setIsbn(getPropAsHashSet(isbn_k))
                .setDoi(getPropAsHashSet(doi_k))
                .setUrl(getPropAsHashSet(url_k))
                .setArXiv_ID(getPropAsHashSet(arXiv_ID_k))
                .setAds_Bibcode(getPropAsHashSet(ads_Bibcode_k));
    }

}
