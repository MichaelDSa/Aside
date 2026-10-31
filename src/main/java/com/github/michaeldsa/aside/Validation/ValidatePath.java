package com.github.michaeldsa.aside.Validation;

import com.github.michaeldsa.aside.AsidePathElement.RestrictedLists;
import com.github.michaeldsa.aside.Initialization.RootPaths;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;

/*
 test whether the client's (user's) submission is valid in case they
 submit a file/dir name consisting of multiple elements, like so:
   `new_category1/new_category2/new_category3/note_name`
  NOTE: users may submit paths that conform to either MetaPath, or
        ViewPath. It's up to the client whether they want to
        enforce ViewPath naming on the user.
 */
public enum ValidatePath implements Predicate<Path> {
    ALL_ELEMENTS(p -> {
        /*
        - Tests each element of the full path for
          correct naming, whether the path elements
          end with a note name or a category name.
        - Path must consist of:
          1. ViewPath root or MetaPath root. However, the client may omit this.
          2. category names; not failing if name is member of RestrictedLists.permanentDirectories
          3. A Note name may be present as the final element.
         */

        // return false if arg has no elements after AsidePathRoot.
        if (p.equals(RootPaths.INSTANCE.getMetapath()) || p.equals(RootPaths.INSTANCE.getViewpath())) {
            return false;
        }

        RootPaths rp = RootPaths.INSTANCE;
        String mpr = rp.getMetapath().toString();
        String vpr = rp.getViewpath().toString();
        boolean pass = false;
        String s = "";
        Path path = p;

        // if path starts with MetaPath or ViewPath root, remove root from path:
        if (p.startsWith(mpr)){
            path = path.subpath(rp.getMetapath().getNameCount(), p.getNameCount());

            /* also test each element to make sure path conforms to
             metapath naming. each element should begin with a dot. */
            for (Path e : path) {
                if (!e.toString().startsWith(".")) {
                    return false;
                }
            }
        } else if (p.startsWith(vpr)){
            path = path.subpath(rp.getViewpath().getNameCount(), p.getNameCount());

            // test for viewpath naming. no leading dots:
            for (Path e : path) {
                if (e.toString().startsWith(".")) {
                    return false;
                }
            }
        } else { // if submitted path does not start with mpr or vpr, make sure leading char is consistent.
            if (path.getName(0).toString().startsWith(".")) {
                for (Path e : path) {
                    if (!e.toString().startsWith(".")) {
                        return false;
                    }
                }
            } else {
                for (Path e : path ) {
                    if (e.toString().startsWith(".")) {
                        return false;
                    }
                }
            }
        }

        // if only one element, pass for now.
        if (path.getNameCount() == 1){
            pass = true;
        } else {
            // if more than one element, test all elements except last
            for (int i = 0; i < path.getNameCount() - 1; i++) {
                s = path.getName(i).toString();
                pass = ValidateString.CATEGORY_NAME.test(s);
                if (!pass) {
                    break;
                }
            }
        }
        // now test last (or only) element
        if (pass) {
            boolean ends_with_note_name = ValidateString.NOTE_NAME.test(path.getFileName().toString());
            boolean ends_with_category_name = ValidateString.CATEGORY_NAME.test(path.getFileName().toString());
            boolean ends_with_bibliography_name = ValidateString.BIBLIOGRAPHY_NAME.test(path.getFileName().toString());
            boolean ends_with_discarded_note_name = ValidateString.DISCARDED_NOTE_NAME.test(path.getFileName().toString());
            boolean ends_with_discarded_bibliography_name = ValidateString.DISCARDED_BIBLIOGRAPHY_NAME.test(path.getFileName().toString());
            pass = ends_with_note_name
                    || ends_with_category_name
                    || ends_with_bibliography_name
                    || ends_with_discarded_note_name
                    || ends_with_discarded_bibliography_name;
        }
        return pass;

    }),
    CATEGORY_NAME(p -> {

        // pass if p is AsidePathRoot
        if (p.equals(RootPaths.INSTANCE.getMetapath()) || p.equals(RootPaths.INSTANCE.getViewpath())) {
            return true;
        }

        // pass if p is AsidePathRoot + DEFAULT
        Path m_default_dir  = RootPaths.INSTANCE.getMetapath().resolve(Paths.get(RestrictedLists.getMetaPathDefaultDirectoryName()));
        Path v_default_dir  = RootPaths.INSTANCE.getViewpath().resolve(Paths.get(RestrictedLists.getViewPathDefaultDirectoryName()));
        if (p.equals(m_default_dir) || p.equals(v_default_dir)) {
            return true;
        }

        // must pass ALL_ELEMENTS
        if (!ALL_ELEMENTS.test(p)) {
            return false;
        }

        // fail if p starts with a permanent directory other than DEFAULT
        String m_default = RestrictedLists.getMetaPathDefaultDirectoryName();
        String v_default = RestrictedLists.getViewPathDefaultDirectoryName();
        for(String s : RestrictedLists.getPermanentDirectories()) {
            Path perm = s.startsWith(".")
                    ? RootPaths.INSTANCE.getMetapath().resolve(Paths.get(s))
                    : RootPaths.INSTANCE.getViewpath().resolve(Paths.get(s));
            if (!(s.equals(m_default) || s.equals(v_default)) && p.startsWith(perm)) {
                return false;
            }
        }

        // fail if p contains a permanent directory name, other than starting with DEFAULT
        Path path = p.startsWith(m_default_dir) || p.startsWith(v_default_dir)
                ? p.subpath(m_default_dir.getNameCount(), p.getNameCount())
                : p;
        for (Path e : path) {
            if (RestrictedLists.getPermanentDirectories().contains(e.toString())) {
                return false;
            }
        }

        return ValidateString.CATEGORY_NAME.test(p.getFileName().toString());
    }),
    NOTE_NAME(p -> {
        Path sans_filename = p.getParent();
        String filename = p.getFileName().toString();
        return CATEGORY_NAME.test(sans_filename) && ValidateString.NOTE_NAME.test(filename);
    }),
    BIBLIOGRAPHY_CATEGORY_NAME(p -> {
        // fail if equal to AsidePathRoot
        if (p.equals(RootPaths.INSTANCE.getMetapath()) || p.equals(RootPaths.INSTANCE.getViewpath())) {
            return false;
        }

        // pass if equal to AsidePathRoot + BIBLIOGRAPHY
        Path m_bibRoot = RootPaths.INSTANCE.getMetapath().resolve(Paths.get(RestrictedLists.getMetaPathBibliographyDirectoryName()));
        Path v_bibRoot = RootPaths.INSTANCE.getViewpath().resolve(Paths.get(RestrictedLists.getViewPathBibliographyDirectoryName()));
        if (p.equals(m_bibRoot) || p.equals(v_bibRoot)) {
            return true;
        }

        // must pass ALL_ELEMENTS
        if (!ALL_ELEMENTS.test(p)) {
            return false;
        }

        // fail if path starts with a permanent directory other than BIBLIOGRAPHY
        String m_bibdir = RestrictedLists.getMetaPathBibliographyDirectoryName();
        String v_bibdir = RestrictedLists.getViewPathBibliographyDirectoryName();
        for (String s : RestrictedLists.getPermanentDirectories()) {
            Path perm = s.startsWith(".")
                    ? RootPaths.INSTANCE.getMetapath().resolve(Paths.get(s))
                    : RootPaths.INSTANCE.getViewpath().resolve(Paths.get(s));
            if (!(s.equals(m_bibdir) || s.equals(v_bibdir)) && p.startsWith(perm)) {
                return false;
            }
        }

        // fail if path contains a permanent directory name, other than starting with BIBLIOGRAPHY
        Path path = p.startsWith(m_bibRoot) || p.startsWith(v_bibRoot) // if true, p is assumed to be longer than comparison, since it cannot be AsidePathRoot + BIBLIOGRAPHY.
                ? p.subpath(m_bibRoot.getNameCount(), p.getNameCount())
                : p;
        for (Path e : path ) {
            if (RestrictedLists.getPermanentDirectories().contains(e.toString())) {
                return false;
            }
        }

        // fail if last element does not conform to CATEGORY name rules:
        return ValidateString.CATEGORY_NAME.test(p.getFileName().toString());
    }),
    BIBLIOGRAPHY_NAME(p -> {
        // fail if equal to AsidePathRoot
        boolean is_AsidePathRoot = p.equals(RootPaths.INSTANCE.getMetapath()) || p.equals(RootPaths.INSTANCE.getViewpath());
        // test parent and filename
        Path sans_filename = p.getParent();
        String filename = p.getFileName().toString();
        return !is_AsidePathRoot
                && BIBLIOGRAPHY_CATEGORY_NAME.test(sans_filename)
                && ValidateString.BIBLIOGRAPHY_NAME.test(filename);
    }),
    DISCARDED_CATEGORY_NAME(p -> {
        // there can only be one discarded category. No children allowed.
        Path m_discarded = RootPaths.INSTANCE.getMetapath().resolve(Paths.get(RestrictedLists.getMetaPathDiscardedDirectoryName()));
        Path v_discarded = RootPaths.INSTANCE.getViewpath().resolve(Paths.get(RestrictedLists.getViewPathDiscardedDirectoryName()));
        return p.equals(m_discarded) || p.equals(v_discarded);
    }),
    DISCARDED_NOTE_NAME(p -> {
        Path sans_filename = p.getParent();
        String filename = p.getFileName().toString();
        return DISCARDED_CATEGORY_NAME.test(sans_filename) && ValidateString.DISCARDED_NOTE_NAME.test(filename);
    }),
    DISCARDED_BIBLIOGRAPHY_NAME(p -> {
        Path sans_filename = p.getParent();
        String filename = p.getFileName().toString();
        return DISCARDED_CATEGORY_NAME.test(sans_filename) && ValidateString.DISCARDED_BIBLIOGRAPHY_NAME.test(filename);
    });


    private final Predicate<Path> predicate;

    ValidatePath(Predicate<Path> predicate) {
        this.predicate = predicate;
    }

    public boolean test(Path path) {
        return predicate.test(path);
    }

}
