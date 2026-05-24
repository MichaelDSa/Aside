package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.AsidePath;
import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.Validation.ValidateAsidePath;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Objects;

public abstract class AbstractBibliography extends AsidePathElement {

    protected static BibliographyCategory bibliographyCategory = RestrictedLists.getBibliographyCategory();

    protected BibliographyCategory stepParent;

    private static final String m_prefix = ".b";
    private static final String v_prefix = "b";

    protected String authors;
    protected String title;
    protected int yearPublished;
    protected String comment;
    protected HashSet<String> references;
    protected String isbn;
    protected String doi;
    protected String url;
    protected String arXiv_ID;
    protected String ads_Bibcode;


    public abstract String getAuthors();
    public abstract String getTitle();
    public abstract int getYearPublished();
    public abstract String getComment();
    public abstract HashSet<String> getReferences();
    public abstract String getIsbn();
    public abstract String getDoi();
    public abstract String getUrl();
    public abstract String getArXiv_ID();
    public abstract String getAds_Bibcode();


    // static methods:

    // AsidePath must start with bibliographyCategory.
    private static boolean startsWithBibliographyCategory(AsidePath ap) {
        boolean success = false;
        if(ap instanceof MetaPath mp) {
            success = mp.startsWith(bibliographyCategory.getMetaPath());
        } else if (ap instanceof ViewPath vp) {
            success = vp.startsWith(bibliographyCategory.getMetaPath());
        }
        return success;
    }

    // must end with a valid category filename, or a valid bibliography filename
    private static boolean noFileNameOrHasBibFileName(AsidePath ap) {
        return ValidateAsidePath.CATEGORY_NAME.test(ap) || ValidateAsidePath.BIBLIOGRAPHY_NAME.test(ap);
    }

    // determine if constructor should throw an IllegalArgumentException.
    protected static boolean argumentIsInvalid(AsidePath ap) {
        return startsWithBibliographyCategory(ap) || !noFileNameOrHasBibFileName(ap);
    }

    protected static MetaPath generateNewBibliographyFileName(MetaPath parent) {
        String filename = AsidePathElement.generateUniqueFileName(new MetaPath()).getPath().getFileName().toString();
        String bibFilename = m_prefix + filename.substring(1);
        return parent.resolve(new MetaPath(Paths.get(bibFilename)));
    }

    protected static ViewPath generateNewBibliographyFileName(ViewPath parent) {
        String filename = AsidePathElement.generateUniqueFileName(new MetaPath()).getPath().getFileName().toString();
        String bibFilename = v_prefix + filename;
        return parent.resolve(new ViewPath(Paths.get(bibFilename)));
    }

    // inherited from AsidePathElement
    @Override
    public AbstractCategory getParentCategory() {
        return new BibliographyCategory(metaPath.getParent());
    }

    @Override
    public AbstractCategory getStepParentCategory() {
        return stepParent;
    }

    @Override
    public void setStepParentCategory(AbstractCategory newStepParent) {
        this.stepParent = (BibliographyCategory) newStepParent;
    }

    @Override
    public boolean hasStepParent() {
        return stepParent != null;
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AbstractBibliography that)) return false;
        if (!super.equals(o)) return false;
        return yearPublished == that.yearPublished && Objects.equals(stepParent, that.stepParent) && Objects.equals(authors, that.authors) && Objects.equals(title, that.title) && Objects.equals(comment, that.comment) && Objects.equals(references, that.references) && Objects.equals(isbn, that.isbn) && Objects.equals(doi, that.doi) && Objects.equals(url, that.url) && Objects.equals(arXiv_ID, that.arXiv_ID) && Objects.equals(ads_Bibcode, that.ads_Bibcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), stepParent, authors, title, yearPublished, comment, references, isbn, doi, url, arXiv_ID, ads_Bibcode);
    }

    @Override
    public String toString() {
        return "AbstractBibliography{" +
                "stepParent=" + stepParent +
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
                ", nest=" + nest +
                '}';
    }
}
