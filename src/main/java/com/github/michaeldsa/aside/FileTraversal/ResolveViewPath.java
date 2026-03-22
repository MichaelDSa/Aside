package com.github.michaeldsa.aside.FileTraversal;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.AsidePathElement.DiscardedElement;
import com.github.michaeldsa.aside.AsidePathElement.RestrictedLists;
import com.github.michaeldsa.aside.Pretty;
import com.github.michaeldsa.aside.RootPaths;
import com.github.michaeldsa.aside.Validation.ValidatePath;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.Properties;
import java.util.Set;

/*
This class traverses the MetaPath, and recursively copies
all qualifying directories and .txt files to the ViewPath
counterpart.
 */
public class ResolveViewPath extends Traverser{
    private int width; // set the width of the ViewPath .txt file

    public ResolveViewPath() {
        this.startingPoint = new MetaPath().getPath(); // root metapath
        this.options = Collections.emptySet();
        this.depth = Integer.MAX_VALUE;
        this.width = 80;
    }
    public ResolveViewPath(MetaPath startingPoint) {
        this.startingPoint = startingPoint.getPath(); // root metapath
        this.options = Collections.emptySet();
        this.depth = Integer.MAX_VALUE;
        this.width = 80;
    }
    public ResolveViewPath(ViewPath startingPoint) {
        this.startingPoint = new MetaPath(startingPoint).getPath();
        this.options = Collections.emptySet();
        this.depth = Integer.MAX_VALUE;
        this.width = 80;
    }
    @Override
    public ResolveViewPath setStartingPoint(MetaPath startingPoint) {
        this.startingPoint = startingPoint.getPath();
        return this;
    }
    @Override
    public ResolveViewPath setStartingPoint(ViewPath viewPath) {
        this.startingPoint = new MetaPath(viewPath).getPath();
        return this;
    }
    @Override
    public ResolveViewPath setFileVisitOptions(Set<FileVisitOption> options) {
        this.options = options;
        return this;
    }
    @Override
    public ResolveViewPath setDepth(int depth) {
        this.depth = depth;
        return this;
    }
    public ResolveViewPath setWidth(int width) {
        this.width = width;
        return this;
    }

    public FileVisitResult _preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        // Does dir have a valid category name?
        if (ValidatePath.CATEGORY_NAME.test(dir)) {

            // ...if so, get its ViewPath counterpart
            Path vpath = new ViewPath(new MetaPath(dir)).getPath();
            if (Files.notExists(vpath)) {
                Files.createDirectory(vpath);
            }
        }


        return FileVisitResult.CONTINUE;
    }

    public FileVisitResult _visitFile(Path file, BasicFileAttributes attrs) throws IOException {

        // early dismissal:
        // valid files only. Text files in root path are not recognized
        // Files that are descendants of DISCARDED are not recognized
        Path discarded = RestrictedLists.getDiscardedElementDirectory().getMetaPath().getPath();
        if (
                file.startsWith(discarded) ||
                file.getParent().equals(RootPaths.INSTANCE.getMetapath()) ||
                !Files.isRegularFile(file) ||
                !ValidatePath.NOTE_NAME.test(file)
        ) {
            return FileVisitResult.CONTINUE;
        }
        
        // feed the file to a properties object
        Properties properties = new Properties();
        try (InputStream is = Files.newInputStream(file)) {
            properties.load(is);
        }

        // This Traverser only traverses MetaPath paths, so we
        // need a ViewPath path which is derived from file wrapped
        // in a MetaPath. ViewPath will convert MetaPath paths.
        Path vpath = new ViewPath(new MetaPath(file)).getPath();

        // format content and title of the ViewPath file

        String title = properties.getProperty("title");
        if (title == null) {
            title = "";
        } else {
            title = Pretty.format(title, width);
        }

        String content = properties.getProperty("content");
        if (content == null) {
            content = "";
        } else {
            content = Pretty.format(content, width);
        }

        String file_contents = "";
        if (content.isBlank() && title.isBlank()) {
            file_contents = "";
        }

        if (!title.isBlank()) {
            file_contents = Pretty.format(title, width) + "\n";
        }

        if (!content.isBlank()) {
            file_contents += Pretty.format(content, width);
        }

        // write the content data to the new ViewPath file.
        try (OutputStream os = Files.newOutputStream(vpath)) {
            os.write(file_contents.getBytes());
        }
        return FileVisitResult.CONTINUE;
    }

}
