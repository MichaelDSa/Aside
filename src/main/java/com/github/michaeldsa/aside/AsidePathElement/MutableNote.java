package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;

import java.util.*;

public class MutableNote extends AbstractNote{
    private ImmutableNote previousState;


    // constructors:
    public MutableNote(MetaPath mp) {
        // if filename belongs to bibliography or DiscardedElement:
        if (hasIllegalArgument(mp)) {
            System.err.println("IllegalArgumentException: " + mp);
            throw new IllegalArgumentException("Invalid Path argument " + mp);
        }
        if (AsidePathElement.endsWithNoteName(mp)) {
            metaPath = mp;
        } else {
            metaPath = AbstractNote.generateNewNoteName(mp);
        }
        metaPath = AsidePathElement.filterMetaPathElements(metaPath);

        viewPath = new ViewPath(metaPath);
        stepParent = null;
        nest = new ArrayList<>();
        previousState = null;
        to = new HashSet<>();
        from = new HashSet<>();
        tags = new HashSet<>();
    }
    public MutableNote(ViewPath vp) {
        // if filename belongs to bibliography or DiscardedElement:
        if (hasIllegalArgument(vp)) {
            System.err.println("IllegalArgumentException: " + vp);
            throw new IllegalArgumentException("Invalid Path argument " + vp);
        }
        if (AsidePathElement.endsWithNoteName(vp)) {
            viewPath = vp;
        } else {
            viewPath = new ViewPath(AbstractNote.generateNewNoteName(new MetaPath(vp)));
        }
        viewPath = AsidePathElement.filterViewPathElements(viewPath);

        metaPath = new MetaPath(viewPath);
        stepParent = null;
        nest = new ArrayList<>();
        previousState = null;
        to = new HashSet<>();
        from = new HashSet<>();
        tags = new HashSet<>();
    }
    public MutableNote(Category c){
        // if filename belongs to bibliography or DiscardedElement:
        if (hasIllegalArgument(c.getMetaPath())) {
            System.err.println("IllegalArgumentException: " + c.getMetaPath());
            throw new IllegalArgumentException("Invalid Path argument " + c.getMetaPath());
        }
        metaPath = AsidePathElement.filterMetaPathElements(c.getMetaPath());
        metaPath = AbstractNote.generateNewNoteName(c.getMetaPath());
        viewPath = new ViewPath(metaPath);
        stepParent = (Category) c.getStepParentCategory();
        nest = new ArrayList<>();
        previousState = null;
        to = new HashSet<>();
        from = new HashSet<>();
        tags = new HashSet<>();
    }
    public MutableNote(MutableNote mn){
        // if filename belongs to bibliography or DiscardedElement:
        if (hasIllegalArgument(mn.getMetaPath())) {
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
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public HashSet<String> getTo() {
        return to;
    }

    @Override
    public HashSet<String> getFrom() {
        return from;
    }

    @Override
    public HashSet<String> getTags() {
        return tags;
    }

    @Override
    public ImmutableNote getPreviousState() {
        return previousState;
    }

    @Override
    public AbstractCategory getParentCategory() {
        return new Category(metaPath);
    }

    @Override
    public AbstractCategory getStepParentCategory() {
        return stepParent;
    }

    @Override
    public void setStepParentCategory(AbstractCategory newStepParent) {
        stepParent = (Category) newStepParent;
    }

    public MutableNote setPreviousState() {
        previousState = new ImmutableNote(this);
        return this;
    }

    @Override
    public boolean hasStepParent() {
        return stepParent != null;
    }

    // setters that replace existing values
    public MutableNote setStepParents(Category stepParent) {
        setStepParentCategory(stepParent);
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
    // setters that add to existing values
    public MutableNote appendToTitle(String title) {
        this.title += " " + title;
        return this;
    }
    public MutableNote prependToTitle(String title) {
        this.title = title + " " + this.title;
        return this;
    }
    public MutableNote appendToContent(String content) {
        this.content += " " + content;
        return this;
    }
    public MutableNote prependToContent(String content) {
        this.content = content + " " + this.content;
        return this;
    }
    public MutableNote addTo(String ... to) {
        this.to.addAll(Arrays.asList(to));
        return this;
    }
    public MutableNote addTo(HashSet<String> to) {
        this.to.addAll(to);
        return this;
    }
    public MutableNote addFrom(String ... from) {
        this.from.addAll(Arrays.asList(from));
        return this;
    }
    public MutableNote addFrom(HashSet<String> from) {
        this.from.addAll(from);
        return this;
    }
    public MutableNote addTags(String ... tags) {
        this.tags.addAll(Arrays.asList(tags));
        return this;
    }
    public MutableNote addTags(HashSet<String> tags) {
        this.tags.addAll(tags);
        return this;
    }
    // setters that remove from existing values
    public MutableNote removeTo(String ... to) {
        Arrays.asList(to).forEach(this.to::remove);
        return this;
    }
    public MutableNote removeTo(HashSet<String> to) {
        this.to.removeAll(to);
        return this;
    }
    public MutableNote removeFrom(String ... from) {
        Arrays.asList(from).forEach(this.from::remove);
        return this;
    }
    public MutableNote removeFrom(HashSet<String> from) {
        this.from.removeAll(from);
        return this;
    }
    public MutableNote removeTags(String ... tags) {
        Arrays.asList(tags).forEach(this.tags::remove);
        return this;
    }
    public MutableNote removeTags(HashSet<String> tags) {
        this.tags.removeAll(tags);
        return this;
    }

    @Override
    public String toString() {
        return "MutableNote{" +
                "stepParent=" + stepParent +
                ", previousState=" + previousState +
                ", viewPath=" + viewPath +
                ", metaPath=" + metaPath +
                ", tags=" + tags +
                ", from=" + from +
                ", to=" + to +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MutableNote that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(stepParent, that.stepParent) && Objects.equals(previousState, that.previousState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), stepParent, previousState);
    }
}
