package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;

import java.util.Collections;
import java.util.HashSet;

public class ImmutableNote extends Note {
    // field shadowing necessary to make fields final:
    private final MetaPath metaPath;
    private final ViewPath viewPath;
    private final String title;
    private final String content;
    private final HashSet<String> to;
    private final HashSet<String> from;
    private final HashSet<String> tags;

    // uses MutableNote as a builder
    ImmutableNote(MutableNote builder){
        this.metaPath = builder.getMetaPath();
        this.viewPath = builder.getViewPath();
        this.title = builder.getTitle();
        this.content = builder.getContent();
        // immutable sets:
        this.to = (HashSet<String>) Collections.unmodifiableSet(builder.getTo());
        this.from = (HashSet<String>) Collections.unmodifiableSet(builder.getFrom());
        this.tags = (HashSet<String>) Collections.unmodifiableSet(builder.getTags());
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
    public HashSet<String> getTo(){
        return to;
    }
    public HashSet<String> getFrom(){
        return from;
    }
    public HashSet<String> getTags(){
        return tags;
    }
    public boolean to_contains(String value) {
        return to.contains(value);
    }
    public boolean from_contains(String value) {
        return from.contains(value);
    }
    public boolean tags_contains(String value) {
        return tags.contains(value);
    }
}
