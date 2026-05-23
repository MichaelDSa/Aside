package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.AsidePath;
import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.Validation.ValidateAsidePath;

import java.util.Arrays;
import java.util.HashSet;

public class DiscardedBibliography extends AbstractDiscardedElement{

    private String authors;
    private String title;
    private int yearPublished;
    private String comment;
    private HashSet<String> references;
    private String isbn;
    private String doi;
    private String url;
    private String arXiv_ID;
    private String ads_Bibcode;

    // Validation: Must start with DISCARDED. Must end with DiscardedBibliography filename.
    private boolean invalidConstructorArg(AsidePath ap) {
        return !ValidateAsidePath.DISCARDED_BIBLIOGRAPHY_NAME.test(ap) || !startsWithDiscardedCategory(ap);
    }

    public DiscardedBibliography(MetaPath mp) {


    }

    // Constructor helper methods:
    private void setFieldsToEmpty() {
        authors = "";
        title = "";
        yearPublished = -1;
        comment = "";
        references = new HashSet<>();
        isbn = "";
        doi = "";
        url = "";
        arXiv_ID = "";
        ads_Bibcode = "";
    }

    // getters:

    public String getAuthors() {
        return authors;
    }
    public String getTitle() {
        return title;
    }
    public int getYearPublished() {
        return yearPublished;
    }
    public String getComment() {
        return comment;
    }
    public HashSet<String> getReferences() {
        return references;
    }
    public String getIsbn() {
        return isbn;
    }
    public String getDoi() {
        return doi;
    }
    public String getUrl() {
        return url;
    }
    public String getArXiv_ID() {
        return  arXiv_ID;
    }
    public String getAds_Bibcode() {
        return ads_Bibcode;
    }

    // setters:

    public DiscardedBibliograhy setAuthors(String authors) {
        this.authors = authors;
        return this;
    }
    public DiscardedBibliograhy setTitle(String title) {
        this.title = title;
        return this;
    }
    public DiscardedBibliograhy setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
        return this;
    }
    public DiscardedBibliograhy setComment(String comment) {
        this.comment = comment;
        return this;
    }
    public DiscardedBibliograhy prependToComment(String comment) {
        this.comment = comment + " " + this.comment;
        return this;
    }
    public DiscardedBibliograhy appendToComment(String comment) {
        this.comment += " " + comment;
        return this;
    }
    public DiscardedBibliograhy setReferences(HashSet<String> references) {
        this.references = references;
        return this;
    }
    public DiscardedBibliograhy addReferences(String ...references) {
        this.references.addAll(Arrays.asList(references));
        return this;
    }
    public DiscardedBibliograhy removeReferences(String ...references) {
        Arrays.asList(references).forEach(this.references::remove);
        return this;
    }
    public DiscardedBibliograhy clearReferences() {
        this.references.clear();
        return this;
    }
    public DiscardedBibliograhy setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }
    public DiscardedBibliograhy setDoi(String doi) {
        this.doi = doi;
        return this;
    }
    public DiscardedBibliograhy setUrl(String url) {
        this.url = url;
        return this;
    }
    public DiscardedBibliograhy setArXiv_ID(String arXiv_ID) {
        this.arXiv_ID = arXiv_ID;
        return this;
    }
    public DiscardedBibliograhy setAds_Bibcode(String ads_Bibcode) {
        this.ads_Bibcode = ads_Bibcode;
        return this;
    }

    @Override
    public DiscardedBibliography setOriginalMetaPath(MetaPath original) {
        this.originalMetaPath = original;
        this.originalViewPath = new ViewPath(originalMetaPath);
        return this;
    }

    @Override
    public DiscardedBibliography setOriginalViewPath(ViewPath original) {
        this.originalViewPath = original;
        this.originalMetaPath = new MetaPath(originalViewPath);
        return this;
    }
}
