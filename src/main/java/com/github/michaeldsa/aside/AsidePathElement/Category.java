package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.RootPaths;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

public class Category extends AsidePathElement{
        // inherited:
    // MetaPath metaPath;
    // ViewPath viewPath;
    protected Category stepParent;

    public Category(MetaPath mp) {
        if (AsidePathElement.endsWithNoteName(mp)) {
            metaPath = mp.getParent();
        } else if (AsidePathElement.endsWithCategoryName(mp)) {
            metaPath = mp;
        }

        metaPath = AsidePathElement.filterMetaPathElements(metaPath);
        viewPath = new ViewPath(metaPath);
        stepParent = null;
        nest = new ArrayList<>();
    }
    public Category(ViewPath vp) {
        if (AsidePathElement.endsWithNoteName(vp)) {
            viewPath = vp.getParent();
        } else if (AsidePathElement.endsWithCategoryName(viewPath)) {
            viewPath = vp;
        }
        viewPath = AsidePathElement.filterViewPathElements(viewPath);
        metaPath = new MetaPath(viewPath);
        stepParent = null;
        nest = new ArrayList<>();
    }
    public Category(Category c) {
        MetaPath mp = c.getMetaPath();
        if (AsidePathElement.endsWithNoteName(mp)) {
            metaPath = mp.getParent();
        } else if (AsidePathElement.endsWithCategoryName(mp)) {
            metaPath = mp;
        }
        metaPath = AsidePathElement.filterMetaPathElements(metaPath);
        viewPath = new ViewPath(metaPath);
        stepParent = null;
        nest = new ArrayList<>();
    }

    @Override
    public Category getParentCategory() {
        return new Category(metaPath);
    }

    @Override
    public Category getStepParentCategory() {
        return stepParent;
    }

    @Override
    public void setStepParentCategory(Category newStepParents) {
        stepParent = newStepParents;

    }

    @Override
    public boolean hasStepParents() {
        return stepParent != null;
    }

    @Override
    public String toString() {
        return "Category{" +
                "stepParent=" + stepParent +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Category category)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(stepParent, category.stepParent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), stepParent);
    }
}
