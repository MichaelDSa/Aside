package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.Validation.ValidateAsidePath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

public class Bibliography extends AbstractBibliography {

    private ImmutableBibliography previousState;


    public Bibliography(MetaPath mp) {
        /* client can use metaPath_root to create a new bibliography
        in the default bibliography category. */
        if (mp.equals(new MetaPath())) {
            mp = bibliographyCategory.getMetaPath().resolve(mp);
        }
        /* mp must start with BIBLIOGRAPHY category and must end
        with either a bibliography filename or no filename. */
        if (AbstractBibliography.argumentIsInvalid(mp)) {
            System.err.println("InvalidArgumentException");
            throw new IllegalArgumentException("Invalid Path argument: " + mp);
        }

        // AsidePathElement fields:
        /* generate bibliography filename is necessary */
        metaPath = ValidateAsidePath.CATEGORY_NAME.test(metaPath)
                ? AbstractBibliography.generateNewBibliographyFileName(metaPath)
                : metaPath;
        viewPath = new ViewPath(mp);
        nest = new ArrayList<>();


        // AbstractBibliography fields:
        stepParent = null;
        setBibFieldsToEmpty();

        // this class' fields:
        previousState = null;

    }
    public Bibliography(ViewPath vp) {
        /* client can use viewPath_root to create a new bibliography
        in the default bibliography category. */
        if (vp.equals(new ViewPath())) {
            vp = bibliographyCategory.getViewPath().resolve(vp);
        }
        /* vp must start with BIBLIOGRAPHY category and must end
        with either a bibliography filename or no filename. */
        if (AbstractBibliography.argumentIsInvalid(vp)) {
            System.err.println("InvalidArgumentException");
            throw new IllegalArgumentException("Invalid Path argument: " + vp);
        }

        // AsidePathElement fields:
        /* ensure that viewPath ends with a Bibliography filename */
        viewPath = ValidateAsidePath.CATEGORY_NAME.test(viewPath)
                ? AbstractBibliography.generateNewBibliographyFileName(viewPath)
                : viewPath;
        metaPath = new MetaPath(vp);
        nest = new ArrayList<>();

        // AbstractBibliography fields:
        stepParent = null;
        setBibFieldsToEmpty();

        // this class' fields:
        previousState = null;

    }
    public Bibliography(BibliographyCategory bc) {
        /* unlikely, but arg can fail if cast to BibliographyCategory */
        if (AbstractBibliography.argumentIsInvalid(bc.getMetaPath())) {
            System.err.println("InvalidArgumentException");
            throw new IllegalArgumentException("Invalid Path argument: " + bc.getMetaPath());
        }

        // AsidePathElement fields:
        /* ensure that metaPath ends with a Bibliography filename.*/
        metaPath = ValidateAsidePath.CATEGORY_NAME.test(metaPath)
                ? AbstractBibliography.generateNewBibliographyFileName(metaPath)
                : metaPath;
        viewPath = new ViewPath(bc.getMetaPath());
        nest = new ArrayList<>();

        // AbstractBibliography fields:
        stepParent = (BibliographyCategory) bc.getStepParentCategory();
        setBibFieldsToEmpty();

        // this class' fields:
        previousState = null;
    }
    public Bibliography(Bibliography bb) {
        /* unlikely, but arg can fail if cast to Bibliography */
        if (AbstractBibliography.argumentIsInvalid(bb.getMetaPath())) {
            System.err.println("InvalidArgumentException");
            throw new IllegalArgumentException("Invalid Path argument: " + bb.getMetaPath());
        }

        // AsidePathElement fields:
        metaPath = bb.getMetaPath();
        viewPath = bb.getViewPath();
        nest = bb.getNest();

        // AbstractBibliography fields:
        stepParent = (BibliographyCategory) bb.getStepParentCategory();

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

        // this class' fields:
        previousState = new ImmutableBibliography(bb);
    }


    // Constructor helper methods:
    private void setBibFieldsToEmpty() {
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

    public Bibliography setPreviousState() {
        previousState = new ImmutableBibliography(this);
        return this;
    }

    // setters:
    public Bibliography setAuthors(String authors) {
        this.authors = authors;
        return this;
    }
    public Bibliography setTitle(String title) {
        this.title = title;
        return this;
    }
    public Bibliography setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
        return this;
    }
    public Bibliography setComment(String comment) {
        this.comment = comment;
        return this;
    }
    public Bibliography prependToComment(String comment) {
        this.comment = comment + " " + this.comment;
        return this;
    }
    public Bibliography appendToComment(String comment) {
        this.comment += " " + comment;
        return this;
    }
    public Bibliography setReferences(HashSet<String> references) {
        this.references = references;
        return this;
    }
    public Bibliography addReferences(String ...references) {
        this.references.addAll(Arrays.asList(references));
        return this;
    }
    public Bibliography removeReferences(String ...references) {
        Arrays.asList(references).forEach(this.references::remove);
        return this;
    }
    public Bibliography clearReferences() {
        this.references.clear();
        return this;
    }
    public Bibliography setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }
    public Bibliography setDoi(String doi) {
        this.doi = doi;
        return this;
    }
    public Bibliography setUrl(String url) {
        this.url = url;
        return this;
    }
    public Bibliography setArXiv_ID(String arXiv_ID) {
        this.arXiv_ID = arXiv_ID;
        return this;
    }
    public Bibliography setAds_Bibcode(String ads_Bibcode) {
        this.ads_Bibcode = ads_Bibcode;
        return this;
    }

    public ImmutableBibliography getPreviousState() {
        return previousState;
    }

    // booleans:
    public boolean hasPreviousState() {
        return previousState != null;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Bibliography that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(previousState, that.previousState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), previousState);
    }

    @Override
    public String toString() {
        return "Bibliography{" +
                "previousState=" + previousState +
                '}';
    }
}
