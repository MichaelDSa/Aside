package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.AsidePath;
import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.Validation.ValidateAsidePath;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/* Bibliography Category:
To handle subdirectories of BIBLIOGRAPHY AsidePath elements */
public class BibCat extends AbstractCategory {

    protected BibCat stepParent;

    public BibCat(MetaPath mp) {
        if (AbstractBibliography.argumentIsInvalid(mp)) {
            System.err.println("IllegalArgumentException");
            throw new IllegalArgumentException("Invalid Path argument BibCat(MetaPath mp): " + mp);
        }

        // must not end with filename.
        metaPath = AsidePathElement.endsWithFileName(mp)
                ? mp.getParent()
                : mp;

        // must start with correct dir.
        metaPath = metaPath.startsWith(RestrictedLists.getBibliographyDirectory().getMetaPath())
                ? metaPath
                : RestrictedLists.getBibliographyDirectory().getMetaPath().resolve(metaPath);

        viewPath = new ViewPath(metaPath);
        stepParent = null;
        nest = new ArrayList<>();
    }
    public BibCat(ViewPath vp) {
        if (AbstractBibliography.argumentIsInvalid(vp)) {
            System.err.println("IllegalArgumentException");
            throw new IllegalArgumentException("Invalid Path argument BibCat(ViewPath vp): " + vp);
        }

        // must not end with filename.
        viewPath = AsidePathElement.endsWithFileName(vp)
                ? vp.getParent()
                : vp;

        // must start with correct dir
        viewPath = viewPath.startsWith(RestrictedLists.getBibliographyDirectory().getViewPath())
                ? viewPath
                : RestrictedLists.getBibliographyDirectory().getViewPath().resolve(viewPath);

        metaPath = new MetaPath(viewPath);
        stepParent = null;
        nest = new ArrayList<>();
    }

    public BibCat(BibCat bc) {
        MetaPath mp = bc.getMetaPath();
        if (AbstractBibliography.argumentIsInvalid(mp)) {
            System.err.println("IllegalArgumentException");
            throw new IllegalArgumentException("Invalid Path argument BibCat(MetaPath mp): " + mp);
        }

        // must not end with filename
        metaPath = AsidePathElement.endsWithFileName(mp)
                ? mp.getParent()
                : mp;

        // must start with correct dir
        metaPath = metaPath.startsWith(RestrictedLists.getBibliographyDirectory().getMetaPath())
                ? metaPath
                : RestrictedLists.getBibliographyDirectory().getMetaPath().resolve(metaPath);

        viewPath = new ViewPath(metaPath);
        stepParent = null;
        nest = new ArrayList<>();
    }


    /* Only BibCat or Bibliography may be added. Later we
    change this to BibCat, MutableBib, ImmutableBib */
    @Override
    public void nestAdd(AsidePathElement ap) {
        if ((ap instanceof BibCat) || (ap instanceof AbstractBibliography)) {
            nest.add(ap);
        }
    }

    // inherited methods:
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
        return false;
    }

    // Now write toString, equals & hashcode.
}
