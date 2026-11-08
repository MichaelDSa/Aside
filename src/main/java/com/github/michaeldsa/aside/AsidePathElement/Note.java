package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.Validation.ValidateAsidePath;

import java.util.*;

public class Note extends AbstractNote{
    private ImmutableNote previousState;


    // constructors:
    public Note() {
        constructorHelper(RestrictedLists.getMetaPathDefaultDirectoryName());
    }
    public Note(MetaPath metaPath) {
        constructorHelper(metaPath);
    }
    public Note(ViewPath viewPath) {
        constructorHelper(viewPath);
    }
    public Note(Category category){
        constructorHelper(category.getMetaPath());
    }
    public Note(Note note){
        /* unlikely, but can fail if cast to Note */
        if (constructorArgIsInvalid(note.getMetaPath()) || !ValidateAsidePath.NOTE.test(note.getMetaPath())) {
            System.err.println("IllegalArgumentException. Path must end with a Note file name");
            throw new IllegalArgumentException("Invalid Path argument:  " + note.getMetaPath());
        }

        // AsidepathElement fields:
        metaPath = note.getMetaPath();
        viewPath = new ViewPath(metaPath);
        nest = note.getNest();

        // AbstractNote fields:
        title = note.getTitle();
        content = note.getContent();
        to = note.getTo();
        from = note.getFrom();
        tags = note.getTags();
        bibliographies = note.getBibliographies();

        // This class' fields:
        previousState = note.getPreviousState();
    }
    public Note(String asidePath, String... more) {
        constructorHelper(asidePath, more);
    }
    public Note(String[] array) {
        constructorHelper(array[0], Arrays.copyOfRange(array, 1, array.length));
    }

    public void constructorHelper(MetaPath mp) {
        // check endings of parameter. must not end with anything other than Category or Note.
        if (constructorArgIsInvalid(mp)) {
            System.err.println("IllegalArgumentException. Path must end with a Note or Category file name");
            throw new IllegalArgumentException("Invalid Path argument:  " + mp);
        }

        // AsidePathElement fields:
        /* filename assignment if necessary: */
        this.metaPath = AsidePathElement.endsWithNoteName(mp) ? mp : AsidePathElement.generateUniqueFileName(mp);
        /* correct if starts with wrong category/directory: */
        this.metaPath = AsidePathElement.filterMetaPathElements(this.metaPath);
        /* validate new path: */
        if (!ValidateAsidePath.NOTE.test(this.metaPath)) {
            System.err.println("Note: invalid path argument mutation:  \nMetaPath:  " + this.metaPath + "\nViewPath:  " + this.viewPath);
            throw new IllegalArgumentException("Invalid path argument mutation:  \nMetaPath:  " + this.metaPath + "\nViewPath:  " + this.viewPath);
        }
        viewPath = new ViewPath(this.metaPath);
        nest = new ArrayList<>();

        // AbstractNote fields:
        to = new HashSet<>();
        from = new HashSet<>();
        tags = new HashSet<>();
        bibliographies = new HashSet<>();

        // This class' fields:
        previousState = null;
    }
    public void constructorHelper(ViewPath vp) {
        constructorHelper(new MetaPath(vp));
    }
    public void constructorHelper(String first, String... more) {
        if (first.startsWith(".")) {
            constructorHelper(new MetaPath(first, more));
        } else {
            constructorHelper(new ViewPath(first, more));
        }
    }


    @Override
    public String getTitle() { return title; }

    @Override
    public String getContent() { return content; }

    @Override
    public HashSet<String> getTo() { return to; }

    @Override
    public HashSet<String> getFrom() { return from; }

    @Override
    public HashSet<String> getTags() { return tags; }

    @Override
    public HashSet<String> getBibliographies() { return bibliographies; }

    private ImmutableNote getPreviousState() { return previousState; }

    public Note setPreviousState() {
        if (previousState == null) {
            previousState = new ImmutableNote(this);
        }
        return this;
    }

