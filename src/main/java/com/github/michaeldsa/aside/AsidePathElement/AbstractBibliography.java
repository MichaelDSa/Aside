package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.AsidePath;
import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.Validation.ValidateAsidePath;
import com.github.michaeldsa.aside.Validation.ValidateName;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public abstract class AbstractBibliography extends AsidePathElement {

    protected static MetaPath bibliographyCategory_MetaPath = new MetaPath(Paths.get(RestrictedLists.getMetaPathBibliographyDirectoryName()));
    protected static ViewPath bibliographyCategory_ViewPath = new ViewPath(Paths.get(RestrictedLists.getViewPathBibliographyDirectoryName()));

    private static final String m_prefix = ".b";
    private static final String v_prefix = "b";

    protected ArrayList<Author> authors;
    protected String title;
    protected HashSet<String> publishers;
    protected HashSet<Integer> yearPublished;
    protected String comment;
    protected HashSet<String> references;
    protected HashSet<String> isbn;
    protected HashSet<String> doi;
    protected HashSet<String> url;
    protected HashSet<String> arXiv_ID;
    protected HashSet<String> ads_Bibcode;


    public abstract ArrayList<Author> getAuthors();
    public abstract String getTitle();
    public abstract HashSet<String> getPublishers();
    public abstract HashSet<Integer> getYearPublished();
    public abstract String getComment();
    public abstract HashSet<String> getReferences();
    public abstract HashSet<String> getIsbn();
    public abstract HashSet<String> getDoi();
    public abstract HashSet<String> getUrl();
    public abstract HashSet<String> getArXiv_ID();
    public abstract HashSet<String> getAds_Bibcode();


    // static methods:

    // Abstract methods from AsidePathElement
    @Override
    public BibliographyCategory getParentCategory() {
        return new BibliographyCategory(metaPath);
    }
    // AsidePath must start with bibliographyCategory.
    private static boolean startsWithBibliographyCategory(AsidePath ap) {
        boolean success = false;
        if(ap instanceof MetaPath mp) {
            success = mp.startsWith(bibliographyCategory_MetaPath);
        } else if (ap instanceof ViewPath vp) {
            success = vp.startsWith(bibliographyCategory_ViewPath);
        }
        return success;
    }

    // must end with a valid category filename, or a valid bibliography filename
    private static boolean hasBibFileNameOrHasNoFileName(AsidePath ap) {
//        return ValidateAsidePath.BIBLIOGRAPHY_CATEGORY.test(ap) || ValidateAsidePath.BIBLIOGRAPHY.test(ap);
        String name = ap.getPath().getFileName().toString();
        return ValidateName.CATEGORY.test(name) || ValidateName.BIBLIOGRAPHY.test(name);
    }

    // determine if constructor should throw an IllegalArgumentException.
    protected static boolean constructorArgIsInvalid(AsidePath ap) {
        return !startsWithBibliographyCategory(ap) || !hasBibFileNameOrHasNoFileName(ap);
    }

    protected static MetaPath generateNewBibliographyFileName(MetaPath parent) {
        String filename = AsidePathElement.generateUniqueFileName(new MetaPath()).getPath().getFileName().toString();
        String bibFilename = m_prefix + filename.substring(1);
        return parent.resolve(new MetaPath(Paths.get(bibFilename)));
    }

    protected static ViewPath generateNewBibliographyFileName(ViewPath parent) {
        String filename = AsidePathElement.generateUniqueFileName(new MetaPath()).getPath().getFileName().toString();
        String bibFilename = v_prefix + filename.substring(1);
        return parent.resolve(new ViewPath(Paths.get(bibFilename)));
    }

    // inherited from AsidePathElement

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AbstractBibliography that)) return false;
        if (!super.equals(o)) return false;
        return yearPublished == that.yearPublished && Objects.equals(authors, that.authors) && Objects.equals(title, that.title) && Objects.equals(comment, that.comment) && Objects.equals(references, that.references) && Objects.equals(isbn, that.isbn) && Objects.equals(doi, that.doi) && Objects.equals(url, that.url) && Objects.equals(arXiv_ID, that.arXiv_ID) && Objects.equals(ads_Bibcode, that.ads_Bibcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), authors, title, yearPublished, comment, references, isbn, doi, url, arXiv_ID, ads_Bibcode);
    }

    @Override
    public String toString() {
        return "AbstractBibliography{" +
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
