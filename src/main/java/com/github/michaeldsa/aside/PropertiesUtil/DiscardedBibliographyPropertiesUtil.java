package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePathElement.DiscardedBibliography;

import java.util.Arrays;
import java.util.HashSet;

public class DiscardedBibliographyPropertiesUtil extends DiscardedElementPropertiesUtil {
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

    protected DiscardedBibliographyPropertiesUtil() {
        super();
        // authors_k is included in this subset because Author constructs using special config-formatted strings, which is saved to properties.
        stringPropertiesKeysSubset = new HashSet<>(Arrays.asList(warning_k, message_k, originalMetaPath_k, originalViewPath_k, authors_k, title_k, comment_k));
        hashSetStringPropertiesKeysSubset = new HashSet<>(Arrays.asList(publishers_k, references_k, isbn_k, doi_k, url_k, arXiv_ID_k, ads_Bibcode_k));
        hashSetIntegerPropertiesKeysSubset = new HashSet<>(Arrays.asList(yearPublished_k));
    }

    protected void setProperties(DiscardedBibliography db) {
        properties.clear();

        // assign keys from DiscardedElementPropertiesUtil:
        properties.setProperty(warning_k, emptyIfNull(db.getWarning()));
        properties.setProperty(message_k, emptyIfNull(db.getMessage()));
        properties.setProperty(originalFileType_k, emptyIfNull(db.getFileTypeName()));
        properties.setProperty(originalMetaPath_k, emptyIfNull(db.getMetaPath().toString()));
        properties.setProperty(originalViewPath_k, emptyIfNull(db.getViewPath().toString()));

        // assign keys from this class:
        properties.setProperty(authors_k, emptyIfNull(authorsListToConfigFormattedString(db.getAuthors())));
        properties.setProperty(title_k, emptyIfNull(db.getTitle()));
        properties.setProperty(publishers_k, hashSetToString(db.getPublishers()));
        properties.setProperty(yearPublished_k, hashSetIntegerToString(db.getYearPublished()));
        properties.setProperty(comment_k, emptyIfNull(db.getComment()));
        properties.setProperty(references_k, hashSetToString(db.getReferences()));
        properties.setProperty(isbn_k, hashSetToString(db.getIsbn()));
        properties.setProperty(doi_k, hashSetToString(db.getDoi()));
        properties.setProperty(url_k, hashSetToString(db.getUrl()));
        properties.setProperty(arXiv_ID_k, hashSetToString(db.getArXiv_ID()));
        properties.setProperty(ads_Bibcode_k, hashSetToString(db.getAds_Bibcode()));
    }
}
