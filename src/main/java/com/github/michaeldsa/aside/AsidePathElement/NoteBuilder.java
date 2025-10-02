package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;

import java.util.HashSet;
import java.util.Objects;

public class NoteBuilder extends AsidePathElement{
    private String title;
    private String content;
    private HashSet<String> to;
    private HashSet<String> from;
    private HashSet<String> tags;


    NoteBuilder(MetaPath metaPath) {
        this.metaPath = Objects.requireNonNull(metaPath);
        this.viewPath = new ViewPath(metaPath);
    }
    NoteBuilder(ViewPath viewPath) {
        this.viewPath = Objects.requireNonNull(viewPath);
        this.metaPath = new MetaPath(viewPath);
    }

    // getters:
    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
    public HashSet<String> getTo() {
        return to;
    }
    public HashSet<String> getFrom() {
        return from;
    }
    public HashSet<String> getTags() {
        return tags;
    }

    // setters:
    public NoteBuilder setTitle(String title) {
        this.title = title;
        return this;
    }
    public NoteBuilder setContent(String content) {
        this.content = content;
        return this;
    }
    public NoteBuilder setTo(HashSet<String> to) {
        this.to = to;
        return this;
    }
    public NoteBuilder setFrom(HashSet<String> from) {
        this.from = from;
        return this;
    }
    public NoteBuilder setTags(HashSet<String> tags) {
        this.tags = tags;
        return this;
    }

}