    public Note getInstanceWithSwappedStates() {
        Note instance = new Note(this);
        instance.previousState = null;
        instance.setPreviousState();
        instance.setParentCategory(this.previousState.getParentCategory())
                .setTitle(this.previousState.getTitle())
                .setContent(this.previousState.getContent())
                .setTo(this.previousState.getTo())
                .setFrom(this.previousState.getFrom())
                .setTags(this.previousState.getTags())
                .setBibliographies(this.previousState.getBibliographies());
        return instance;
    }

    public Note setParentCategory(Category newParentCategory) {
        // validate by instantiating new Category
        try {
            metaPath = new Category(newParentCategory).getMetaPath().resolve(metaPath.getFileName());
            viewPath = new ViewPath(metaPath);
        } catch (IllegalArgumentException e) {
            System.err.println("Note.setParentCategory: IllegalArgumentException. Argument must be Category");
        }
        return this;
    }
    public Note setTitle(String title) {
        this.title = title;
        return this;
    }
    public Note setContent(String content) {
        this.content = content;
        return this;
    }
    public Note setTo(HashSet<String> to) {
        this.to = to;
        return this;
    }
    public Note setFrom(HashSet<String> from) {
        this.from = from;
        return this;
    }
    public Note setTags(HashSet<String> tags) {
        this.tags = tags;
        return this;
    }
    public Note setBibliographies(HashSet<String> bibliographies) {
        this.bibliographies = bibliographies;
        return this;
    }
    // setters that add to existing values
    public Note appendToTitle(String title) {
        this.title += " " + title;
        return this;
    }
    public Note prependToTitle(String title) {
        this.title = title + " " + this.title;
        return this;
    }
    public Note appendToContent(String content) {
        this.content += " " + content;
        return this;
    }
    public Note prependToContent(String content) {
        this.content = content + " " + this.content;
        return this;
    }
    public Note addTo(String ... to) {
        this.to.addAll(Arrays.asList(to));
        return this;
    }
    public Note addTo(HashSet<String> to) {
        this.to.addAll(to);
        return this;
    }
    public Note addFrom(String ... from) {
        this.from.addAll(Arrays.asList(from));
        return this;
    }
    public Note addFrom(HashSet<String> from) {
        this.from.addAll(from);
        return this;
    }
    public Note addTags(String ... tags) {
        this.tags.addAll(Arrays.asList(tags));
        return this;
    }
    public Note addTags(HashSet<String> tags) {
        this.tags.addAll(tags);
        return this;
    }
    public Note addBibliographies(String ... bibliographies) {
        this.bibliographies.addAll(Arrays.asList(bibliographies));
        return this;
    }
    public Note addBibliographies(HashSet<String> bibliographies) {
        this.bibliographies.addAll(bibliographies);
        return this;
    }
    // setters that remove from existing values
    public Note removeTo(String ... to) {
        Arrays.asList(to).forEach(this.to::remove);
        return this;
    }
    public Note removeTo(HashSet<String> to) {
        this.to.removeAll(to);
        return this;
    }
    public Note removeFrom(String ... from) {
        Arrays.asList(from).forEach(this.from::remove);
        return this;
    }
    public Note removeFrom(HashSet<String> from) {
        this.from.removeAll(from);
        return this;
    }
    public Note removeTags(String ... tags) {
        Arrays.asList(tags).forEach(this.tags::remove);
        return this;
    }
    public Note removeTags(HashSet<String> tags) {
        this.tags.removeAll(tags);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Note note)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(previousState, note.previousState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), previousState);
    }

    @Override
    public String toString() {
        return "Note{" +
                "previousState=" + previousState +
                ", metaPath=" + metaPath +
                ", viewPath=" + viewPath +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", to=" + to +
                ", from=" + from +
                ", tags=" + tags +
                ", bibliographies=" + bibliographies +
                '}';
    }
}
