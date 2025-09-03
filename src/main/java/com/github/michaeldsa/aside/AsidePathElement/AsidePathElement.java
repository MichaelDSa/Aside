package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;

import java.util.Objects;

public abstract class AsidePathElement {
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AsidePathElement that)) return false;
        return Objects.equals(metaPath, that.metaPath) && Objects.equals(viewPath, that.viewPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(metaPath, viewPath);
    }

    protected MetaPath metaPath;
    protected ViewPath viewPath;

    public MetaPath getMetaPath(){
        return metaPath;
    }
    public ViewPath getViewPath(){
        return viewPath;
    }
    public void setAsidePaths(MetaPath metaPath){
        this.metaPath = metaPath;
        this.viewPath = new ViewPath(metaPath);
    }
    public void setAsidePaths(ViewPath viewPath){
        this.viewPath = viewPath;
        this.metaPath = new MetaPath(viewPath);
    }
}
