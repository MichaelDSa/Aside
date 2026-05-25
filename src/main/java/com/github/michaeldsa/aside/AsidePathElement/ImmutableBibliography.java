package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;

public class ImmutableBibliography extends AbstractBibliography {
    private final MetaPath metaPath;
    private final ViewPath viewPath;
    private final BibliographyCategory stepParent;
    private final String authors;
    private final String title;
    private final int yearPublished;
    private final String comment;
    private final HashSet<String> references;
    private final String isbn;
    private final String doi;
    private final String url;
    private final String arXiv_ID;
    private final String ads_Bibcode;

    public ImmutableBibliography(Bibliography bb) {
        /* unlikely, but arg can fail if cast to Bibliography*/
        if (AbstractBibliography.constructorArgIsInvalid(bb.getMetaPath())) {
            System.err.println("InvalidArgumentException");
            throw new IllegalArgumentException("Invalid Path argument: " + bb.getMetaPath());
        }
        this.metaPath = bb.getMetaPath();
        this.viewPath = bb.getViewPath();
        this.stepParent = (BibliographyCategory)bb.getStepParentCategory();

        this.authors = bb.getAuthors();
        this.title = bb.getTitle();
        this.yearPublished = bb.getYearPublished();
        this.comment = bb.getComment();
        this.references = new HashSet<>(Collections.unmodifiableSet(bb.getReferences()));
        this.isbn = bb.getIsbn();
        this.doi = bb.getDoi();
        this.url  = bb.getUrl();
        this.arXiv_ID = bb.getArXiv_ID();
        this.ads_Bibcode = bb.getAds_Bibcode();
    }

    @Override
    public String getAuthors() {
        return authors;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getYearPublished() {
        return yearPublished;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public HashSet<String> getReferences() {
        return references;
    }

    @Override
    public String getIsbn() {
        return isbn;
    }

    @Override
    public String getDoi() {
        return doi;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getArXiv_ID() {
        return arXiv_ID;
    }

    @Override
    public String getAds_Bibcode() {
        return ads_Bibcode;
    }

    @Override
    public MetaPath getMetaPath() {
        return this.metaPath;
    }
    @Override
    public ViewPath getViewPath() {
        return this.viewPath;
    }
    @Override
    public AbstractCategory getParentCategory() {
        return new BibliographyCategory(this.metaPath);
    }
    @Override
    public AbstractCategory getStepParentCategory() {
        return this.stepParent;
    }
    @Override
    public void setStepParentCategory(AbstractCategory category) { }

    @Override
    public boolean hasStepParent() {
        return this.stepParent != null;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ImmutableBibliography that)) return false;
        if (!super.equals(o)) return false;
        return yearPublished == that.yearPublished && Objects.equals(metaPath, that.metaPath) && Objects.equals(viewPath, that.viewPath) && Objects.equals(stepParent, that.stepParent) && Objects.equals(authors, that.authors) && Objects.equals(title, that.title) && Objects.equals(comment, that.comment) && Objects.equals(references, that.references) && Objects.equals(isbn, that.isbn) && Objects.equals(doi, that.doi) && Objects.equals(url, that.url) && Objects.equals(arXiv_ID, that.arXiv_ID) && Objects.equals(ads_Bibcode, that.ads_Bibcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), metaPath, viewPath, stepParent, authors, title, yearPublished, comment, references, isbn, doi, url, arXiv_ID, ads_Bibcode);
    }

    @Override
    public String toString() {
        return "ImmutableBibliography{" +
                "metaPath=" + metaPath +
                ", viewPath=" + viewPath +
                ", stepParent=" + stepParent +
                ", authors='" + authors + '\'' +
                ", title='" + title + '\'' +
                ", yearPublished=" + yearPublished +
                ", comment='" + comment + '\'' +
                ", references=" + references +
                ", isbn='" + isbn + '\'' +
                ", doi='" + doi + '\'' +
                ", url='" + url + '\'' +
                ", arXiv_ID='" + arXiv_ID + '\'' +
                ", ads_Bibcode='" + ads_Bibcode + '\'' +
                '}';
    }
}
