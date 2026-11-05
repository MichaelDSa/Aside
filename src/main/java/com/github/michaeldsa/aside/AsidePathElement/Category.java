package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.Validation.ValidateAsidePath;

import java.util.ArrayList;
import java.util.Objects;

public class Category extends AbstractCategory {
    private ImmutableCategory previousState;

    public Category(MetaPath mp) {
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
    public Category(ViewPath vp) {
        if (AsidePathElement.endsWithFileName(vp)) {
            viewPath = vp.getParent();
        } else if (AsidePathElement.endsWithCategoryName(vp)) {
            viewPath = vp;
        } else {
            System.err.println("IllegalArgumentException");
            throw new IllegalArgumentException("Invalid Path Argument: " + vp);
        }

        // AsidePathElement fields
        viewPath = AsidePathElement.filterViewPathElements(viewPath);
        /* validate new path: */
        if (!ValidateAsidePath.CATEGORY.test(viewPath)) {
            System.err.println("Category: invalid path argument mutation:  " + viewPath);
            throw new IllegalArgumentException("Invalid path argument mutation:  " + viewPath);
        }
        metaPath = new MetaPath(viewPath);
        nest = new ArrayList<>();

        // AbstractCategory fields
        // none

        // this class' state
        previousState = null;
    }
    public Category(Category c) {
        MetaPath mp = c.getMetaPath();
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

        // This class' fields:
        previousState = c.getPreviousState();
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
    public ImmutableCategory getPreviousState() { return previousState; }


    @Override
    public String toString() {
        return "Category{" +
                ", metaPath=" + metaPath +
                ", viewPath=" + viewPath +
                '}';
    }

}
