package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.RootPaths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public abstract class AbstractNote extends AsidePathElement {
    // optional:
    // Category stepParent;

    protected String title;
    protected String content;
    protected HashSet<String> to;
    protected HashSet<String> from;
    protected HashSet<String> tags;

    // getters:
    public abstract String getTitle();
    public abstract String getContent();
    public abstract HashSet<String> getTo();
    public abstract HashSet<String> getFrom();
    public abstract HashSet<String> getTags();
    public abstract ImmutableNote getPreviousState();


    // static methods:

    /* anti-redundant-name set:
    This anti-redundant-name set is meant to store names created in the current
    session. When generateNewNoteName() creates notes in quick succession,
    it checks the set for names already created. */
    private static final Set<String> anti_redundant_set = new HashSet<>();

    // generate a MetaPath that ends with the unique file name formatted for notes.
    public static MetaPath generateNewNoteName(MetaPath parent) {
        // generate date stamp MetaPath ending with `.txt`.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd_HHmm_ss");
        MetaPath name = getTimeStampedFileName(formatter);

        // this note name will be added to anti_redundant_set
        String note_name = name.getPath().getFileName().toString();

        // resolve the filename to the parent
        name = parent.resolve(name);

        // edge case: resolve naming conflict
        if (Files.exists(name.getPath()) || !fileNameIsUnique(name)) {
            for (int i = 0; i < 60; i++) {
                try {
                    Thread.sleep(1000);
                    name = getTimeStampedFileName(formatter);
                    note_name = name.getPath().getFileName().toString();
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
        // tests whether file name is unique amongst all files, including files not yet written
        if (!anti_redundant_set.add(fileName.getPath().getFileName().toString())) {
            return false;
        }

        String fileName_str = fileName.getPath().getFileName().toString();

        try (Stream<Path> stream = Files.walk(RootPaths.INSTANCE.getMetapath())) {
            return stream.parallel().noneMatch(
                    path ->
                            path.getFileName().toString().equals(fileName_str)
            );
        } catch (IOException e) {
            System.out.println("AbstractNote.fileNameIsUnique(): IOException.\n" + fileName);
            return false;
        }
    }

    @Override
    public String toString() {
        return "AbstractNote{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", to=" + to +
                ", from=" + from +
                ", tags=" + tags +
                ", metaPath=" + metaPath +
                ", viewPath=" + viewPath +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AbstractNote that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(title, that.title) && Objects.equals(content, that.content) && Objects.equals(to, that.to) && Objects.equals(from, that.from) && Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, content, to, from, tags);
    }
}
