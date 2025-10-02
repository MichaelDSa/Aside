package com.github.michaeldsa.aside.Validation;

import com.github.michaeldsa.aside.AsidePathElement.AsidePathElement;
import com.github.michaeldsa.aside.AsidePathElement.Category;
import com.github.michaeldsa.aside.AsidePathElement.MutableNote;

import java.util.function.Predicate;

public enum ValidateElement implements Predicate<AsidePathElement> {
    NOTE_NAME (p -> {
        String mname = "", vname = "";
        if (p instanceof MutableNote) {
            mname = p.getMetaPath().getPath().getFileName().toString();
            vname = p.getMetaPath().getPath().getFileName().toString();
        }
        return ValidateString.NOTE_NAME.test(mname) && ValidateString.NOTE_NAME.test(vname);
    }),
    CATEGORY_NAME (p -> {
       String mname = "", vname = "";
       if (p instanceof Category) {
           mname = p.getMetaPath().getPath().getFileName().toString();
           vname = p.getMetaPath().getPath().getFileName().toString();
       }
       return ValidateString.CATEGORY_NAME.test(mname) && ValidateString.CATEGORY_NAME.test(vname);
    });

    // function boilerplate
    private final Predicate<AsidePathElement> predicate;
    
    ValidateElement(Predicate<AsidePathElement> predicate) {
        this.predicate = predicate;
    }
    @Override
    public boolean test(AsidePathElement asidePathElement) {
        return predicate.test(asidePathElement);
    }
}
