package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePathElement.Bibliography;

public class BibliographyRetriever extends BibliographyPropertiesUtil {

    public BibliographyRetriever() {
        super();
    }

    public void retrieve(Bibliography bib) {
        properties.clear();

        loadPropertiesFile(bib.getMetaPath().getPath());

        bib.setTitle(emptyIfNull(properties.getProperty(title_k)))
                .setAuthors(parseAuthorsToList(properties.getProperty(authors_k)))
                .setPublishers(getPropAsHashSet(properties.getProperty(publishers_k)))
                .setYearPublished(getPropAsIntegerHashSet(properties.getProperty(yearPublished_k))) // create public int stringToIntegerHashSet();
                .setComment(emptyIfNull(properties.getProperty(comment_k)))
                .setReferences(getPropAsHashSet(properties.getProperty(references_k)))
                .setIsbn(getPropAsHashSet(properties.getProperty(isbn_k)))
                .setDoi(getPropAsHashSet(properties.getProperty(doi_k)))
                .setUrl(getPropAsHashSet(properties.getProperty(url_k)))
                .setArXiv_ID(getPropAsHashSet(properties.getProperty(arXiv_ID_k)))
                .setAds_Bibcode(getPropAsHashSet(properties.getProperty(ads_Bibcode_k)));

    }
}
