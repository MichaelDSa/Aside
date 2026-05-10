package com.github.michaeldsa.aside.Validation;

import com.github.michaeldsa.aside.AsidePath.AsidePath;
import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;

import java.util.function.Predicate;

public enum ValidateAsidePath implements Predicate<AsidePath> {
    NOTE_NAME (ap -> {
        String mname = "", vname = "";
        if(ap instanceof MetaPath mp) {
            mname = mp.getPath().getFileName().toString();
            vname = new ViewPath(mp).getPath().getFileName().toString();
        } else if (ap instanceof ViewPath vp) {
            mname = new MetaPath(vp).getPath().getFileName().toString();
            vname = vp.getPath().getFileName().toString();
        }
        return ValidateString.NOTE_NAME.test(mname) && ValidateString.NOTE_NAME.test(vname);
    }),
    CATEGORY_NAME (ap -> {
        String mname = "", vname = "";
        if(ap instanceof MetaPath mp) {
            mname = mp.getPath().getFileName().toString();
            vname = new ViewPath(mp).getPath().getFileName().toString();
        } else if (ap instanceof ViewPath vp) {
            mname = new MetaPath(vp).getPath().getFileName().toString();
            vname = vp.getPath().getFileName().toString();
        }
        return ValidateString.CATEGORY_NAME.test(mname) && ValidateString.CATEGORY_NAME.test(vname);
    }),
    BIBLIOGRAPHY_NAME (ap -> {
        String mname = "", vname = "";
        if (ap instanceof MetaPath mp) {
            mname = mp.getPath().getFileName().toString();
            vname = new ViewPath(mp).getPath().getFileName().toString();
        } else if (ap instanceof ViewPath vp) {
            mname = new MetaPath(vp).getPath().getFileName().toString();
            vname = vp.getPath().getFileName().toString();
        }
        return ValidateString.BIBLIOGRAPHY_NAME.test(mname) && ValidateString.BIBLIOGRAPHY_NAME.test(vname);
    }),
    DISCARDED_ELEMENT_NAME (ap -> {
        String mname = "", vname = "";
        if (ap instanceof MetaPath mp) {
            mname = mp.getPath().getFileName().toString();
            vname = new ViewPath(mp).getPath().getFileName().toString();
        } else if (ap instanceof ViewPath vp) {
            mname = new MetaPath(vp).getPath().getFileName().toString();
            vname = vp.getPath().getFileName().toString();
        }
        return ValidateString.DISCARDED_ELEMENT_NAME.test(mname) && ValidateString.DISCARDED_ELEMENT_NAME.test(vname);
    });

    // class boilerplate:
    private final Predicate<AsidePath> predicate;

    ValidateAsidePath(Predicate<AsidePath> predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean test(AsidePath asidePath) {
        return predicate.test(asidePath);
    }

}
