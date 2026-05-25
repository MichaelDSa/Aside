package com.github.michaeldsa.aside.AsidePathElement;

/*
DiscardedCategory is an immutable preset Category. There is only one
DiscardedElement category (asidePathRoot/DISCARDED). Sub-categories
may not be created in DISCARDED.
 */

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;

import java.nio.file.Paths;

public class DiscardedCategory extends AbstractCategory{

    private DiscardedCategory(MetaPath mp) {
        metaPath = mp;
        viewPath = new ViewPath(mp);
        stepParent = null;
    }

    private static class DiscardedCategoryHolder {
        private static final DiscardedCategory dc = new DiscardedCategory(new MetaPath(Paths.get(RestrictedLists.getMetaPathDiscardedDirectoryName())));
    }
    public static DiscardedCategory getInstance() {
        return DiscardedCategoryHolder.dc;
    }

    @Override
    public AbstractCategory getParentCategory() { return new Category(new MetaPath()); }

    @Override
    public AbstractCategory getStepParentCategory() { return null; }

    @Override
    public void setStepParentCategory(AbstractCategory newStepParent) { }

    @Override
    public boolean hasStepParent() {
        return false;
    }

    @Override
    public String toString() {
        return "DiscardedCategory{" +
                "metaPath=" + metaPath +
                ", viewPath=" + viewPath +
                '}';
    }
}
