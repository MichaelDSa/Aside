package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;

import java.util.*;

public class Note extends AbstractNote{
    private ImmutableNote previousState;


    // constructors:
    public Note(MetaPath mp) {
        // if filename belongs to bibliography or DiscardedElement:
        if (argumentIsInvalid(mp)) {
            System.err.println("IllegalArgumentException: " + mp);
            throw new IllegalArgumentException("Invalid Path argument (This is either a DiscardedElement or a Bibliography filename): " + mp);
        }
        if (AsidePathElement.endsWithNoteName(mp)) {
            metaPath = mp;
        } else {
            metaPath = AsidePathElement.generateUniqueFileName(mp);
        }
        metaPath = AsidePathElement.filterMetaPathElements(metaPath);

        viewPath = new ViewPath(metaPath);
        stepParent = null;
        nest = new ArrayList<>();
        previousState = null;
        to = new HashSet<>();
        from = new HashSet<>();
        tags = new HashSet<>();
        bibliographies = new HashSet<>();
    }
    public Note(ViewPath vp) {
        // if filename belongs to bibliography or DiscardedElement:
        if (argumentIsInvalid(vp)) {
            System.err.println("IllegalArgumentException: " + vp);
            throw new IllegalArgumentException("Invalid Path argument (This is either a DiscardedElement or a Bibliography filename):  " + vp);
        }
        if (AsidePathElement.endsWithNoteName(vp)) {
            viewPath = vp;
        } else {
            viewPath = new ViewPath(AsidePathElement.generateUniqueFileName(new MetaPath(vp)));
        }
        viewPath = AsidePathElement.filterViewPathElements(viewPath);

        metaPath = new MetaPath(viewPath);
        stepParent = null;
        nest = new ArrayList<>();
        previousState = null;
        to = new HashSet<>();
        from = new HashSet<>();
        tags = new HashSet<>();
        bibliographies = new HashSet<>();
    }
    public Note(Category c){
        // if filename belongs to bibliography or DiscardedElement:
        if (argumentIsInvalid(c.getMetaPath())) {
            System.err.println("IllegalArgumentException: " + c.getMetaPath());
            throw new IllegalArgumentException("Invalid Path argument (This is either a DiscardedElement or a Bibliography filename):  " + c.getMetaPath());
        }
        metaPath = AsidePathElement.filterMetaPathElements(c.getMetaPath());
        metaPath = AsidePathElement.generateUniqueFileName(c.getMetaPath());
        viewPath = new ViewPath(metaPath);
        stepParent = (Category) c.getStepParentCategory();
        nest = new ArrayList<>();
        previousState = null;
        to = new HashSet<>();
        from = new HashSet<>();
        tags = new HashSet<>();
        bibliographies = new HashSet<>();
    }
    public Note(Note mn){
        // if filename belongs to bibliography or DiscardedElement:
        if (argumentIsInvalid(mn.getMetaPath())) {
            System.err.println("IllegalArgumentException: " + mn.getMetaPath());
            throw new IllegalArgumentException("Invalid Path argument " + mn.getMetaPath());
        }
        metaPath = mn.getMetaPath();
        viewPath = new ViewPath(metaPath);
        stepParent = (Category) mn.getStepParentCategory();
        nest = mn.getNest();
        previousState = new ImmutableNote(mn);
        title = mn.getTitle();
        content = mn.getContent();
        to = mn.getTo();
        from = mn.getFrom();
        tags = mn.getTags();
        bibliographies = mn.getBibliographies();
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

    @Override
    public ImmutableNote getPreviousState() { return previousState; }

    @Override
    public AbstractCategory getParentCategory() { return new Category(metaPath); }

    @Override
    public AbstractCategory getStepParentCategory() { return stepParent; }

    @Override
    public void setStepParentCategory(AbstractCategory newStepParent) { stepParent = (Category) newStepParent; }

    public Note setPreviousState() {
        previousState = new ImmutableNote(this);
        return this;
    }

    @Override
    public boolean hasStepParent() { return stepParent != null; }

    // setters that replace existing values
    public Note setStepParents(Category stepParent) {
        setStepParentCategory(stepParent);
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
                ", stepParent=" + stepParent +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", to=" + to +
                ", from=" + from +
                ", tags=" + tags +
                ", bibliographies=" + bibliographies +
                '}';
    }
}
