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
        MetaPath perm = new MetaPath(); // MetaPath root

        // return default if mp == metapath root.
        if (path.equals(perm.getPath())) {
            return RestrictedLists.getDefaultCategory().getMetaPath();
        }
        if (endsWithNoteName(mp)) {
            return filterMetaPathElements_withNoteName(mp);
        }

        // check if mp is a permanent directory
        boolean identical = false;
        boolean startsWith = false;
        boolean isLonger = false;

        for (String s : RestrictedLists.getPermanentDirectories()) {
            if (s.startsWith(".")) {
                perm = new MetaPath(Paths.get(s));

                String path_str = path.toString().toLowerCase();
                String perm_str = perm.toString().toLowerCase();

                identical = path_str.equals(perm_str);
                startsWith = path_str.startsWith(perm_str);
                isLonger = path.getNameCount() > perm.getPath().getNameCount();

            }
            if (identical || startsWith) {
                break;
            }
        }
        if (identical) {
            // DISCARDED is unavailable to Category and AbstractNote.
            // Return DEFAULT. DISCARDED is available only to DiscardedItem
            return new MetaPath(Paths.get(RestrictedLists.getMetaPathDefaultDirectoryName()));
        }
        if (startsWith && isLonger) {
            // eliminate permanent dir name from MetaPath.
            return new MetaPath(path.subpath(perm.getPath().getNameCount(), path.getNameCount()));
        }
        // does not have permanent dir name after MetaPath root.
        return mp;
    }

    protected static ViewPath filterViewPathElements(ViewPath vp) {
        return new ViewPath(filterMetaPathElements(new MetaPath(vp)));
    }

    private static MetaPath filterMetaPathElements_withNoteName(MetaPath mp) {
        MetaPath name = mp.getFileName();
        MetaPath newdir = filterMetaPathElements(mp.getParent());
        return newdir.resolve(name);
    }

    private static ViewPath filterViewPathElements_withNoteName(ViewPath vp) {
        ViewPath name = vp.getFileName();
        ViewPath newdir = filterViewPathElements(vp.getParent());
        return newdir.resolve(name);
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
