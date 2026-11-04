package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;

import java.util.List;

public class ImmutableBibliographyCategory extends AbstractCategory {
    private final MetaPath metaPath;
    private final ViewPath viewPath;
    private final List<AsidePathElement> nest;

    public ImmutableBibliographyCategory(BibliographyCategory bibliographyCategory) {
        this.metaPath = bibliographyCategory.getMetaPath();
        this.viewPath = bibliographyCategory.getViewPath();
        this.nest = bibliographyCategory.getNest();
    }
    @Override
    // test: what if metaPath.getParent is asidepath/DEFAULT, or asidePathRoot?
    public BibliographyCategory getParentCategory() {
        return new BibliographyCategory(this.metaPath.getParent());
    }
    @Override
    public MetaPath getMetaPath() { return this.metaPath; }
    @Override
    public ViewPath getViewPath() { return this.viewPath; }
    @Override
    public List<AsidePathElement> getNest() { return this.nest; }

}
