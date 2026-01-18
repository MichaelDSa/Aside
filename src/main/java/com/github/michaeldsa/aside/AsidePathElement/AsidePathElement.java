package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.AsidePath;
import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.Validation.ValidateAsidePath;

import java.util.Objects;

public abstract class AsidePathElement {
    protected MetaPath metaPath;
    protected ViewPath viewPath;
    // public Category stepParent; // subclasses can optionally assign this field

    // getters:
    public MetaPath getMetaPath() {return this.metaPath;}
    public ViewPath getViewPath() {return this.viewPath;}

    // abstract methods:
    // most of these methods are for subclasses that choose to include a Category stepParent field.
    public abstract Category getParentCategory();
    public abstract Category getStepParentsCategory();
    public abstract void setStepParentsCategory(Category newStepParents);
    public abstract boolean hasStepParents();

    // static methods:
    protected static boolean endsWithNoteName(AsidePath ap) {return ValidateAsidePath.NOTE_NAME.test(ap);}
    protected static boolean endsWithCategoryName(AsidePath ap) {return ValidateAsidePath.CATEGORY_NAME.test(ap);}

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AsidePathElement that)) return false;
        return Objects.equals(metaPath, that.metaPath) && Objects.equals(viewPath, that.viewPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(metaPath, viewPath);
    }

    @Override
    public String toString() {
        return "AsidePathElement{" +
                "metaPath=" + metaPath +
                ", viewPath=" + viewPath +
                '}';
    }
}
