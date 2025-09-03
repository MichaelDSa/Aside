package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;

import java.util.HashSet;

public class Note extends AsidePathElement {
    private Category parent;

    private String title;
    private HashSet<String> to;
    private HashSet<String> from;
    private HashSet<String> tags;
    private String content;

    public Note(Category parent) {
        this.parent = parent;

        title = "";
        to = new HashSet<>();
        from = new HashSet<>();
        tags = new HashSet<>();
        content = "";
    }

    public Note(MetaPath metaPath) {
        parent = new Category(metaPath);
        title = "";
        to = new HashSet<>();
        from = new HashSet<>();
        tags = new HashSet<>();
        content = "";
        // now use a new note name to set this.metaPath & this.viewPath

    }
    public Note(ViewPath viewPath) {
        parent = new Category(viewPath);
        title = "";
        to = new HashSet<>();
        from = new HashSet<>();
        tags = new HashSet<>();
        content = "";
        // now use a new note name to set this.metaPath & this.viewPath
    }

    public Note(Note note) {
        this.parent = note.parent;
        this.metaPath = note.metaPath;// this is the MetaPath note name
        this.viewPath = note.viewPath;// this is the ViewPath note name
        title = "";
        to = new HashSet<>();
        from = new HashSet<>();
        tags = new HashSet<>();
        content = "";
    }


    // methods inherited from super:

    // public MetaPath getMetaPath();
    // public ViewPath getViewPath();
    // public void setAsidePaths(MetaPath metaPath) // sets both this.metaPath and this.viewPath at once
    // public void setAsidePaths(ViewPath viewPath) // sets both this.metaPath and this.viewPath at once


    // Getters and Setters for parent:
    public Category getCategory() {
        return parent;
    }
    public MetaPath getParentMetaPath() {
        return parent.getMetaPath();
    }
    public ViewPath getParentViewPath() {
        return parent.getViewPath();
    }
    public void setCategory(Category newCategory) {
        this.parent = newCategory;
    }
    public void setParentMetaPath(MetaPath newParentMetaPath) {
        this.parent.setAsidePaths(newParentMetaPath);
    }
    public void setParentViewPath(ViewPath newParentViewPath) {
        this.parent.setAsidePaths(newParentViewPath);
    }


    // getters and setters for metadata:
    public HashSet<String> getTo() {
        return to;
    }
    public HashSet<String> getFrom() {
        return from;
    }
    public HashSet<String> getTags() {
        return tags;
    }
    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
    public void setTo(HashSet<String> to) {
        this.to = to;
    }
    public void setFrom(HashSet<String> from) {
        this.from = from;
    }
    public void setTags(HashSet<String> tags) {
        this.tags = tags;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setContent(String content) {
        this.content = content;
    }

    // Incremental Set mutation for to, from, tags:
    public void addTo(String str) {
        to.add(str);
    }
    public void addFrom(String str) {
        from.add(str);
    }
    public void addTag(String str) {
        tags.add(str);
    }
    public void removeTo(String str) {
        to.remove(str);
    }
    public void removeFrom(String str) {
        from.remove(str);
    }
    public void removeTag(String str) {
        tags.remove(str);
    }

    // Content mutation:
    // add content mutation methods here

    // Title mutation:
    // add content mutation methods here

    // Other ideas:
    // Should I set up and configure a Properties object?
    //      That way I can maybe merge merge or replace this Properties with the other...
    // Should I have HashSet<String> instance vars to save elements to remove from the .meta/ properties object?


}
