package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

/* Bibliography Category:
To handle subdirectories of BIBLIOGRAPHY AsidePath elements */
public class BibliographyCategory extends AbstractCategory {

    protected BibliographyCategory stepParent;

    public BibliographyCategory(MetaPath mp) {
        /* client can use metapath root to create a new
        BibliographyCategory instance pointing to default bibliography dirs */
        if(mp.equals(new MetaPath())) {
            mp = AbstractBibliography.bibliographyCategory_MetaPath;
        }
        if (AbstractBibliography.constructorArgIsInvalid(mp)) {
            System.err.println("IllegalArgumentException");
            throw new IllegalArgumentException("Invalid Path argument BibCat(MetaPath mp): " + mp);
        }

        // AsidePathElement fields:
        /* must not end with filename. */
        metaPath = AsidePathElement.endsWithFileName(mp)
                ? mp.getParent()
                : mp;
        /* must start with correct dir. */
        metaPath = metaPath.startsWith(new MetaPath(Paths.get(RestrictedLists.getMetaPathBibliographyDirectoryName())))
                ? metaPath
                : new MetaPath(Paths.get(RestrictedLists.getMetaPathBibliographyDirectoryName())).resolve(metaPath);
        viewPath = new ViewPath(metaPath);
        nest = new ArrayList<>();

        // This class' fields:
        this.stepParent = null;
    }
    public BibliographyCategory(ViewPath vp) {
        /* client can use viewpath root to create a new
        BibliographyCategory instance pointing to default bibliography dirs */
        if (vp.equals(new ViewPath())) {
            vp = AbstractBibliography.bibliographyCategory_ViewPath;
        }
        if (AbstractBibliography.constructorArgIsInvalid(vp)) {
            System.err.println("IllegalArgumentException");
            throw new IllegalArgumentException("Invalid Path argument BibCat(ViewPath vp): " + vp);
        }

        // AsidePathElement fields:
        /* must not end with filename. */
        viewPath = AsidePathElement.endsWithFileName(vp)
                ? vp.getParent()
                : vp;
        /* must start with correct dir */
        viewPath = viewPath.startsWith(new ViewPath(Paths.get(RestrictedLists.getViewPathBibliographyDirectoryName())))
                ? viewPath
                : new ViewPath(Paths.get(RestrictedLists.getViewPathBibliographyDirectoryName())).resolve(viewPath);
        metaPath = new MetaPath(viewPath);
        nest = new ArrayList<>();

        // This class' fields:
        this.stepParent = null;
    }

    public BibliographyCategory(BibliographyCategory bc) {
        /* unlikely, but can fail if cast to BibliographyCategory */
        if (AbstractBibliography.constructorArgIsInvalid(bc.getMetaPath()) || AsidePathElement.endsWithFileName(bc.getMetaPath())) {
            System.err.println("IllegalArgumentException");
            throw new IllegalArgumentException("Invalid Path argument BibCat(MetaPath mp): " + bc.getMetaPath());
        }

        // AsidePathElement fields:
        /* must start with correct dir */
        metaPath = bc.getMetaPath().startsWith(new MetaPath(Paths.get(RestrictedLists.getMetaPathBibliographyDirectoryName())))
                ? bc.getMetaPath()
                : new MetaPath(Paths.get(RestrictedLists.getMetaPathBibliographyDirectoryName())).resolve(metaPath);
        viewPath = new ViewPath(metaPath);
        nest = new ArrayList<>();

        // This class' fields:
        this.stepParent = null;
    }

    // static singleton BibliographyCategory that holds the root bib category;
    private static class BibliographyCategoryHolder {
        private static final BibliographyCategory bc = new BibliographyCategory(new MetaPath(Paths.get(RestrictedLists.getMetaPathBibliographyDirectoryName())));
    }

    public static BibliographyCategory getRootCategory() {
        return BibliographyCategoryHolder.bc;
    }


    /* Only BibCat or Bibliography may be added. Later we
    change this to BibCat, MutableBib, ImmutableBib */
    @Override
    public void nestAdd(AsidePathElement ap) {
        if ((ap instanceof BibliographyCategory) || (ap instanceof AbstractBibliography)) {
            nest.add(ap);
        }
    }

    // inherited methods:
    @Override
    public BibliographyCategory getParentCategory() {
        return new BibliographyCategory(metaPath.getParent());
    }
    @Override
    public BibliographyCategory getStepParentCategory() {
        return stepParent;
    }
    @Override
    public void setStepParentCategory(AbstractCategory newStepParent) {
        this.stepParent = (BibliographyCategory) newStepParent;

    }
    @Override
    public boolean hasStepParent() {
        return false;
    }

    // Now write toString, equals & hashcode.

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BibliographyCategory that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(stepParent, that.stepParent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), stepParent);
    }

    @Override
    public String toString() {
        return "BibliographyCategory{" +
                "stepParent=" + stepParent +
                ", metaPath=" + metaPath +
                ", viewPath=" + viewPath +
                '}';
    }
}
