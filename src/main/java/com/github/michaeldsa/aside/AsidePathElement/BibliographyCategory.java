package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.Validation.ValidateAsidePath;

import java.nio.file.Paths;
import java.util.ArrayList;

/* Bibliography Category:
To handle subdirectories of BIBLIOGRAPHY AsidePath elements */
public class BibliographyCategory extends AbstractCategory {
    private ImmutableBibliographyCategory previousState;

    public BibliographyCategory(MetaPath mp) {
        /* client can use metapath root to create a new
        BibliographyCategory instance pointing to default bibliography dirs */
        if(mp.equals(new MetaPath())) {
            mp = AbstractBibliography.bibliographyCategory_MetaPath;
        }
        if (AbstractBibliography.constructorArgIsInvalid(mp)) {
            System.err.println("IllegalArgumentException");
            throw new IllegalArgumentException("Invalid Path argument BibliographyCategory(MetaPath mp): " + mp);
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
        /* validate new path: */
        if (!ValidateAsidePath.BIBLIOGRAPHY_CATEGORY.test(metaPath)) {
            System.err.println("BibliographyCategory: invalid path argument mutation: " + metaPath);
            throw new IllegalArgumentException("Invalid Path Argument mutation: " + metaPath);
        }
        viewPath = new ViewPath(metaPath);
        nest = new ArrayList<>();

        // AbstractCategory fields:
        // none

        // this class' fields
        previousState = new ImmutableBibliographyCategory(this);

    }
    public BibliographyCategory(ViewPath vp) {
        /* client can use viewpath root to create a new
        BibliographyCategory instance pointing to default bibliography dirs */
        if (vp.equals(new ViewPath())) {
            vp = AbstractBibliography.bibliographyCategory_ViewPath;
        }
        if (AbstractBibliography.constructorArgIsInvalid(vp)) {
            System.err.println("IllegalArgumentException");
            throw new IllegalArgumentException("Invalid Path argument BibliogrphyCategory(ViewPath vp): " + vp);
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
        /* validate new path: */
        if (!ValidateAsidePath.BIBLIOGRAPHY_CATEGORY.test(viewPath)) {
            System.err.println("BibliographyCategory: invalid path argument mutation: " + viewPath);
            throw new IllegalArgumentException("Invalid Path Argument mutation: " + viewPath);
        }
        metaPath = new MetaPath(viewPath);
        nest = new ArrayList<>();

        // AbstractCategory fields:
        // none

        // this class' fields
        previousState = new ImmutableBibliographyCategory(this);

    }

    public BibliographyCategory(BibliographyCategory bc) {
        /* unlikely, but can fail if cast to BibliographyCategory */
        if (AbstractBibliography.constructorArgIsInvalid(bc.getMetaPath()) || AsidePathElement.endsWithFileName(bc.getMetaPath())) {
            System.err.println("IllegalArgumentException");
            throw new IllegalArgumentException("Invalid Path argument BibliographyCategory(MetaPath mp): " + bc.getMetaPath());
        }

        // AsidePathElement fields:
        /* must start with correct dir */
        metaPath = bc.getMetaPath().startsWith(new MetaPath(Paths.get(RestrictedLists.getMetaPathBibliographyDirectoryName())))
                ? bc.getMetaPath()
                : new MetaPath(Paths.get(RestrictedLists.getMetaPathBibliographyDirectoryName())).resolve(metaPath);
        /* validate new path: */
        if (!ValidateAsidePath.BIBLIOGRAPHY_CATEGORY.test(metaPath)) {
            System.err.println("BibliographyCategory: invalid path argument mutation: " + metaPath);
            throw new IllegalArgumentException("Invalid Path Argument mutation: " + metaPath);
        }
        viewPath = new ViewPath(metaPath);
        nest = new ArrayList<>();

        // AbstractCategory fields:
        // none

        // this class' fields
        previousState = bc.getPreviousState(); // next: make this method.

    }

    // static singleton BibliographyCategory that holds the root bib category;
    private static class BibliographyCategoryHolder {
        private static final BibliographyCategory bc = new BibliographyCategory(new MetaPath(Paths.get(RestrictedLists.getMetaPathBibliographyDirectoryName())));
    }

    public static BibliographyCategory getRootCategory() {
        return BibliographyCategoryHolder.bc;
    }

    @Override
    public BibliographyCategory getParentCategory() {
        return new BibliographyCategory(metaPath);
    }

    /* Only BibCat or Bibliography may be added. Later we
    change this to BibCat, MutableBib, ImmutableBib */
    @Override
    public void nestAdd(AsidePathElement ap) {
        if ((ap instanceof BibliographyCategory) || (ap instanceof AbstractBibliography)) {
            nest.add(ap);
        }
    }

    public BibliographyCategory setParentCategory(BibliographyCategory newParentCategory) {
        // not possible if filename is BIBLIOGRAPHY.
        if (!metaPath.getFileName().equals(AbstractBibliography.bibliographyCategory_MetaPath)) {
            // validate by instantiation
            try {
                metaPath = new BibliographyCategory(newParentCategory).getMetaPath().resolve(metaPath.getFileName());
                viewPath = new ViewPath(metaPath);
            } catch (IllegalArgumentException e) {
                System.err.println("BibliographyCategory.setParentCategory: IllegalArgumentException. Argument must be BibliographyCategory");
            }
        }
        return this;
    }


    private ImmutableBibliographyCategory getPreviousState() {
        return previousState;
    }

    public BibliographyCategory setPreviousState() {
        if(previousState == null) {
            this.previousState = new ImmutableBibliographyCategory(this);
        }
        return this;
    }

    public BibliographyCategory getInstanceWithSwappedStates() {
        BibliographyCategory instance = new BibliographyCategory(this);
        instance.previousState = null;
        instance.setPreviousState();
        instance.setParentCategory(this.previousState.getParentCategory());
        return instance;
    }


    // inherited methods:
    // none.
    // Now write toString, equals & hashcode.

}
