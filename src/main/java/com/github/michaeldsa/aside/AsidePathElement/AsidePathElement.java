package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.AsidePath;
import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.RootPaths;
import com.github.michaeldsa.aside.Validation.ValidateAsidePath;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public abstract class AsidePathElement {
    protected MetaPath metaPath;
    protected ViewPath viewPath;
    // public Category stepParent; // subclasses can optionally assign this field

    // getters:
    public MetaPath getMetaPath() {return this.metaPath;}
    public ViewPath getViewPath() {return this.viewPath;}

    // abstract methods:
    // most of these methods are for subclasses that choose to include a Category stepParent field.
    public abstract Category getParentCategory();
    public abstract Category getStepParentsCategory();
    public abstract void setStepParentsCategory(Category newStepParents);
    public abstract boolean hasStepParents();

    // static methods:
    protected static boolean endsWithNoteName(AsidePath ap) {return ValidateAsidePath.NOTE_NAME.test(ap);}
    protected static boolean endsWithCategoryName(AsidePath ap) {return ValidateAsidePath.CATEGORY_NAME.test(ap);}

    protected static MetaPath filterMetaPathElements(MetaPath mp) {
        // remove permanent directories such as .default
        // from the Path elements of metaPath.
        Path path = mp.getPath();
        Path perm = RootPaths.INSTANCE.getMetapath();
        boolean identical = false;
        boolean startsWith = false;
        boolean isLonger = false;

        for (String s : RestrictedLists.getPermanentDirectories()) {
            if (s.startsWith(".")) {
                perm = new MetaPath(Paths.get(s)).getPath();

                String path_str = path.toString().toLowerCase();
                String perm_str = perm.toString().toLowerCase();

                identical = path_str.equals(perm_str);
                startsWith = path_str.startsWith(perm_str);
                isLonger = path.getNameCount() > perm.getNameCount();
            }
            if (identical || startsWith) {
                break;
            }
        }
        if (identical) {
            return mp;
        }
        if (startsWith && isLonger) {
            return new MetaPath(path.subpath(perm.getNameCount(), path.getNameCount()));
        }
        return mp;
    }

    protected static ViewPath filterViewPathElements(ViewPath vp) {
        return new ViewPath(filterMetaPathElements(new MetaPath(vp)));
    }

    protected static MetaPath filterMetaPathElements_withNoteName(MetaPath mp) {
        Path p = mp.getPath();
        Path noteName = p.getFileName();
        Path parent = p.getParent();
        Path filtered = filterMetaPathElements(new MetaPath(parent)).getPath();
        return new MetaPath(filtered.resolve(noteName));
    }

    protected static ViewPath filterViewPathElements_withNoteName(ViewPath vp) {
        return new ViewPath(filterMetaPathElements(new MetaPath(vp)));
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AsidePathElement that)) return false;
        return Objects.equals(metaPath, that.metaPath) && Objects.equals(viewPath, that.viewPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(metaPath, viewPath);
    }

    @Override
    public String toString() {
        return "AsidePathElement{" +
                "metaPath=" + metaPath +
                ", viewPath=" + viewPath +
                '}';
    }
}
