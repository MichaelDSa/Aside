package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePathElement.AbstractBibliography;

import java.util.Arrays;
import java.util.HashSet;

public class BibliographyPropertiesUtil extends PropertiesUtil{
    // all property key names:
    protected final String authors_k = "authors";
    protected final String title_k = "title";
    protected final String publishers_k = "publishers";
    protected final String yearPublished_k = "year_published";
    protected final String comment_k = "comment";
    protected final String references_k = "references";
    protected final String isbn_k = "isbn";
    protected final String doi_k = "doi";
    protected final String url_k = "url";
    protected final String arXiv_ID_k = "arXivID";
    protected final String ads_Bibcode_k = "adsBibCode";

    protected BibliographyPropertiesUtil() {
        super();
        // authors_k is included in this subset because Author constructs using special config-formatted strings, which is saved to properties.
        stringPropertiesKeysSubset = new HashSet<>(Arrays.asList(authors_k, title_k, comment_k));
        hashSetStringPropertiesKeysSubset = new HashSet<>(Arrays.asList(publishers_k, references_k, isbn_k, doi_k, url_k, arXiv_ID_k, ads_Bibcode_k));
        hashSetIntegerPropertiesKeysSubset = new HashSet<>(Arrays.asList(yearPublished_k));
    }

    protected void setProperties(AbstractBibliography bib) {

        properties.clear();

        properties.setProperty(authors_k, emptyIfNull(authorsListToConfigFormattedString(bib.getAuthors())));
        properties.setProperty(title_k, emptyIfNull(bib.getTitle()));
        properties.setProperty(publishers_k, hashSetToString(bib.getPublishers()));
        properties.setProperty(yearPublished_k, hashSetIntegerToString(bib.getYearPublished()));
        properties.setProperty(comment_k, emptyIfNull(bib.getComment()));
        properties.setProperty(references_k, hashSetToString(bib.getReferences()));
        properties.setProperty(isbn_k, hashSetToString(bib.getIsbn()));
        properties.setProperty(doi_k, hashSetToString(bib.getDoi()));
        properties.setProperty(url_k, hashSetToString(bib.getUrl()));
        properties.setProperty(arXiv_ID_k, hashSetToString(bib.getArXiv_ID()));
        properties.setProperty(ads_Bibcode_k, hashSetToString(bib.getAds_Bibcode()));

    }
}
