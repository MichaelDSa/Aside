package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;

public class DiscardedBibliography extends AbstractDiscardedElement{

    @Override
    public DiscardedBibliography setOriginalMetaPath(MetaPath original) {
        this.originalMetaPath = original;
        this.originalViewPath = new ViewPath(originalMetaPath);
        return this;
    }

    @Override
    public DiscardedBibliography setOriginalViewPath(ViewPath original) {
        this.originalViewPath = original;
        this.originalMetaPath = new MetaPath(originalViewPath);
        return this;
    }
}
