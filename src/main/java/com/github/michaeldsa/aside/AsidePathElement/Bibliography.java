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
            mp = bibliographyCategory_MetaPath.resolve(mp);
        }
        /* mp must start with BIBLIOGRAPHY category and must end
        with either a bibliography filename or no filename. */
        if (AbstractBibliography.constructorArgIsInvalid(mp)) {
            System.err.println("InvalidArgumentException");
            throw new IllegalArgumentException("Invalid Path argument: " + mp);
        }

        // AsidePathElement fields:
        /* generate bibliography filename if necessary */
        metaPath = ValidateAsidePath.CATEGORY_NAME.test(mp)
                ? AbstractBibliography.generateNewBibliographyFileName(mp)
                : mp;
        viewPath = new ViewPath(metaPath);
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
            vp = bibliographyCategory_ViewPath.resolve(vp);
        }
        /* vp must start with BIBLIOGRAPHY category and must end
        with either a bibliography filename or no filename. */
        if (AbstractBibliography.constructorArgIsInvalid(vp)) {
            System.err.println("InvalidArgumentException");
            throw new IllegalArgumentException("Invalid Path argument: " + vp);
        }

        // AsidePathElement fields:
        /* ensure that viewPath ends with a Bibliography filename */
        viewPath = ValidateAsidePath.CATEGORY_NAME.test(vp)
                ? AbstractBibliography.generateNewBibliographyFileName(vp)
                : vp;
        metaPath = new MetaPath(viewPath);
        nest = new ArrayList<>();

        // AbstractBibliography fields:
        stepParent = null;
        setBibFieldsToEmpty();

        // this class' fields:
        previousState = null;

    }
    public Bibliography(BibliographyCategory bc) {
        /* unlikely, but arg can fail if cast to BibliographyCategory */
        if (AbstractBibliography.constructorArgIsInvalid(bc.getMetaPath())) {
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
        if (AbstractBibliography.constructorArgIsInvalid(bb.getMetaPath())) {
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
        authors = new HashSet<>();
        title = "";
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

    @Override
    public HashSet<String> getAuthors() {
        return authors;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public HashSet<Integer> getYearPublished() {
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
    public HashSet<String> getIsbn() {
        return isbn;
    }

    @Override
    public HashSet<String> getDoi() {
        return doi;
    }

    @Override
    public HashSet<String> getUrl() {
        return url;
    }

    @Override
    public HashSet<String> getArXiv_ID() {
        return arXiv_ID;
    }

    @Override
    public HashSet<String> getAds_Bibcode() {
        return ads_Bibcode;
    }

    public ImmutableBibliography getPreviousState() {
        return previousState;
    }


    // setters and field modifiers:
    public Bibliography setAuthors(HashSet<String> authors) {
        this.authors = authors;
        return this;
    }
    public Bibliography setTitle(String title) {
        this.title = title;
        return this;
    }
    public Bibliography setYearPublished(HashSet<Integer> yearPublished) {
        this.yearPublished = yearPublished;
        return this;
    }
    public Bibliography addYearPublished(Integer ...yearPublished) {
        this.yearPublished.addAll(Arrays.asList(yearPublished));
        return this;
    }
    public Bibliography removeYearPublished(Integer ...yearPublished) {
        Arrays.asList(yearPublished).forEach(this.yearPublished::remove);
        return this;
    }
    public Bibliography clearYearPublished() {
        this.yearPublished.clear();
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
    public Bibliography setIsbn(HashSet<String> isbn) {
        this.isbn = isbn;
        return this;
    }
    public Bibliography addIsbn(String ...isbn) {
        this.isbn.addAll(Arrays.asList(isbn));
        return this;
    }
    public Bibliography removeIsbn(String ...isbn) {
        Arrays.asList(isbn).forEach(this.isbn::remove);
        return this;
    }
    public Bibliography clearIsbn() {
        this.isbn.clear();
        return this;
    }
    public Bibliography setDoi(HashSet<String> doi) {
        this.doi = doi;
        return this;
    }
    public Bibliography addDoi(String ... doi) {
        this.doi.addAll(Arrays.asList(doi));
        return this;
    }
    public Bibliography removeDoi(String ... doi) {
        Arrays.asList(doi).forEach(this.doi::remove);
        return this;
    }
    public Bibliography clearDoi() {
        this.doi.clear();
        return this;
    }
    public Bibliography setUrl(HashSet<String> url) {
        this.url = url;
        return this;
    }
    public Bibliography addUrl(String ...url) {
        this.url.addAll(Arrays.asList(url));
        return this;
    }
    public Bibliography removeUrl(String ...url) {
        Arrays.asList(url).forEach(this.url::remove);
        return this;
    }
    public Bibliography clearUrl() {
        this.url.clear();
        return this;
    }
    public Bibliography setArXiv_ID(HashSet<String> arXiv_ID) {
        this.arXiv_ID = arXiv_ID;
        return this;
    }
    public Bibliography addArXiv_ID(String ...arXiv_ID) {
        this.arXiv_ID.addAll(Arrays.asList(arXiv_ID));
        return this;
    }
    public Bibliography removeArXiv_ID(String ...arXiv_ID) {
        Arrays.asList(arXiv_ID).forEach(this.arXiv_ID::remove);
        return this;
    }
    public Bibliography clearArXiv_ID() {
        this.arXiv_ID.clear();
        return this;
    }
    public Bibliography setAds_Bibcode(HashSet<String> ads_Bibcode) {
        this.ads_Bibcode = ads_Bibcode;
        return this;
    }
    public Bibliography addAds_Bibcode(String ...ads_Bibcode) {
        this.ads_Bibcode.addAll(Arrays.asList(ads_Bibcode));
        return this;
    }
    public Bibliography removeAds_Bibcode(String ...ads_Bibcode) {
        Arrays.asList(ads_Bibcode).forEach(this.ads_Bibcode::remove);
        return this;
    }
    public Bibliography clearAds_Bibcode() {
        this.ads_Bibcode.clear();
        return this;
    }
    public Bibliography setPreviousState() {
        previousState = new ImmutableBibliography(this);
        return this;
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
                ", metaPath=" + metaPath +
                ", viewPath=" + viewPath +
                '}';
    }
}
