package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ImmutableNote extends Note {
    // field shadowing necessary to make fields final:
    private final MetaPath metaPath;
    private final ViewPath viewPath;
    private final String title;
    private final String content;
    private final Set<String> to;
    private final Set<String> from;
    private final Set<String> tags;

    // uses MutableNote as a builder
    ImmutableNote(MutableNote builder){
        this.metaPath = builder.getMetaPath();
        this.viewPath = builder.getViewPath();
        this.title = builder.getTitle();
        this.content = builder.getContent();
        // immutable sets:
        this.to = Collections.unmodifiableSet(builder.getTo());
        this.from = Collections.unmodifiableSet(builder.getFrom());
        this.tags = Collections.unmodifiableSet(builder.getTags());
    }

    // AsidePathElement abstract methods:
    public MetaPath getMetaPath(){
        return metaPath;
    }
    public ViewPath getViewPath() {
        return viewPath;
    }

    // Note abstract methods:
    public Category getCategory(){
        return new Category(metaPath);
    }
    public String getTitle(){
        return title;
    }
    public String getContent(){
        return content;
    }
    // getters for to, from and tags return null. Use the
    // Set<String> getters instead, for immutable structures.
    public HashSet<String> getTo(){
        return null;
    }
    public HashSet<String> getFrom(){
        return null;
    }
    public HashSet<String> getTags(){
        return null;
    }
    public Set<String> getToSet() { return to;}
    public Set<String> getFromSet() { return from;}
    public Set<String> getTagsSet() { return tags;}
    public boolean to_contains(String value) {
        return to.contains(value);
    }
    public boolean from_contains(String value) {
        return from.contains(value);
    }
    public boolean tags_contains(String value) {
        return tags.contains(value);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ImmutableNote that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(metaPath, that.metaPath) && Objects.equals(viewPath, that.viewPath) && Objects.equals(title, that.title) && Objects.equals(content, that.content) && Objects.equals(to, that.to) && Objects.equals(from, that.from) && Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), metaPath, viewPath, title, content, to, from, tags);
    }

    @Override
    public String toString() {
        return "ImmutableNote{" +
                "metaPath=" + metaPath +
                ", viewPath=" + viewPath +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", to=" + to +
                ", from=" + from +
                ", tags=" + tags +
                '}';
    }
}
