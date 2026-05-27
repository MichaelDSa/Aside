package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePathElement.Bibliography;

import java.util.Arrays;
import java.util.HashSet;

public class BibliographyPropertiesUtil extends PropertiesUtil{
    // all property key names:
    protected String authors_k = "authors";
    protected String title_k = "title";
    protected String yearPublished_k = "year_published";
    protected String comment_k = "comment";
    protected String references_k = "references";
    protected String isbn_k = "isbn";
    protected String doi_k = "doi";
    protected String url_k = "url";
    protected String arXiv_ID_k = "arXivID";
    protected String ads_Bibcode_k = "adsBibCode";

    protected BibliographyPropertiesUtil() {
        super();
        stringPropertiesKeysSubset = new HashSet<>(Arrays.asList(title_k, yearPublished_k, comment_k, isbn_k, doi_k, url_k, arXiv_ID_k, ads_Bibcode_k));
        hashSetPropertiesKeysSubset = new HashSet<>(Arrays.asList(authors_k, references_k));
    }

    protected void setProperties(Bibliography bib) {

        properties.clear();

//        properties.setProperty(authors_k, hashSetToString(bib.getAuthors()))
//        properties.setProperty
//        properties.setProperty
//        properties.setProperty
//        properties.setProperty
//        properties.setProperty
//        properties.setProperty
//        properties.setProperty
//        properties.setProperty
//        properties.setProperty

    }

}
