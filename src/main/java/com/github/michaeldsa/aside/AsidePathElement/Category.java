package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;

import java.util.ArrayList;
import java.util.Objects;

public class Category extends AbstractCategory {
    protected Category stepParent;

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
        viewPath = new ViewPath(metaPath);
        nest = new ArrayList<>();

        // AbstractCategory fields
        this.stepParent = null;
    }
    public Category(ViewPath vp) {
        if (AsidePathElement.endsWithFileName(vp)) {
            viewPath = vp.getParent();
        } else if (AsidePathElement.endsWithCategoryName(viewPath)) {
            viewPath = vp;
        } else {
            System.err.println("IllegalArgumentException");
            throw new IllegalArgumentException("Invalid Path Argument: " + vp);
        }

        // AsidePathElement fields
        viewPath = AsidePathElement.filterViewPathElements(viewPath);
        metaPath = new MetaPath(viewPath);
        nest = new ArrayList<>();

        // AbstractCategory fields
        this.stepParent = null;
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
        viewPath = new ViewPath(metaPath);
        nest = new ArrayList<>();

        // AbstractCategory fields
        this.stepParent = null;
    }

    @Override
    public Category getParentCategory() {
        // returns default category if parent is AProot.
        return new Category(metaPath.getParent());
    }

    @Override
    public Category getStepParentCategory() {
        return this.stepParent;
    }

    @Override
    public void setStepParentCategory(AbstractCategory newStepParent) {
        if (newStepParent instanceof Category) {
            this.stepParent = (Category) newStepParent;
        }
    }

    @Override
    public boolean hasStepParent() {
        return this.stepParent != null;
    }

    @Override
    public String toString() {
        return "Category{" +
                "stepParent=" + stepParent +
                ", stepParent=" + stepParent +
                ", metaPath=" + metaPath +
                ", viewPath=" + viewPath +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Category category)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(this.stepParent, category.stepParent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.stepParent);
    }
}
