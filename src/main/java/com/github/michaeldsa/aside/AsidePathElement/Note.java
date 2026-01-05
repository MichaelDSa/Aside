package com.github.michaeldsa.aside.AsidePathElement;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class Note extends AsidePathElement {
    protected String title;
    protected Set<String> to;
    protected Set<String> from;
    protected Set<String> tags;
    protected String content;

    public abstract Category getCategory();
    public abstract String getTitle();
    public abstract HashSet<String> getTo();
    public abstract HashSet<String> getFrom();
    public abstract HashSet<String> getTags();
    public abstract String getContent();
    public abstract boolean to_contains(String value);
    public abstract boolean from_contains(String value);
    public abstract boolean tags_contains(String value);


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Note note)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(title, note.title) && Objects.equals(to, note.to) && Objects.equals(from, note.from) && Objects.equals(tags, note.tags) && Objects.equals(content, note.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, to, from, tags, content);
    }

    @Override
    public String toString() {
        return "Note{" +
                "title='" + title + '\'' +
                ", to=" + to +
                ", from=" + from +
                ", content='" + content + '\'' +
                ", tags=" + tags +
                ", metaPath=" + metaPath +
                ", viewPath=" + viewPath +
                '}';
    }
}
