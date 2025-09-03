package com.github.michaeldsa.aside.Validation;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.AsidePathElement.AsidePathElement;
import com.github.michaeldsa.aside.AsidePathElement.Note;

import java.util.function.Predicate;

public enum ValidateElement implements Predicate<AsidePathElement> {
    NOTE_NAME_ELEMENT (p -> {
        MetaPath mpath;
        ViewPath vpath;
        String mname = "", vname = "";

        if (p instanceof Note) {
            // p.getMetaPath() will return null
            // unless Note class is fixed.
            mpath = p.getMetaPath();
            vpath = p.getViewPath();
            mname = mpath.getPath().getFileName().toString();
            vname = vpath.getPath().getFileName().toString();
        }
        return ValidateString.NOTE_NAME.test(mname) && ValidateString.NOTE_NAME.test(vname);
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
