package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePathElement.Bibliography;

public class BibliographyRetriever extends BibliographyPropertiesUtil {

    public BibliographyRetriever() {
        super();

    }

    public void retrieve(Bibliography bib) {
        properties.clear();

        loadPropertiesFile(bib.getMetaPath().getPath());

        bib.setTitle(properties.getProperty(title_k, ""))
                .setAuthors(parseAuthorsToList(properties.getProperty(authors_k, "")))
                .setPublishers(getPropAsHashSet(publishers_k))
                .setYearPublished(getPropAsIntegerHashSet(yearPublished_k))
                .setComment(properties.getProperty(comment_k, ""))
                .setReferences(getPropAsHashSet(references_k))
                .setIsbn(getPropAsHashSet(isbn_k))
                .setDoi(getPropAsHashSet(doi_k))
                .setUrl(getPropAsHashSet(url_k))
                .setArXiv_ID(getPropAsHashSet(arXiv_ID_k))
                .setAds_Bibcode(getPropAsHashSet(ads_Bibcode_k));

    }
}
