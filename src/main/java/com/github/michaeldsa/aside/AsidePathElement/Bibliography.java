package com.github.michaeldsa.aside.AsidePathElement;

import java.util.HashSet;

public class Bibliography extends AsidePathElement {
    private String authors;
    private String title;
    private int year_published;
    private String comment;
    private HashSet<AsidePathElement> references;
    @Override
    public Category getParentCategory() {
        return null;
    }

    @Override
    public Category getStepParentCategory() {
        return null;
    }

    @Override
    public void setStepParentCategory(Category newStepParents) {

    }

    @Override
    public boolean hasStepParents() {
        return false;
    }
}
