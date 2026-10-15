package com.github.michaeldsa.aside.Validation;

import com.github.michaeldsa.aside.AsidePathElement.RestrictedLists;
import com.github.michaeldsa.aside.Initialization.RootPaths;

import java.nio.file.Path;
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
        RootPaths rp = RootPaths.INSTANCE;
        String mpr = rp.getMetapath().toString();
        String vpr = rp.getViewpath().toString();
        boolean pass = false;
        String s = "";
        Path path = p;

        // if path starts with MetaPath or ViewPath root, remove root from path:
        if (p.startsWith(mpr)){
            path = path.subpath(rp.getMetapath().getNameCount() - 1, p.getNameCount());

            /* also test each element to make sure path conforms to
             metapath naming. each element should begin with a dot. */
            for (Path e : path) {
                if (!e.toString().startsWith(".")) {
                    return false;
                }
            }
        } else if (p.startsWith(vpr)){
            path = path.subpath(rp.getViewpath().getNameCount() - 1, p.getNameCount());

            // test for viewpath naming. no leading dots:
            for (Path e : path) {
                if (e.toString().startsWith(".")) {
                    return false;
                }
            }
        } else { // if submitted path does nto start with mpr or vpr, make sure leading char is consistent.
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
            // if 1 or more elements, test all elements except last
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
        /*
        first test with ALL_ELEMENTS
        then, test last element with CATEGORY_NAME
         */
        if (ALL_ELEMENTS.test(p)) {
            boolean contains_restricted_element = false;
            for (Path e : p) {
                if(RestrictedLists.getPermanentDirectories().contains(e.toString())) {
                    contains_restricted_element = true;
                    break;
                }
            }
            if (contains_restricted_element) {
                /*
                If p contains DEFAULT:
                1) DEFAULT must be the first element
                2) There may only be two elements, the second being a NOTE_NAME
                 */
                // check if first element is DEFAULT
                if (RestrictedLists.getMetaPathDefaultDirectoryName().equals(p.getName(0).toString())
                    || RestrictedLists.getViewPathDefaultDirectoryName().equals(p.getName(0).toString())) {

                    // check if 1) length is 2. 2) 2nd element is a NOTE_NAME.
                    if (p.getNameCount() == 2 && ValidateString.NOTE_NAME.test(p.getName(1).toString())) {
                        contains_restricted_element = false;
                    }
                }
            }

            return !contains_restricted_element && ValidateString.CATEGORY_NAME.test(p.getFileName().toString());
        }
        return false;
    }),
    NOTE_NAME(p -> {
        /*
        first test with ALL_ELEMENTS
        then, test last element with NOTE_NAME
         */
        if (ALL_ELEMENTS.test(p)) {
            return ValidateString.NOTE_NAME.test(p.getFileName().toString());
        }
        return false;
    }),
    BIBLIOGRAPHY_NAME(p -> {
        if (ALL_ELEMENTS.test(p)) {
            return ValidateString.BIBLIOGRAPHY_NAME.test(p.getFileName().toString());
        }
        return false;
    }),
    DISCARDED_NOTE_NAME(p -> {
        if (ALL_ELEMENTS.test(p)) {
            return ValidateString.DISCARDED_NOTE_NAME.test(p.getFileName().toString());
        }
        return false;
    }),
    DISCARDED_BIBLIOGRAPHY_NAME(p -> {
        if (ALL_ELEMENTS.test(p)) {
            return ValidateString.DISCARDED_BIBLIOGRAPHY_NAME.test(p.getFileName().toString());
        }
        return false;
    });


    private final Predicate<Path> predicate;

    ValidatePath(Predicate<Path> predicate) {
        this.predicate = predicate;
    }

    public boolean test(Path path) {
        return predicate.test(path);
    }

}
