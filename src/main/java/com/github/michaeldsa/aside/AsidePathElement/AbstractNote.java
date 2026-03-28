package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.MetaPath;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    // generate a MetaPath that ends with the unique file name formatted for notes.
    public static MetaPath generateNewNoteName(MetaPath parent) {
        // generate date stamp String starting with `.` and ending with `.txt`.
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd_HHmm_ss");
        String note_name = "." + now.format(formatter) + ".txt";

        // Make MetaPath of the note name resolved to parent
        MetaPath name = new MetaPath(Paths.get(note_name));
        name = parent.resolve(name);

        // edge case: resolve naming conflict
        if (Files.exists(name.getPath())) {
            for (int i = 0; i < 60; i++) {
                try {
                    Thread.sleep(1000);
                    name = generateNewNoteName(parent);

                    if (Files.notExists(name.getPath())) {
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
