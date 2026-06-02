package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.AsidePath;
import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.Validation.ValidateAsidePath;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class DiscardedBibliography extends AbstractDiscardedElement{

    private ArrayList<Author> authors;
    private String title;
    private String message; // a message about this discareded element
    private HashSet<String> publishers;
    private HashSet<Integer> yearPublished;
    private String comment; // a comment included with original Bibliography file.
    private HashSet<String> references;
    private HashSet<String> isbn;
    private HashSet<String> doi;
    private HashSet<String> url;
    private HashSet<String> arXiv_ID;
    private HashSet<String> ads_Bibcode;

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

        // AbstractDiscardedElement fields:
        fileTypeName = fileTypeName_bibliography;
        fileNamePrefix = fileNamePrefix_discardedBibliography;
        originalMetaPath = null;
        originalViewPath = null;

        // AsidePathElement fields:
        metaPath = mp;
        viewPath = new ViewPath(mp);
        nest = new ArrayList<>();

        // This class' fields:
        setBiliographyFieldsToEmpty();
    }

    // arg should be DiscardedBibliography ViewPath url
    public DiscardedBibliography(ViewPath vp) {
        if (invalidConstructorArg(vp)) {
            System.err.println("IllegalArgumentException.  Path must start with 'DISCARDED' ViewPath Category and end with DiscardedBibilography filename.");
            throw new IllegalArgumentException("Invalid Path argument: " + vp);
        }

        // AbstractDiscardedElement fields:
        fileTypeName = fileTypeName_bibliography;
        fileNamePrefix = fileNamePrefix_discardedBibliography;
        originalMetaPath = null;
        originalViewPath = null;

        // AsidePathElement fields:
        viewPath = vp;
        metaPath = new MetaPath(vp);
        nest = new ArrayList<>();

        // This class' fileds:
        setBiliographyFieldsToEmpty();
    }

    // arg should be a Bibliography
    public DiscardedBibliography(Bibliography bb) {
        /* test Metapath. Arg can fail if cast to Bibliography */
        MetaPath bibliographyCategory = new MetaPath(Paths.get(RestrictedLists.getMetaPathBibliographyDirectoryName()));
        if (!bb.getMetaPath().startsWith(bibliographyCategory) || !ValidateAsidePath.BIBLIOGRAPHY_NAME.test(bb.getMetaPath())) {
            System.err.println("IllegalArgumentException.  Path must start with 'BIBLIOGRAPHY' Category and end with Bibliography filename.");
            throw new IllegalArgumentException("Invalid Path argument: " + bb.getMetaPath());

        }

        // AbstractDiscardedElement fields:
        fileNamePrefix = fileNamePrefix_discardedBibliography;
        fileTypeName = fileTypeName_bibliography;
        originalMetaPath = bb.getMetaPath();
        originalViewPath = bb.getViewPath();

        // AsidePathElement fields:
        metaPath = discardedCategory.getMetaPath().resolve(renameMetaPathFileName(bb.getMetaPath()));
        viewPath = new ViewPath(metaPath);
        nest = new ArrayList<>();

        // This class' fields:
        setBiliographyFieldsToEmpty(); // in case bb fields are null
        authors = bb.getAuthors();
        title = bb.getTitle();
        publishers = bb.getPublishers();
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
        authors = new ArrayList<>();
        title = "";
        message = "";
        publishers = new HashSet<>();
        yearPublished = new HashSet<>();
        comment = "";
        references = new HashSet<>();
        isbn = new HashSet<>();
        doi = new HashSet<>();
        url = new HashSet<>();
        arXiv_ID = new HashSet<>();
        ads_Bibcode = new HashSet<>();
    }

    // getters:

    public ArrayList<Author> getAuthors() {
        return authors;
    }
    public String getTitle() {
        return title;
    }
    public String getMessage() {
        return message;
    }
    public HashSet<String> getPublishers() {
        return publishers;
    }
    public HashSet<Integer> getYearPublished() {
        return yearPublished;
    }
    public String getComment() {
        return comment;
    }
    public HashSet<String> getReferences() {
        return references;
    }
    public HashSet<String> getIsbn() {
        return isbn;
    }
    public HashSet<String> getDoi() {
        return doi;
    }
    public HashSet<String> getUrl() {
        return url;
    }
    public HashSet<String> getArXiv_ID() {
        return  arXiv_ID;
    }
    public HashSet<String> getAds_Bibcode() {
        return ads_Bibcode;
    }

    // setters:

    public DiscardedBibliography setAuthors(ArrayList<Author> authors) {
        this.authors = authors;
        return this;
    }
    public DiscardedBibliography setTitle(String title) {
        this.title = title;
        return this;
    }
    public DiscardedBibliography setMessage(String message) {
        this.message = message;
        return this;
    }
    public DiscardedBibliography setPublishers(HashSet<String> publishers) {
        this.publishers = publishers;
        return this;
    }
    public DiscardedBibliography setYearPublished(HashSet<Integer> yearPublished) {
        this.yearPublished = yearPublished;
        return this;
    }
    public DiscardedBibliography setComment(String comment) {
        this.comment = comment;
        return this;
    }
    public DiscardedBibliography setReferences(HashSet<String> references) {
        this.references = references;
        return this;
    }
    public DiscardedBibliography setIsbn(HashSet<String> isbn) {
        this.isbn = isbn;
        return this;
    }
    public DiscardedBibliography setDoi(HashSet<String> doi) {
        this.doi = doi;
        return this;
    }
    public DiscardedBibliography setUrl(HashSet<String> url) {
        this.url = url;
        return this;
    }
    public DiscardedBibliography setArXiv_ID(HashSet<String> arXiv_ID) {
        this.arXiv_ID = arXiv_ID;
        return this;
    }
    public DiscardedBibliography setAds_Bibcode(HashSet<String> ads_Bibcode) {
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
    public Bibliography restore() {
        /* Restore original data object. Client is meant to retrieve beforehand. */
        return new Bibliography(originalMetaPath != null ? originalMetaPath : metaPath)
                .setAuthors(authors)
                .setTitle(title)
                .setPublishers(publishers)
                .setYearPublished(yearPublished)
                .setComment(comment)
                .setReferences(references)
                .setIsbn(isbn)
                .setDoi(doi)
                .setUrl(url)
                .setArXiv_ID(arXiv_ID)
                .setAds_Bibcode(ads_Bibcode);

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
                ", discardedCategory=" + discardedCategory +
                ", originalMetaPath=" + originalMetaPath +
                ", originalViewPath=" + originalViewPath +
                ", metaPath=" + metaPath +
                ", viewPath=" + viewPath +
                '}';
    }
}
