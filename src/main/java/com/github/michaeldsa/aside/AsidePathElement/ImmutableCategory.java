package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;

import java.util.List;

public class ImmutableCategory extends AbstractCategory {
    private final MetaPath metaPath;
    private final ViewPath viewPath;
    private final List<AsidePathElement> nest;

    public ImmutableCategory(Category category) {
        this.metaPath = category.getMetaPath();
        this.viewPath = category.getViewPath();
        this.nest = category.getNest();
    }

    @Override
    public Category getParentCategory() { return new Category(this.metaPath.getParent()); }
    @Override
    public MetaPath getMetaPath() { return this.metaPath; }
    @Override
    public ViewPath getViewPath() { return this.viewPath; }
    @Override
    public List<AsidePathElement> getNest() { return this.nest; }
}
