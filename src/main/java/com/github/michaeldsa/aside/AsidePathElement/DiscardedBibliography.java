package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.AsidePath;
import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.Validation.ValidateAsidePath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

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

    // constructors:

    // arg should be DiscardedBibliography MetaPath url
    public DiscardedBibliography(MetaPath mp) {
        if (invalidConstructorArg(mp)) {
            System.err.println("IllegalArgumentException.  Path must start with '.DISCARDED' MetaPath Category and end with DiscardedBibilography filename.");
            throw new IllegalArgumentException("Invalid Path argument: " + mp);
        }
        setBiliographyFieldsToEmpty();
        metaPath = mp;
        viewPath = new ViewPath(mp);
        fileTypeName = fileTypeName_bibliography;
        fileNamePrefix = fileNamePrefix_discardedBibliography;
        originalMetaPath = null;
        originalViewPath = null;
    }

    // arg should be DiscardedBibliography ViewPath url
    public DiscardedBibliography(ViewPath vp) {
        if (invalidConstructorArg(vp)) {
            System.err.println("IllegalArgumentException.  Path must start with 'DISCARDED' ViewPath Category and end with DiscardedBibilography filename.");
            throw new IllegalArgumentException("Invalid Path argument: " + vp);
        }
        setBiliographyFieldsToEmpty();
        viewPath = vp;
        metaPath = new MetaPath(vp);
        originalMetaPath = null;
        originalViewPath = null;
        fileTypeName = fileTypeName_bibliography;
        fileNamePrefix = fileNamePrefix_discardedBibliography;
        originalMetaPath = null;
        originalViewPath = null;

    }

    // arg should be a Bibliography
    public DiscardedBibliography(Bibliography bb) {

        setBiliographyFieldsToEmpty(); // in case bb fields are null

        fileTypeName = fileTypeName_bibliography;
        fileNamePrefix = fileNamePrefix_discardedBibliography;

        metaPath = discardedCategory.getMetaPath().resolve(renameMetaPathFileName(bb.getMetaPath()));
        viewPath = new ViewPath(metaPath);

        originalMetaPath = bb.getMetaPath();
        originalViewPath = bb.getViewPath();

        nest = new ArrayList<>();

        authors = bb.getAuthors();
        title = bb.getTitle();
        yearPublished = bb.getYearPublished();
        comment = bb.getComment();
        references = bb.getReferences();
        isbn = bb.getIsbn();
        doi = bb.getDoi();
        url = bb.getUrl();
        arXiv_ID = bb.getArXiv_ID();
        ads_Bibcode = bb.getAds_Bibcode();
    }

    // Constructor helper methods:
    private void setBiliographyFieldsToEmpty() {
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

    public DiscardedBibliography setAuthors(String authors) {
        this.authors = authors;
        return this;
    }
    public DiscardedBibliography setTitle(String title) {
        this.title = title;
        return this;
    }
    public DiscardedBibliography setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
        return this;
    }
    public DiscardedBibliography setComment(String comment) {
        this.comment = comment;
        return this;
    }
    public DiscardedBibliography prependToComment(String comment) {
        this.comment = comment + " " + this.comment;
        return this;
    }
    public DiscardedBibliography appendToComment(String comment) {
        this.comment += " " + comment;
        return this;
    }
    public DiscardedBibliography setReferences(HashSet<String> references) {
        this.references = references;
        return this;
    }
    public DiscardedBibliography addReferences(String ...references) {
        this.references.addAll(Arrays.asList(references));
        return this;
    }
    public DiscardedBibliography removeReferences(String ...references) {
        Arrays.asList(references).forEach(this.references::remove);
        return this;
    }
    public DiscardedBibliography clearReferences() {
        this.references.clear();
        return this;
    }
    public DiscardedBibliography setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }
    public DiscardedBibliography setDoi(String doi) {
        this.doi = doi;
        return this;
    }
    public DiscardedBibliography setUrl(String url) {
        this.url = url;
        return this;
    }
    public DiscardedBibliography setArXiv_ID(String arXiv_ID) {
        this.arXiv_ID = arXiv_ID;
        return this;
    }
    public DiscardedBibliography setAds_Bibcode(String ads_Bibcode) {
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DiscardedBibliography that)) return false;
        if (!super.equals(o)) return false;
        return yearPublished == that.yearPublished && Objects.equals(authors, that.authors) && Objects.equals(title, that.title) && Objects.equals(comment, that.comment) && Objects.equals(references, that.references) && Objects.equals(isbn, that.isbn) && Objects.equals(doi, that.doi) && Objects.equals(url, that.url) && Objects.equals(arXiv_ID, that.arXiv_ID) && Objects.equals(ads_Bibcode, that.ads_Bibcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), authors, title, yearPublished, comment, references, isbn, doi, url, arXiv_ID, ads_Bibcode);
    }

    @Override
    public String toString() {
        return "DiscardedBibliography{" +
                "authors='" + authors + '\'' +
                ", title='" + title + '\'' +
                ", yearPublished=" + yearPublished +
                ", comment='" + comment + '\'' +
                ", references=" + references +
                ", isbn='" + isbn + '\'' +
                ", doi='" + doi + '\'' +
                ", url='" + url + '\'' +
                ", arXiv_ID='" + arXiv_ID + '\'' +
                ", ads_Bibcode='" + ads_Bibcode + '\'' +
                ", metaPath=" + metaPath +
                ", viewPath=" + viewPath +
                ", nest=" + nest +
                '}';
    }
}
