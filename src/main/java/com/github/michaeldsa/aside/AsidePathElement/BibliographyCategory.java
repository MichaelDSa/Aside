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
        if (AbstractBibliography.argumentIsInvalid(mp)) {
            System.err.println("IllegalArgumentException");
            throw new IllegalArgumentException("Invalid Path argument BibCat(MetaPath mp): " + mp);
        }

        // must not end with filename.
        metaPath = AsidePathElement.endsWithFileName(mp)
                ? mp.getParent()
                : mp;

        // must start with correct dir.
        metaPath = metaPath.startsWith(new MetaPath(Paths.get(RestrictedLists.getMetaPathBibliographyDirectoryName())))
                ? metaPath
                : new MetaPath(Paths.get(RestrictedLists.getMetaPathBibliographyDirectoryName())).resolve(metaPath);

        viewPath = new ViewPath(metaPath);
        stepParent = null;
        nest = new ArrayList<>();
    }
    public BibliographyCategory(ViewPath vp) {
        if (AbstractBibliography.argumentIsInvalid(vp)) {
            System.err.println("IllegalArgumentException");
            throw new IllegalArgumentException("Invalid Path argument BibCat(ViewPath vp): " + vp);
        }

        // must not end with filename.
        viewPath = AsidePathElement.endsWithFileName(vp)
                ? vp.getParent()
                : vp;

        // must start with correct dir
        viewPath = viewPath.startsWith(new ViewPath(Paths.get(RestrictedLists.getViewPathBibliographyDirectoryName())))
                ? viewPath
                : new ViewPath(Paths.get(RestrictedLists.getViewPathBibliographyDirectoryName())).resolve(viewPath);

        metaPath = new MetaPath(viewPath);
        stepParent = null;
        nest = new ArrayList<>();
    }

    public BibliographyCategory(BibliographyCategory bc) {
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
        metaPath = metaPath.startsWith(new MetaPath(Paths.get(RestrictedLists.getMetaPathBibliographyDirectoryName())))
                ? metaPath
                : new MetaPath(Paths.get(RestrictedLists.getMetaPathBibliographyDirectoryName())).resolve(metaPath);

        viewPath = new ViewPath(metaPath);
        stepParent = null;
        nest = new ArrayList<>();
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
