package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.AsidePath;
import com.github.michaeldsa.aside.Validation.ValidateAsidePath;

import java.util.HashSet;
import java.util.Objects;

public abstract class AbstractNote extends AsidePathElement {
    // optional:
    protected Category stepParent;

    protected String title;
    protected String content;
    protected HashSet<String> to;
    protected HashSet<String> from;
    protected HashSet<String> tags;
    protected HashSet<String> bibliographies;

    // getters:
    public abstract String getTitle();
    public abstract String getContent();
    public abstract HashSet<String> getTo();
    public abstract HashSet<String> getFrom();
    public abstract HashSet<String> getTags();
    public abstract HashSet<String> getBibliographies();
    public abstract ImmutableNote getPreviousState();


    // static methods:

    // methods for constructor use:
    protected boolean constructorArgIsInvalid(AsidePath ap) {
        /* If an AsidePath with a filename is submitted, it must be a
        Note filename, and not pass one of the below tests. However,
        if the AsidePath contains a wrong directory element
        (RestrictedLists.permanentDirectories), the offending element will
        be removed. The parameter will be modified to conform to sandbox
        rules using AsidePathElement.filterMetaPathElements().  */
        return ValidateAsidePath.BIBLIOGRAPHY_NAME.test(ap)
                || ValidateAsidePath.DISCARDED_NOTE_NAME.test(ap)
                || ValidateAsidePath.DISCARDED_BIBLIOGRAPHY_NAME.test(ap);
    }

    @Override
    public String toString() {
        return "AbstractNote{" +
                "stepParent=" + stepParent +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", to=" + to +
                ", from=" + from +
                ", tags=" + tags +
                ", bibliographies=" + bibliographies +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AbstractNote that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(stepParent, that.stepParent) && Objects.equals(title, that.title) && Objects.equals(content, that.content) && Objects.equals(to, that.to) && Objects.equals(from, that.from) && Objects.equals(tags, that.tags) && Objects.equals(bibliographies, that.bibliographies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), stepParent, title, content, to, from, tags, bibliographies);
    }
}
