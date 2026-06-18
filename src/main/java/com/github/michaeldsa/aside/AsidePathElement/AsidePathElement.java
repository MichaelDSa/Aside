package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.AsidePath;
import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.Initialization.RootPaths;
import com.github.michaeldsa.aside.Validation.ValidateAsidePath;
import com.github.michaeldsa.aside.Validation.ValidateString;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class AsidePathElement {
    protected MetaPath metaPath;
    protected ViewPath viewPath;
    protected List<AsidePathElement> nest;

    /*anti_redundant_set stores this sessions's filenames
    to prevent generation of filenames already in use */
    private static final HashSet<String> anti_redundant_set = new HashSet<>();

    // getters:
    public MetaPath getMetaPath() {return this.metaPath;}
    public ViewPath getViewPath() {return this.viewPath;}

    // nest getter
    public List<AsidePathElement> getNest() {
        return this.nest;
    }

    // nest convenience methods:
    public void nestClear() {
        this.nest.clear();
    }
    public void nestAdd(AsidePathElement element) {
        this.nest.add(element);
    }

    // abstract methods:
    // most of these methods are for subclasses that choose to include a Category stepParent field.
    public abstract AbstractCategory getParentCategory();
    public abstract AbstractCategory getStepParentCategory();
    public abstract void setStepParentCategory(AbstractCategory newStepParent);
    public abstract boolean hasStepParent();

    // static methods:
    protected static boolean endsWithFileName(AsidePath ap) {
        return ValidateAsidePath.NOTE_NAME.test(ap)
                || ValidateAsidePath.BIBLIOGRAPHY_NAME.test(ap)
                || ValidateAsidePath.DISCARDED_NOTE_NAME.test(ap)
                || ValidateAsidePath.DISCARDED_BIBLIOGRAPHY_NAME.test(ap)
                || ap.getPath().getFileName().endsWith(".txt");}
    protected static boolean endsWithNoteName(AsidePath ap) {return ValidateAsidePath.NOTE_NAME.test(ap);}
    protected static boolean endsWithCategoryName(AsidePath ap) {return ValidateAsidePath.CATEGORY_NAME.test(ap);}

    protected static MetaPath filterMetaPathElements(MetaPath mp) {
        /* For use with Category or Note classes. Converts paths in adherence
        to rules of the sandbox. Subcategories may not be created in any member
        of RestrictedLists.permanentDirectories. Notes may not be persisted to
        asidepath root, or members of RestriectedLists.permanentDirectories. */
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
            // DISCARDED & BIBLIOGRAPHY is unavailable to Category and Note.
            // Return DEFAULT regardless.
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

    /* static methods for unique filename generation. Also a
    dependency for other filename types, such as BIBLIOGRAPHY and
    DISCARDED members. */

    // generate a MetaPath that ends with the unique file name formatted for notes.
    public static MetaPath generateUniqueFileName(MetaPath parent) {
        // generate date stamp MetaPath file, formatted as `yyMMdd_HHmm_ss`, and ending with `.txt`.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd_HHmm_ss");
        MetaPath name = getTimeStampedFileName(formatter);

        // resolve the filename to the parent
        name = parent.resolve(name);

        // edge case: resolve naming conflict
        if (Files.exists(name.getPath()) || !fileNameIsUnique(name)) {
            for (int i = 0; i < 60; i++) {
                try {
                    Thread.sleep(1000);
                    name = getTimeStampedFileName(formatter);
                    name = parent.resolve(name);

                    if (Files.notExists(name.getPath()) && fileNameIsUnique(name)) {
                        break;
                    } else {
                        System.out.print(".");
                        name = null;
                    }
                } catch (InterruptedException ex) {
                    System.out.printf("Thread.sleep() exception: %s%n", ex);
                }
            }
        }
        return Objects.requireNonNull(name, "Create.newNoteName(): failed to generate unique file name");
    }
    private static MetaPath getTimeStampedFileName(DateTimeFormatter formatter) {
        LocalDateTime now = LocalDateTime.now();
        return new MetaPath(Paths.get("." + now.format(formatter) + ".txt"));
    }

    private static boolean fileNameIsUnique(MetaPath fileName) {
        // tests whether file name is unique amongst all files, including in-memory Note filenames not yet written
        if (!anti_redundant_set.add(fileName.getPath().getFileName().toString())) {
            return false;
        }

        String fileName_str = fileName.getPath().getFileName().toString();

        // test whole sandbox for filnames identical to fileName.
        try (Stream<Path> stream = Files.walk(RootPaths.INSTANCE.getMetapath())) {
            return stream.parallel().noneMatch(
                    path -> {
                        String name = path.getFileName().toString();
                        if (ValidateString.BIBLIOGRAPHY_NAME.test(name) || ValidateString.DISCARDED_NOTE_NAME.test(name)) {
                            name = "." + name.substring(2);
                        } else if (ValidateString.DISCARDED_BIBLIOGRAPHY_NAME.test(name)) {
                            name = "." + name.substring(3);
                        }
                        return name.equals(fileName_str);
                    }
            );
        } catch (IOException e) {
            System.out.println("AbstractNote.fileNameIsUnique(): IOException.\n" + fileName);
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AsidePathElement that)) return false;
        return Objects.equals(metaPath, that.metaPath) && Objects.equals(viewPath, that.viewPath) && Objects.equals(nest, that.nest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(metaPath, viewPath, nest);
    }

    @Override
    public String toString() {
        return "AsidePathElement{" +
                "metaPath=" + metaPath +
                ", viewPath=" + viewPath +
                ", nest=" + nest +
                '}';
    }
}
