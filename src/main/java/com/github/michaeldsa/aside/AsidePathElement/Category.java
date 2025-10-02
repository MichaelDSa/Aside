package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.AsidePath;
import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.Validation.ValidateAsidePath;

import java.util.Objects;


public class Category extends AsidePathElement {
    private Category stepParent;

    public Category(){}
    public Category(MetaPath metaPath) {
        // assign metaPath:
        if(endsWithCategoryName(metaPath)) {
            this.metaPath = Objects.requireNonNull(metaPath);
        } else if (endsWithNoteName(metaPath)) {
            this.metaPath = Objects.requireNonNull(metaPath.getParent());
        } else {
            throw new IllegalArgumentException("Invalid metaPath: " + metaPath);
        }
        // assign viewPath
        this.viewPath = new ViewPath(this.metaPath);
    }
    public Category(ViewPath viewPath) {
        // assign viewPath
        if(endsWithCategoryName(viewPath)) {
            this.viewPath = Objects.requireNonNull(viewPath);
        } else if (endsWithNoteName(viewPath)) {
            this.viewPath = Objects.requireNonNull(viewPath.getParent());
        } else {
            throw new IllegalArgumentException("Invalid viewPath: " + viewPath);
        }
        // assign metaPath
        this.metaPath = new MetaPath(this.viewPath);
    }
    public Category(Category stepParent, MetaPath metaPath) {
        // assign metaPath:
        if(endsWithCategoryName(metaPath)) {
            this.metaPath = Objects.requireNonNull(metaPath);
        } else if (endsWithNoteName(metaPath)) {
            this.metaPath = Objects.requireNonNull(metaPath.getParent());
        } else {
            throw new IllegalArgumentException("Invalid metaPath: " + metaPath);
        }
        // assign viewPath and stepParent:
        this.viewPath = new ViewPath(this.metaPath);
        this.stepParent = Objects.requireNonNull(stepParent);
    }

    public Category(Category stepParent, ViewPath viewPath) {
        // assign viewPath:
        if(endsWithCategoryName(viewPath)) {
            this.viewPath = Objects.requireNonNull(viewPath);
        } else if (endsWithNoteName(viewPath)) {
            this.viewPath = Objects.requireNonNull(viewPath.getParent());
        } else {
            throw new IllegalArgumentException("Invalid viewPath: " + viewPath);
        }
        // assign metaPath and stepParent:
        this.metaPath = new MetaPath(this.viewPath);
        this.stepParent = Objects.requireNonNull(stepParent);
    }

    // getters/setters:
    public void setParent(Category stepParent) {
        this.stepParent = stepParent;
    }
    public void setParent(MetaPath metaPath) {
        setParent(new Category(metaPath));
    }
    public void setParent(ViewPath viewPath) {
        setParent(new Category(viewPath));
    }

    public Category getParent() {
        return new Category(metaPath.getParent());
    }

    public Category getStepParent(){
        return stepParent;
    }

    public boolean hasSetpParent() {
        return stepParent != null;
    }

    private boolean endsWithCategoryName(AsidePath asidePath) {
        return ValidateAsidePath.CATEGORY_NAME.test(asidePath);
    }

    private boolean endsWithNoteName(AsidePath asidePath) {
        return ValidateAsidePath.NOTE_NAME.test(asidePath);
    }

    // implement .equals, .hashCode, .toString

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
