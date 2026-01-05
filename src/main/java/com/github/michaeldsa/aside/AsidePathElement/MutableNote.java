package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.AsidePath;
import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.Validation.ValidateAsidePath;
import com.github.michaeldsa.aside.Validation.ValidateString;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MutableNote extends Note {
    private Category parent;
    private final ImmutableNote previousState;


    public MutableNote(Category category){
        parent = category;
        this.metaPath = newNoteName(parent.getMetaPath());
        this.viewPath = new ViewPath(this.metaPath);
        this.previousState = null;
        setEmptyFields();
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
        setEmptyFields();
    }

    public MutableNote(ViewPath viewPath){
        // assign viewPath:
        if(endsWithCategoryName(viewPath)){
            this.viewPath = newNoteName(viewPath);
        } else if (endsWithNoteName(viewPath)){
            this.viewPath = viewPath;
        }
        // assign metaPath, parent & previousState:
        this.metaPath = new MetaPath(this.viewPath);
        parent = new Category(viewPath);
        this.previousState = null;
        setEmptyFields();
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

    private void setEmptyFields() {
        title = "";
        content = "";
        to = new HashSet<>();
        from = new HashSet<>();
        tags = new HashSet<>();
    }

    // methods inherited from super:

    // public MetaPath getMetaPath();
    // public ViewPath getViewPath();
    // the following two AsidePathElement methods have been commented out:
    // public void setAsidePaths(MetaPath metaPath) // sets both this.metaPath and this.viewPath at once
    // public void setAsidePaths(ViewPath viewPath) // sets both this.metaPath and this.viewPath at once


    // Getters, Setters & utilities for parent:
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
    // returns true if this.parent is parent of this.metaPath filename
    public boolean parentsMatch() {
        return parent.equals(new Category(this.metaPath));
    }
//    public void setParentMetaPath(MetaPath newParentMetaPath) {
//        this.parent.setAsidePaths(newParentMetaPath);
//    }
//    public void setParentViewPath(ViewPath newParentViewPath) {
//        this.parent.setAsidePaths(newParentViewPath);
//    }

    // getters for metaPath & viewPath:
    public MetaPath getMetaPathNoteName() { return new MetaPath(this.metaPath.getPath().getFileName());}
    public ViewPath getViewPathNoteName() { return new ViewPath(this.viewPath.getPath().getFileName());}


    // getters for metadata
    // getTo, getFrom, getTags return a new reference to prevent mutation upon this reference. Mutation
    public HashSet<String> getTo() {
        return new HashSet<>(to);
    }
    public HashSet<String> getFrom() {
        return new HashSet<>(from);
    }
    public HashSet<String> getTags() {
        return new HashSet<>(tags);
    }
    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }

    // method chaining setters for metadata: reassigning the whole Set
    public MutableNote setTo(Set<String> to) {
        Set<String> replace_to = new HashSet<>();
        to.stream().filter(ValidateString.NOTE_NAME).forEach(replace_to::add);
        this.to = replace_to;
        return this;
    }
    public MutableNote setFrom(Set<String> from) {
        Set<String> replace_from = new HashSet<>();
        from.stream().filter(ValidateString.NOTE_NAME).forEach(replace_from::add);
        this.from = replace_from;
        return this;
    }
    public MutableNote setTags(Set<String> tags) {
        Set<String> replace_tags = new HashSet<>();
        tags.stream().filter(ValidateString.TAG_NAME).forEach(replace_tags::add);
        this.tags = replace_tags;
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

    // method chaining incremental Set mutation for to, from, tags:
    public MutableNote addTo(String... str) {
        Arrays.stream(str).filter(ValidateString.NOTE_NAME).forEach(this.to::add);
        return this;
    }
    public MutableNote addTo(Set<String> set) {
        set.stream().filter(ValidateString.NOTE_NAME).forEach(this.to::add);
        return this;
    }

    public MutableNote addFrom(String... str) {
        Arrays.stream(str).filter(ValidateString.NOTE_NAME).forEach(this.from::add);
        return this;
    }
    public MutableNote addFrom(Set<String> set) {
        set.stream().filter(ValidateString.NOTE_NAME).forEach(this.from::add);
        return this;
    }

    public MutableNote addTags(String... str) {
        Arrays.stream(str).filter(ValidateString.TAG_NAME).forEach(this.tags::add);
        return this;
    }
    public MutableNote addTags(Set<String> set) {
        set.stream().filter(ValidateString.TAG_NAME).forEach(this.tags::add);
        return this;
    }

    public MutableNote removeTo(String... str) {
//        Arrays.asList(str).forEach(to::remove);
        Arrays.stream(str).filter(ValidateString.NOTE_NAME).forEach(this.to::remove);
        return this;
    }
    public MutableNote removeTo(Set<String> set) {
        set.stream().filter(ValidateString.NOTE_NAME).forEach(this.to::remove);
        return this;
    }

    public MutableNote removeFrom(String... str) {
        Arrays.stream(str).filter(ValidateString.NOTE_NAME).forEach(this.from::remove);
        return this;
    }
    public MutableNote removeFrom(Set<String> set) {
        set.stream().filter(ValidateString.NOTE_NAME).forEach(this.from::remove);
        return this;
    }

    public MutableNote removeTags(String... str) {
        Arrays.stream(str).filter(ValidateString.TAG_NAME).forEach(this.tags::remove);
        return this;
    }
    public MutableNote removeTags(Set<String> set) {
        set.stream().filter(ValidateString.TAG_NAME).forEach(this.tags::remove);
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
//        System.out.println("metaPathParent: " + metaPathParent);
        MetaPath noteName = newNoteName(metaPathParent);
//        System.out.println("noteName:" + noteName);
        return new ViewPath(noteName);
    }

    // title and content editing:
    public void prependToTitle(String segment) {
        this.title = segment.strip() + " " + title;
    }
    public void appendToTitle(String segment) {
        this.title += " " + segment.strip();
    }
    public void prependToContent(String segment) {
        this.content = segment.strip() + " " + content;
    }
    public void appendToContent(String segment) {
        this.content += " " + segment.strip();
    }

    // print commands:
    public void printTitle() {
        if (this.title != null) {
            System.out.printf("%s%n    %s%n", "TITLE:", title);
        }
    }
    public void printTo() {
        System.out.printf("%s%n    %s%n", "TO:", to);
    }
    public void printFrom() {
        System.out.printf("%s%n    %s%n", "FROM:", from);
    }
    public void printTags() {
        System.out.printf("%s%n    %s%n","TAGS:", tags);
    }
    public void printContent() {
        System.out.printf("%s%n    %s%n", "CONTENT:", content);
    }
    public void printNoteAll() {
        printTitle();
        printTo();
        printFrom();
        printTags();
        printContent();
    }
    public void printNote() {
        printTitle();
        printContent();
    }

    // Other ideas:
    // Should I set up and configure a Properties object?
    //      That way I can maybe merge merge or replace this Properties with the other...
    // Should I have HashSet<String> instance vars to save elements to remove from the .meta/ properties object?

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MutableNote that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(parent, that.parent) && Objects.equals(previousState, that.previousState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), parent, previousState);
    }

    @Override
    public String toString() {
        return "MutableNote{" +
                "parent=" + parent +
                ", previousState=" + previousState +
                ", title='" + title + '\'' +
                ", to=" + to +
                ", from=" + from +
                ", tags=" + tags +
                ", content='" + content + '\'' +
                ", metaPath=" + metaPath +
                ", viewPath=" + viewPath +
                '}';
    }


}
