package com.github.michaeldsa.aside.Validation;

import com.github.michaeldsa.aside.AsidePath.AsidePath;
import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;

import java.nio.file.Path;
import java.util.function.Predicate;

public enum ValidateAsidePath implements Predicate<AsidePath> {
    NOTE_NAME (ap -> {
        Path mname = null, vname = null;
        if(ap instanceof MetaPath mp) {
            mname = mp.getPath();
            vname = new ViewPath(mp).getPath();
        } else if (ap instanceof ViewPath vp) {
            mname = new MetaPath(vp).getPath();
            vname = vp.getPath();
        }
        return ValidatePath.NOTE_NAME.test(mname) && ValidatePath.NOTE_NAME.test(vname);
    }),
    CATEGORY_NAME (ap -> {
        Path mname = null, vname = null;
        if(ap instanceof MetaPath mp) {
            mname = mp.getPath();
            vname = new ViewPath(mp).getPath();
        } else if (ap instanceof ViewPath vp) {
            mname = new MetaPath(vp).getPath();
            vname = vp.getPath();
        }
        return ValidatePath.CATEGORY_NAME.test(mname) && ValidatePath.CATEGORY_NAME.test(vname);
    }),
    BIBLIOGRAPHY_NAME (ap -> {
        Path mname = null, vname = null;
        if (ap instanceof MetaPath mp) {
            mname = mp.getPath();
            vname = new ViewPath(mp).getPath();
        } else if (ap instanceof ViewPath vp) {
            mname = new MetaPath(vp).getPath();
            vname = vp.getPath();
        }
        return ValidatePath.BIBLIOGRAPHY_NAME.test(mname) && ValidatePath.BIBLIOGRAPHY_NAME.test(vname);
    }),
    BIBLIOGRAPHY_CATEGORY_NAME (ap -> {
        Path mname = null, vname = null;
        if (ap instanceof MetaPath mp) {
            mname = mp.getPath();
            vname = new ViewPath(mp).getPath();
        } else if (ap instanceof ViewPath vp) {
            mname = new MetaPath(vp).getPath();
            vname = vp.getPath();
        }
        return ValidatePath.BIBLIOGRAPHY_CATEGORY_NAME.test(mname) && ValidatePath.BIBLIOGRAPHY_CATEGORY_NAME.test(vname);
    }),
    DISCARDED_NOTE_NAME(ap -> {
        Path mname = null, vname = null;
        if (ap instanceof MetaPath mp) {
            mname = mp.getPath();
            vname = new ViewPath(mp).getPath();
        } else if (ap instanceof ViewPath vp) {
            mname = new MetaPath(vp).getPath();
            vname = vp.getPath();
        }
        return ValidatePath.DISCARDED_NOTE_NAME.test(mname) && ValidatePath.DISCARDED_NOTE_NAME.test(vname);
    }),
    DISCARDED_BIBLIOGRAPHY_NAME(ap -> {
        Path mname = null, vname = null;
        if (ap instanceof MetaPath mp) {
            mname = mp.getPath();
            vname = new ViewPath(mp).getPath();
        } else if (ap instanceof ViewPath vp) {
            mname = new MetaPath(vp).getPath();
            vname = vp.getPath();
        }
        return ValidatePath.DISCARDED_BIBLIOGRAPHY_NAME.test(mname) && ValidatePath.DISCARDED_BIBLIOGRAPHY_NAME.test(vname);
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
