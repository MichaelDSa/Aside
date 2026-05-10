package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.Validation.ValidateAsidePath;

import java.util.Arrays;
import java.util.HashSet;

public class Bibliography extends AbstractBibliography {

    private ImmutableBibliography previousState;


    // this should move to BibCat.
    private Bibliography() {
        metaPath = RestrictedLists.getBibliographyDirectory().getMetaPath();
        viewPath = RestrictedLists.getBibliographyDirectory().getViewPath();
        stepParent = null;
        previousState = null;
    }

    public Bibliography(MetaPath mp, String title) {
        if (argumentIsInvalid(mp)) {
            System.err.println("InvalidArgumentException");
            throw new IllegalArgumentException("Invalid Path argument: " + mp);
        }

        setFieldsToEmpty();

        // ensure that metaPath starts with the defined Bibliography directory.
        metaPath = mp.startsWith(RestrictedLists.getBibliographyDirectory().getMetaPath())
                ? mp
                : RestrictedLists.getBibliographyDirectory().getMetaPath().resolve(mp);

        // ensure that metaPath ends with a Bibliography filename.
        metaPath = ValidateAsidePath.CATEGORY_NAME.test(metaPath)
                ? AbstractBibliography.generateNewBibliographyFileName(metaPath)
                : metaPath;

        viewPath = new ViewPath(mp);
        this.title = title;
        stepParent = null;
    }
    public Bibliography(ViewPath vp, String title) {
        if (argumentIsInvalid(vp)) {
            System.err.println("InvalidArgumentException");
            throw new IllegalArgumentException("Invalid Path argument: " + vp);
        }

        setFieldsToEmpty();

        // ensure that viewPath starts with the defined Bibliography directgory
        viewPath = vp.startsWith(RestrictedLists.getBibliographyDirectory().getViewPath())
                ? vp
                : RestrictedLists.getBibliographyDirectory().getViewPath().resolve(vp);

        // ensure that viewPath ends with a Bibliography filename
        viewPath = ValidateAsidePath.CATEGORY_NAME.test(viewPath)
                ? AbstractBibliography.generateNewBibliographyFileName(viewPath)
                : viewPath;

        metaPath = new MetaPath(vp);
        this.title = title;
        stepParent = null;
    }
    public Bibliography(BibCat bc, String title) {
        if (argumentIsInvalid(bc.getMetaPath())) {
            System.err.println("InvalidArgumentException");
            throw new IllegalArgumentException("Invalid Path argument: " + bc.getMetaPath());
        }

        setFieldsToEmpty();

        // ensure (redundantly, i know.) that metaPath startw with defined Bibliography directory
        metaPath = bc.getMetaPath().startsWith(RestrictedLists.getBibliographyDirectory().getMetaPath())
                ? bc.getMetaPath()
                : RestrictedLists.getBibliographyDirectory().getMetaPath().resolve(bc.getMetaPath());

        // ensure that metaPath ends with a Bibliography filename.
        metaPath = ValidateAsidePath.CATEGORY_NAME.test(metaPath)
                ? AbstractBibliography.generateNewBibliographyFileName(metaPath)
                : metaPath;

        viewPath = new ViewPath(bc.getMetaPath());
        this.title = title;
        stepParent = (BibCat) bc.getStepParentCategory();
    }
    public Bibliography(Bibliography bb) {
        if (argumentIsInvalid(bb.getMetaPath())) {
            System.err.println("InvalidArgumentException");
            throw new IllegalArgumentException("Invalid Path argument: " + bb.getMetaPath());
        }

        metaPath = bb.getMetaPath();
        viewPath = bb.getViewPath();
        stepParent = (BibCat) bb.getStepParentCategory();

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

    public static Bibliography getEmptyBibliographyElement() {
        return new Bibliography();
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


}
