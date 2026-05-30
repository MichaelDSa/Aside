package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePathElement.AbstractBibliography;
import com.github.michaeldsa.aside.AsidePathElement.Author;
import com.github.michaeldsa.aside.AsidePathElement.Bibliography;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class BibliographyPropertiesUtil extends PropertiesUtil{
    // all property key names:
    protected String authors_k = "authors";
    protected String title_k = "title";
    protected String publishers_k = "publishers";
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
        stringPropertiesKeysSubset = new HashSet<>(Arrays.asList(title_k, comment_k));
        hashSetStringPropertiesKeysSubset = new HashSet<>(Arrays.asList(authors_k, references_k, isbn_k, doi_k, url_k, arXiv_ID_k, ads_Bibcode_k));
        hashSetIntegerPropertiesKeysSubset = new HashSet<>(Arrays.asList(yearPublished_k));
    }

    protected void setProperties(AbstractBibliography bib) {

        properties.clear();

        properties.setProperty(authors_k, emptyIfNull(authorsArrayFormattedString(bib.getAuthors())));
        properties.setProperty(title_k, emptyIfNull(bib.getTitle()));
        properties.setProperty(publishers_k, hashSetToString(bib.getPublishers()));
        properties.setProperty(yearPublished_k, emptyIfNull(String.valueOf(bib.getYearPublished())));
        properties.setProperty(comment_k, emptyIfNull(bib.getComment()));
        properties.setProperty(references_k, hashSetToString(bib.getReferences()));
        properties.setProperty(isbn_k, hashSetToString(bib.getIsbn()));
        properties.setProperty(doi_k, hashSetToString(bib.getDoi()));
        properties.setProperty(url_k, hashSetToString(bib.getUrl()));
        properties.setProperty(arXiv_ID_k, hashSetToString(bib.getArXiv_ID()));
        properties.setProperty(ads_Bibcode_k, hashSetToString(bib.getAds_Bibcode()));

    }

    private String authorsArrayFormattedString(ArrayList<Author> authors) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < authors.size(); i++) {
            if (i < authors.size() - 1) {
                sb.append(authors.get(i).getConfigFormattedString()).append(delimiter);
            } else {
                sb.append(authors.get(i).getConfigFormattedString());
            }
        }
        return sb.toString();
    }

    protected ArrayList<Author> parseAuthorsToList(String configFormattedString) {
        String[] formattedStrings = configFormattedString.split(delimiter);
        ArrayList<Author> authors = new ArrayList<>();
        for (String s : formattedStrings) {
            authors.add(new Author(s));
        }
       return authors;
    }

}
