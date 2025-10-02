package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.AsidePath;
import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.Validation.ValidateAsidePath;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Objects;

public class MutableNote extends Note {
    private Category parent;
    private final ImmutableNote previousState;


    public MutableNote(Category category){
        parent = category;
        this.metaPath = newNoteName(parent.getMetaPath());
        this.viewPath = new ViewPath(this.metaPath);
        this.previousState = null;
    }

    public MutableNote(MetaPath metaPath){
        // assign metaPath:
        if(endsWithCategoryName(metaPath)){
            this.metaPath = newNoteName(metaPath);
        } else if (endsWithNoteName(metaPath)){
            this.metaPath = metaPath;
        }
        // assign viewPath, parent & previousState:
        this.viewPath = new ViewPath(this.metaPath);
        parent = new Category(metaPath);
        this.previousState = null;
    }

    public MutableNote(ViewPath viewPath){
        // assign viewPath:
        if(endsWithCategoryName(viewPath)){
            this.viewPath = viewPath;
        } else if (endsWithNoteName(viewPath)){
            this.viewPath = newNoteName(viewPath);
        }
        // assign metaPath, parent & previousState:
        this.metaPath = newNoteName(parent.getMetaPath());
        parent = new Category(viewPath);
        this.previousState = null;
    }

    // assumes note's metaPath ends with note name.
    public MutableNote(MutableNote note) {
        this.parent = note.parent;
        this.metaPath = note.metaPath;// this is the MetaPath note name
        this.viewPath = note.viewPath;// this is the ViewPath note name
        this.title = note.getTitle();
        this.to = note.getTo();
        this.from = note.getFrom();
        this.tags = note.getTags();
        this.content = note.getContent();
        this.previousState = new ImmutableNote(note);
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
//    public void setParentMetaPath(MetaPath newParentMetaPath) {
//        this.parent.setAsidePaths(newParentMetaPath);
//    }
//    public void setParentViewPath(ViewPath newParentViewPath) {
//        this.parent.setAsidePaths(newParentViewPath);
//    }


    // getters for metadata
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

    // method chaining setters for metadata
    public MutableNote setTo(HashSet<String> to) {
        this.to = to;
        return this;
    }
    public MutableNote setFrom(HashSet<String> from) {
        this.from = from;
        return this;
    }
    public MutableNote setTags(HashSet<String> tags) {
        this.tags = tags;
        return this;
    }
    public MutableNote setTitle(String title) {
        this.title = title;
        return this;
    }
    public MutableNote setContent(String content) {
        this.content = content;
        return this;
    }

    // Method chaining incremental Set mutation for to, from, tags:
    public MutableNote addTo(String str) {
        to.add(str);
        return this;
    }
    public MutableNote addFrom(String str) {
        from.add(str);
        return this;
    }
    public MutableNote addTag(String str) {
        tags.add(str);
        return this;
    }
    public MutableNote removeTo(String str) {
        to.remove(str);
        return this;
    }
    public MutableNote removeFrom(String str) {
        from.remove(str);
        return this;
    }
    public MutableNote removeTag(String str) {
        tags.remove(str);
        return this;
    }

    // prevState methods:
    public boolean hasPreviousState(){
        return previousState != null;
    }

    public ImmutableNote getPreviousState(){
        return previousState;
    }

    public ImmutableNote getAsImmutableNote(){
        return new ImmutableNote(this);
    }

    // tests for HashSets:
    public boolean to_contains(String value){
        return to.contains(value);
    }
    public boolean from_contains(String value){
        return from.contains(value);
    }
    public boolean tags_contains(String value){
        return tags.contains(value);
    }


    // OTHER UTILITIES:
    // tests & validation:
    private boolean endsWithCategoryName(AsidePath asidePath){
        return ValidateAsidePath.CATEGORY_NAME.test(asidePath);
    }
    private boolean endsWithNoteName(AsidePath asidePath){
        return ValidateAsidePath.NOTE_NAME.test(asidePath);
    }

    // NOTE_NAME GENERATOR: generate a MetaPath that ends with the unique file name formatted for notes.
    private MetaPath newNoteName(MetaPath parent) {
        // generate date stamp String starting with `.` and ending with `.txt`.
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd_hhmm_ss");
        String note_name = "." + now.format(formatter) + ".txt";

        // Make MetaPath of the note name resolved to parent
        MetaPath name = new MetaPath(Paths.get(note_name));
        name = parent.resolve(name);

        // edge case: resolve naming conflict
        if (Files.exists(name.getPath())) {
            for (int i = 0; i < 60; i++) {
                try {
                    Thread.sleep(1000);
                    name = newNoteName(parent);

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

    private ViewPath newNoteName(ViewPath parent) {
        MetaPath metaPathParent = new MetaPath(parent);
        MetaPath noteName = newNoteName(metaPathParent);
        return new ViewPath(noteName);
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
