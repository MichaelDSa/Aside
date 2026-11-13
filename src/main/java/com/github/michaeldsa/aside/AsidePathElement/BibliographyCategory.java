package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.Validation.ValidateAsidePath;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/* Bibliography Category:
To handle subdirectories of BIBLIOGRAPHY AsidePath elements */
public class BibliographyCategory extends AbstractCategory {
    private ImmutableBibliographyCategory previousState;

    public BibliographyCategory() {
        constructorHelper(new MetaPath(RestrictedLists.getMetaPathBibliographyDirectoryName()));
    }
    public BibliographyCategory(MetaPath mp) {
        constructorHelper(mp);
    }
    public BibliographyCategory(ViewPath vp) {
        constructorHelper(vp);
    }

    public BibliographyCategory(BibliographyCategory bc) {
        constructorHelper(bc.getMetaPath());
        nest = bc.getNest();
        previousState = bc.getPreviousState();
    }
    public BibliographyCategory(String first, String... more) {
        String[] elements = new String[1 + more.length];
        elements[0] = first;
        System.arraycopy(more, 0, elements, 1, more.length);
        constructorHelper(getBibliographyDirectoryName(first), elements);
    }
    public BibliographyCategory(String[] array) {
        constructorHelper(getBibliographyDirectoryName(array[0]), array);
    }

    // constructor helpers
    private void constructorHelper(MetaPath mp) {
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
        previousState = null;
    }
    private void constructorHelper(ViewPath vp) {
        constructorHelper(new MetaPath(vp));
    }
    private void constructorHelper(String first, String... more) {
        if (first.startsWith(".")) {
            constructorHelper(new MetaPath(first, more));
        } else {
            constructorHelper(new ViewPath(first, more));
        }
    }
    private String getBibliographyDirectoryName(String first) {
        return first.startsWith(".")
                ? RestrictedLists.getMetaPathBibliographyDirectoryName()
                : RestrictedLists.getViewPathBibliographyDirectoryName();
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BibliographyCategory that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(previousState, that.previousState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), previousState);
    }

    @Override
    public String toString() {
        return "BibliographyCategory{" +
                "previousState=" + previousState +
                ", metaPath=" + metaPath +
                ", viewPath=" + viewPath +
                '}';
    }
}
