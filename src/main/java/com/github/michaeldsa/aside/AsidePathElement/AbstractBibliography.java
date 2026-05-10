package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.AsidePath;
import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.Initialization.RootPaths;
import com.github.michaeldsa.aside.Validation.ValidateAsidePath;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;

public abstract class AbstractBibliography extends AsidePathElement {
    protected BibCat stepParent;

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
    /* AsidePath cannot start with DISCARDED or DEFAULT. */
    private static boolean startsWithWrongDir(AsidePath ap) {
        String m_string = "", v_string = "";
        if(ap instanceof MetaPath mp) {
            m_string = mp.getPath().toString();
            v_string = new ViewPath(mp).getPath().toString();
        } else if (ap instanceof ViewPath vp) {
            v_string = vp.getPath().toString();
            m_string = new MetaPath(vp).getPath().toString();
        }
        Path m_dis = RestrictedLists.getDiscardedElementDirectory().getMetaPath().getPath();
        Path v_dis = RestrictedLists.getDiscardedElementDirectory().getViewPath().getPath();
        Path m_def = RestrictedLists.getDefaultCategory().getMetaPath().getPath();
        Path v_def = RestrictedLists.getDefaultCategory().getViewPath().getPath();
        Path m_path = Paths.get(m_string);
        Path v_path = Paths.get(v_string);

        return m_path.startsWith(m_dis)
                || v_path.startsWith(v_dis)
                || m_path.startsWith(m_def)
                || v_path.startsWith(v_def);
    }

    // must end with a valid category filename, or a valid bibliography filename
    private static boolean noFileNameOrHasBibFileName(AsidePath ap) {
        return ValidateAsidePath.CATEGORY_NAME.test(ap) || ValidateAsidePath.BIBLIOGRAPHY_NAME.test(ap);
    }
    // determine if constructor should throw an IllegalArgumentException.
    protected static boolean argumentIsInvalid(AsidePath ap) {
        return startsWithWrongDir(ap) || !noFileNameOrHasBibFileName(ap);
    }

    protected static MetaPath generateNewBibliographyFileName(MetaPath parent) {
        String filename = AbstractNote.generateNewNoteName(new MetaPath()).getPath().getFileName().toString();
        String bibFilename = m_prefix + filename.substring(1);
        return parent.resolve(new MetaPath(Paths.get(bibFilename)));
    }

    protected static ViewPath generateNewBibliographyFileName(ViewPath parent) {
        String filename = AbstractNote.generateNewNoteName(new MetaPath()).getPath().getFileName().toString();
        String bibFilename = v_prefix + filename;
        return parent.resolve(new ViewPath(Paths.get(bibFilename)));
    }

    // inherited from AsidePathElement
    @Override
    public AbstractCategory getParentCategory() {
        return new BibCat(metaPath.getParent());
    }

    @Override
    public AbstractCategory getStepParentCategory() {
        return stepParent;
    }

    @Override
    public void setStepParentCategory(AbstractCategory newStepParent) {
        this.stepParent = (BibCat) newStepParent;
    }

    @Override
    public boolean hasStepParent() {
        return stepParent != null;
    }
}
