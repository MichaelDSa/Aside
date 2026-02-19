package com.github.michaeldsa.aside.Validation;

import com.github.michaeldsa.aside.AsidePathElement.AsidePathElement;
import com.github.michaeldsa.aside.AsidePathElement.Category;

import java.util.function.Predicate;

public enum ValidateAsidePathElement implements Predicate<AsidePathElement> {
    NOTE_NAME (ape -> {
        if(!(ape instanceof Category)) {
            return ValidateAsidePath.NOTE_NAME.test(ape.getMetaPath()) &&
                    ValidateAsidePath.NOTE_NAME.test(ape.getViewPath());
        }
        return false;
    }),
    CATEGORY_NAME (ape -> {
       if(ape instanceof Category c) {
           return ValidateAsidePath.CATEGORY_NAME.test(c.getMetaPath()) &&
                   ValidateAsidePath.CATEGORY_NAME.test(c.getViewPath());
       }
       return false;
    });

    // function boilerplate
    private final Predicate<AsidePathElement> predicate;
    
    ValidateAsidePathElement(Predicate<AsidePathElement> predicate) {
        this.predicate = predicate;
    }
    @Override
    public boolean test(AsidePathElement asidePathElement) {
        return predicate.test(asidePathElement);
    }
}
