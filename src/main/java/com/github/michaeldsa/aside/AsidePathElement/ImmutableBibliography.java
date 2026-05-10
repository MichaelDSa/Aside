package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;

import java.util.Collections;
import java.util.HashSet;

public class ImmutableBibliography extends AbstractBibliography {
    private final MetaPath metaPath;
    private final ViewPath viewPath;
    private final BibCat stepParent;
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

    public ImmutableBibliography(Bibliography bib) {
        this.metaPath = bib.getMetaPath();
        this.viewPath = bib.getViewPath();
        this.stepParent = (BibCat)bib.getStepParentCategory();

        this.authors = bib.getAuthors();
        this.title = bib.getTitle();
        this.yearPublished = bib.getYearPublished();
        this.comment = bib.getComment();
        this.references = new HashSet<>(Collections.unmodifiableSet(bib.getReferences()));
        this.isbn = bib.getIsbn();
        this.doi = bib.getDoi();
        this.url  = bib.getUrl();
        this.arXiv_ID = bib.getArXiv_ID();
        this.ads_Bibcode = bib.getAds_Bibcode();
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
        return new BibCat(this.metaPath);
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
}
