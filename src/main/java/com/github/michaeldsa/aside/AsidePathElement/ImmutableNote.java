package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ImmutableNote extends AbstractNote{
    private final MetaPath metaPath;
    private final ViewPath viewPath;
    private final String title;
    private final String content;
    private final HashSet<String> to;
    private final HashSet<String> from;
    private final HashSet<String> tags;
    private final Category stepParent;
    // ImmutableNote previousState;
    public ImmutableNote(MutableNote mn) {
        this.metaPath = mn.metaPath;
        this.viewPath = mn.viewPath;
        this.nest = mn.getNest();
        this.stepParent = mn.getStepParentCategory();
        this.title = mn.getTitle();
        this.content = mn.getContent();
        this.to = new HashSet<>(Collections.unmodifiableSet(getTo()));
        this.from = new HashSet<>(Collections.unmodifiableSet(getFrom()));
        this.tags = new HashSet<>(Collections.unmodifiableSet(getTags()));
    }
    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getContent() {
        return this.content;
    }

    @Override
    public HashSet<String> getTo() {
        return this.to;
    }

    @Override
    public HashSet<String> getFrom() {
        return this.from;
    }

    @Override
    public HashSet<String> getTags() {
        return this.tags;
    }

    @Override
    public Category getParentCategory() {
        return new Category(metaPath);
    }

    @Override
    public Category getStepParentCategory() {
        return this.stepParent;
    }

    @Override
    public void setStepParentCategory(Category newStepParents) {
        return;
    }

    @Override
    public ImmutableNote getPreviousState() {
        return this;
    }

    @Override
    public boolean hasStepParents() {
        return this.stepParent != null;
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
                ", stepParent=" + stepParent +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ImmutableNote that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(metaPath, that.metaPath) && Objects.equals(viewPath, that.viewPath) && Objects.equals(title, that.title) && Objects.equals(content, that.content) && Objects.equals(to, that.to) && Objects.equals(from, that.from) && Objects.equals(tags, that.tags) && Objects.equals(stepParent, that.stepParent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), metaPath, viewPath, title, content, to, from, tags, stepParent);
    }
}
