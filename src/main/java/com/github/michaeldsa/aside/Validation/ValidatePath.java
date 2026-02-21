package com.github.michaeldsa.aside.Validation;

import com.github.michaeldsa.aside.RootPaths;

import java.nio.file.Path;
import java.util.function.Predicate;

// test whether the client's submission is valid in
// case they submit a file/dir name consisting of
// multiple elements, like so:
//   `new_category1/new_category2/new_category3/note_name`
public enum ValidatePath implements Predicate<Path> {
    CATEGORY_OR_NOTE_NAME_SUBMISSION (p -> {
        /*
        - Tests each element of the full path for
          correct naming, whether the path elements
          end with a note name or a category name.
        - Path must consist of:
          1. ViewPath root or MetaPath root. However, the client may omit this.
          2. category names
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
            path = path.subpath(rp.getMetapath().getNameCount(), p.getNameCount());
        }
        else if (p.startsWith(vpr)){
            path = path.subpath(rp.getViewpath().getNameCount(), p.getNameCount());
        }

        // test all elements except last
        for (int i = 0; i < path.getNameCount() - 1; i++) {
            s = path.getName(i).toString();
            pass = ValidateString.CATEGORY_NAME.test(s);
            if (!pass) {
                break;
            }
        }
        // now test last element
        if (pass) {
            boolean ends_with_note_name = ValidateString.NOTE_NAME.test(path.getFileName().toString());
            boolean ends_with_category_name = ValidateString.CATEGORY_NAME.test(path.getFileName().toString());
            pass = ends_with_note_name || ends_with_category_name;
        }
        return pass;

    }),
    CATEGORY_NAME_SUBMISSION (p -> {
        /*
        first test with CLIENT_PATH_NAME_SUBMISSION
        then, test last element with CATEGORY_NAME
         */
        if (CATEGORY_OR_NOTE_NAME_SUBMISSION.test(p)) {
            return ValidateString.CATEGORY_NAME.test(p.getFileName().toString());
        }
        return false;
    }),
    NOTE_NAME_SUBMISSION (p -> {
        /*
        first test with CLIENT_PATH_NAME_SUBMISSION
        then, test last element with NOTE_NAME
         */
        if (CATEGORY_OR_NOTE_NAME_SUBMISSION.test(p)) {
            return ValidateString.NOTE_NAME.test(p.getFileName().toString());
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
