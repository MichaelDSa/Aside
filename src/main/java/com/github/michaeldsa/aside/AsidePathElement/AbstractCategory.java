package com.github.michaeldsa.aside.AsidePathElement;

import java.util.Objects;

public abstract class AbstractCategory extends AsidePathElement{
    protected AbstractCategory stepParent;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AbstractCategory that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(stepParent, that.stepParent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), stepParent);
    }

    @Override
    public String toString() {
        return "AbstractCategory{" +
                "stepParent=" + stepParent +
                ", metaPath=" + metaPath +
                ", viewPath=" + viewPath +
                '}';
    }
}
