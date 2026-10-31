package com.github.michaeldsa.aside.Validation;

import com.github.michaeldsa.aside.AsidePath.AsidePath;
import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;

import java.nio.file.Path;
import java.util.function.Predicate;

public enum ValidateAsidePath implements Predicate<AsidePath> {
    NOTE_NAME (ap -> ValidatePath.NOTE_NAME.test(asMetaPath(ap)) && ValidatePath.NOTE_NAME.test(asViewPath(ap))),
    CATEGORY_NAME (ap -> ValidatePath.CATEGORY_NAME.test(asMetaPath(ap)) && ValidatePath.CATEGORY_NAME.test(asViewPath(ap))),
    BIBLIOGRAPHY_NAME (ap -> ValidatePath.BIBLIOGRAPHY_NAME.test(asMetaPath(ap)) && ValidatePath.BIBLIOGRAPHY_NAME.test(asViewPath(ap))),
    BIBLIOGRAPHY_CATEGORY_NAME (ap -> ValidatePath.BIBLIOGRAPHY_CATEGORY_NAME.test(asMetaPath(ap)) && ValidatePath.BIBLIOGRAPHY_CATEGORY_NAME.test(asViewPath(ap))),
    DISCARDED_NOTE_NAME (ap -> ValidatePath.DISCARDED_NOTE_NAME.test(asMetaPath(ap)) && ValidatePath.DISCARDED_NOTE_NAME.test(asViewPath(ap))),
    DISCARDED_BIBLIOGRAPHY_NAME (ap -> ValidatePath.DISCARDED_BIBLIOGRAPHY_NAME.test(asMetaPath(ap)) && ValidatePath.DISCARDED_BIBLIOGRAPHY_NAME.test(asViewPath(ap)));

    // class boilerplate:
    private final Predicate<AsidePath> predicate;

    ValidateAsidePath(Predicate<AsidePath> predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean test(AsidePath asidePath) {
        return predicate.test(asidePath);
    }

    private static Path asMetaPath(AsidePath ap) {
        return ap instanceof MetaPath
                ? ap.getPath()
                : new MetaPath(new ViewPath(ap.getPath())).getPath();
    }
    private static Path asViewPath(AsidePath ap) {
        return ap instanceof ViewPath
                ? ap.getPath()
                : new ViewPath(new MetaPath(ap.getPath())).getPath();
    }

}
