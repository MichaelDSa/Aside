package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;

public class Category extends AsidePathElement {
    public Category(MetaPath metaPath) {
        this.metaPath = metaPath;
        this.viewPath = new ViewPath(metaPath);
    }
    public Category(ViewPath viewPath) {
        this.viewPath = viewPath;
        this.metaPath = new MetaPath(viewPath);
    }

}
