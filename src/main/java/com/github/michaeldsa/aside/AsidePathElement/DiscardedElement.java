package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.Validation.ValidateAsidePath;
import com.github.michaeldsa.aside.Validation.ValidateAsidePathElement;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class DiscardedElement extends AsidePathElement {
    /*
    DiscardedElement is meant to access the DISCARDED MetaPath &
    ViewPath directories, which are otherwise inaccessible to other
    AsidePathElements. DiscardedElement can either be a 'category'
    or 'note' type of path. isCategory and isNote can distinguish
    which kind of element the client is dealing with. This subclass
    is meant to participate in handling files and directories in the
    DISCARDED directory, for which each file and category should
    exist a properties file with the extra properties, "isCategory",
    "originalMetaPath" and "originalViewPath". Additionally,
    DiscardedElements files will also have a human readable warning,
    as well as an optional message for the user's useage.
     */

    private MetaPath originalMetaPath;
    private ViewPath originalViewPath;

    // Note metadata:
    private String title;
    private String content;
    private final String warning = "THIS IS A DISCARDED ELEMENT";
    private String message;
    private HashSet<String> to;
    private HashSet<String> from;
    private HashSet<String> tags;

    private DiscardedElement() {
        metaPath = new MetaPath(Paths.get(RestrictedLists.getMetaPathDiscardedDirectoryName()));
        viewPath = new ViewPath(Paths.get(RestrictedLists.getViewPathDiscardedDirectoryName()));
        originalMetaPath = metaPath;
        originalViewPath = viewPath;
        nest = new ArrayList<>();
    }

    public DiscardedElement(MetaPath mp) {
        Path m_discarded = Paths.get(RestrictedLists.getMetaPathDiscardedDirectoryName());

        // DiscardedElement must use an existing note
        if(!ValidateAsidePath.NOTE_NAME.test(mp)) {
            System.err.println("IllegalArgumentException: " + mp);
            throw new IllegalArgumentException("Invalid Path argument " + mp);
        }
        // DiscardedElement may only have parent .DISCARDED and DISCARDED.
        metaPath = new MetaPath(m_discarded).resolve(mp.getFileName());
        viewPath = new ViewPath(metaPath);

        originalMetaPath = mp;
        originalViewPath = new ViewPath(mp);
        nest = new ArrayList<>();

        title = "";
        content = "";
        message = "";
        to = new HashSet<>();
        from = new HashSet<>();
        tags = new HashSet<>();
    }

    public DiscardedElement(ViewPath vp) {
        Path v_discarded = Paths.get(RestrictedLists.getViewPathDiscardedDirectoryName());

        // DiscardedElement must use an existing note
        if(!ValidateAsidePath.NOTE_NAME.test(vp)) {
            System.err.println("IllegalArgumentException: " + vp);
            throw new IllegalArgumentException("Invalid Path argument " + vp);
        }

        // DiscardedElement may only have parent .DISCARDED and DISCARDED.
        viewPath = new ViewPath(v_discarded).resolve(vp.getFileName());
        metaPath = new MetaPath(viewPath);

        originalViewPath = vp;
        originalMetaPath = new MetaPath(vp);
        nest = new ArrayList<>();

        title = "";
        content = "";
        message = "";
        to = new HashSet<>();
        from = new HashSet<>();
        tags = new HashSet<>();
    }

    public DiscardedElement(MutableNote mn) {
        Path m_discarded = Paths.get(RestrictedLists.getMetaPathDiscardedDirectoryName());

        if(ValidateAsidePathElement.NOTE_NAME.test(mn)) {
            System.err.println("IllegalArgumentException: " + mn);
            throw new IllegalArgumentException("Invalid Path argument " + mn.getMetaPath());
        }
        metaPath = new MetaPath(m_discarded).resolve(mn.getMetaPath().getFileName());
        viewPath = new ViewPath(metaPath);

        originalMetaPath = mn.getMetaPath();
        originalViewPath = mn.getViewPath();
        nest = new ArrayList<>();

        title = mn.getTitle();
        content = mn.getContent();
        message = "";
        to = mn.getTo();
        from = mn.getFrom();
        tags = mn.getTags();
    }

    // get empty final DiscardedElement:
    public static DiscardedElement getEmptyDiscardedElement() {
        return new DiscardedElement();
    }

    // get original AsidePaths:
    public MetaPath getOriginalMetaPath() { return originalMetaPath; }
    public ViewPath getOriginalViewPath() { return originalViewPath; }

    // set original AsidePaths:
    public DiscardedElement setOriginalMetaPath(MetaPath mp) {
        originalMetaPath = mp;
        return this;
    }
    public DiscardedElement setOriginalViewPath(ViewPath vp) {
        originalViewPath = vp;
        return this;
    }

    // other metadata getters:
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getWarning() { return warning;}
    public String getMessage() { return message; }
    public HashSet<String> getTo() { return to;}
    public HashSet<String> getFrom() { return from; }
    public HashSet<String> getTags() { return tags; }

    // Other metadata setters:
    public DiscardedElement setTitle(String title) {
        this.title = title;
        return this;
    }
    public DiscardedElement setContent(String content) {
        this.content = content;
        return this;
    }
    public DiscardedElement setMessage(String message) {
        this.message = message;
        return this;
    }
    public DiscardedElement appendToMessage(String message) {
        this.message += " " + message;
        return this;
    }
    public DiscardedElement prependToMessage(String message) {
        this.message = message + " " + this.message;
        return this;
    }
    public DiscardedElement setTo(HashSet<String> to) {
        this.to = to;
        return this;
    }
    public DiscardedElement setFrom(HashSet<String> from) {
        this.from = from;
        return this;
    }
    public DiscardedElement setTags(HashSet<String> tags) {
        this.tags = tags;
        return this;
    }

    @Override
    public Category getParentCategory() { return null; }

    @Override
    public Category getStepParentCategory() { return null; }

    @Override
    public void setStepParentCategory(Category newStepParents) { }

    @Override
    public boolean hasStepParents() {
        return false;
    }


    // set up object methods: toString, equals, hashCode.

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DiscardedElement that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(originalMetaPath, that.originalMetaPath) && Objects.equals(originalViewPath, that.originalViewPath) && Objects.equals(title, that.title) && Objects.equals(content, that.content) && Objects.equals(warning, that.warning) && Objects.equals(message, that.message) && Objects.equals(to, that.to) && Objects.equals(from, that.from) && Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), originalMetaPath, originalViewPath, title, content, warning, message, to, from, tags);
    }

    @Override
    public String toString() {
        return "DiscardedElement{" +
                "metaPath=" + metaPath +
                ", viewPath=" + viewPath +
                ", originalMetaPath=" + originalMetaPath +
                ", originalViewPath=" + originalViewPath +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", warning='" + warning + '\'' +
                ", message='" + message + '\'' +
                ", to=" + to +
                ", from=" + from +
                ", tags=" + tags +
                '}';
    }
}