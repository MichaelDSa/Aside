package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.Validation.ValidateAsidePath;

import java.util.ArrayList;
import java.util.Arrays;

public class Category extends AbstractCategory {
    private ImmutableCategory previousState;

    public Category(MetaPath mp) {
        constructorHelper(mp);
    }
    public Category(ViewPath vp) {
        constructorHelper(vp);
    }
    public Category(Category c) {
        constructorHelper(c.getMetaPath());
        nest = c.getNest();
        previousState = c.getPreviousState();
    }
    public Category(String asidePath, String... more) {
        constructorHelper(asidePath, more);
    }
    public Category(String[] array) {
        constructorHelper(array[0], Arrays.copyOfRange(array, 1, array.length));
    }

    private void constructorHelper(MetaPath mp) {
        if (AsidePathElement.endsWithFileName(mp)) {
            metaPath = mp.getParent();
        } else if (AsidePathElement.endsWithCategoryName(mp)) {
            metaPath = mp;
        } else {
            System.err.println("IllegalArgumentException");
            throw new IllegalArgumentException("Invalid Path Argument: " + mp);
        }

        // AsidePathElement fields
        metaPath = AsidePathElement.filterMetaPathElements(metaPath);
        /* validate new path: */
        if (!ValidateAsidePath.CATEGORY.test(metaPath)) {
            System.err.println("Category: invalid path argument mutation:  " + metaPath);
            throw new IllegalArgumentException("Invalid path argument mutation:  " + metaPath);
        }
        viewPath = new ViewPath(metaPath);
        nest = new ArrayList<>();

        // AbstractCategory fields
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

    public Category getParentCategory() {
        return new Category(metaPath.getParent());
    }

    public Category setParentCategory(Category newParentCategory) {
        // not possible if filename is DEFAULT
        if (!metaPath.getFileName().equals(new Category(new MetaPath()).getMetaPath())) {
            // validate by instantiation:
            try {
                metaPath = new Category(newParentCategory).getMetaPath().resolve(metaPath.getFileName());
                viewPath = new ViewPath(metaPath);
            } catch (IllegalArgumentException e) {
                System.err.println("Category.setParentCategory: IllegalArgumentException. Argument must be Category");
            }
        }
        return this;
    }

    public Category setPreviousState() {
        if (previousState == null) {
            previousState = new ImmutableCategory(this);
        }
        return this;
    }

    public Category getInstanceWithSwappedStates(){
        Category instance = new Category(this);
        instance.previousState = null;
        instance.setPreviousState();
        instance.setParentCategory(this.previousState.getParentCategory());
        return instance;
    }
    private ImmutableCategory getPreviousState() { return previousState; }


    @Override
    public String toString() {
        return "Category{" +
                ", metaPath=" + metaPath +
                ", viewPath=" + viewPath +
                '}';
    }

}
