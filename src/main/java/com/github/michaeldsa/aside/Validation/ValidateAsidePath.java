package com.github.michaeldsa.aside.Validation;

import com.github.michaeldsa.aside.AsidePath.AsidePath;
import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;

import java.nio.file.Path;
import java.util.function.Predicate;

public enum ValidateAsidePath implements Predicate<AsidePath> {
    NOTE(ap -> ValidatePath.NOTE.test(asMetaPath(ap)) && ValidatePath.NOTE.test(asViewPath(ap))),
    CATEGORY(ap -> ValidatePath.CATEGORY.test(asMetaPath(ap)) && ValidatePath.CATEGORY.test(asViewPath(ap))),
    BIBLIOGRAPHY(ap -> ValidatePath.BIBLIOGRAPHY.test(asMetaPath(ap)) && ValidatePath.BIBLIOGRAPHY.test(asViewPath(ap))),
    BIBLIOGRAPHY_CATEGORY(ap -> ValidatePath.BIBLIOGRAPHY_CATEGORY.test(asMetaPath(ap)) && ValidatePath.BIBLIOGRAPHY_CATEGORY.test(asViewPath(ap))),
    DISCARDED_NOTE(ap -> ValidatePath.DISCARDED_NOTE.test(asMetaPath(ap)) && ValidatePath.DISCARDED_NOTE.test(asViewPath(ap))),
    DISCARDED_BIBLIOGRAPHY(ap -> ValidatePath.DISCARDED_BIBLIOGRAPHY.test(asMetaPath(ap)) && ValidatePath.DISCARDED_BIBLIOGRAPHY.test(asViewPath(ap)));

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
