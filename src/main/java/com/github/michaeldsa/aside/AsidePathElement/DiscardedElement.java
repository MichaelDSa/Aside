package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.Validation.ValidateAsidePath;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;

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

    private final MetaPath originalMetaPath;
    private final ViewPath originalViewPath;

    // Note metadata:
    private String title;
    private String content;
    private final String warning = "THIS IS A DISCARDED ELEMENT";
    private String message;
    private HashSet<String> to;
    private HashSet<String> from;
    private HashSet<String> tags;

    private DiscardedElement() {
        originalMetaPath = new MetaPath(Paths.get(RestrictedLists.getMetaPathDiscardedDirectoryName()));
        originalViewPath = new ViewPath(Paths.get(RestrictedLists.getViewPathDiscardedDirectoryName()));
    }

    public DiscardedElement(MetaPath mp) {
        Path m_default = Paths.get(RestrictedLists.getMetaPathDefaultDirectoryName());
        Path m_discarded = Paths.get(RestrictedLists.getMetaPathDiscardedDirectoryName());
        // no metapath root or default (contents of default ok)
        if (!mp.equals(new MetaPath())
                || !mp.equals(new MetaPath(m_default))) {
            metaPath = new MetaPath(m_discarded).resolve(mp.getFileName());
            viewPath = new ViewPath(metaPath);
        } else {
            metaPath = new MetaPath(m_discarded);
            viewPath = new ViewPath(metaPath);
        }

        originalMetaPath = mp;
        originalViewPath = new ViewPath(mp);

        title = "";
        content = "";
        message = "";
        to = new HashSet<>();
        from = new HashSet<>();
        tags = new HashSet<>();
    }

    public DiscardedElement(ViewPath vp) {
        Path v_default = Paths.get(RestrictedLists.getViewPathDefaultDirectoryName());
        Path v_discarded = Paths.get(RestrictedLists.getViewPathDiscardedDirectoryName());
        // no viewpath root or default (contents of default ok)
        if (!vp.equals(new ViewPath())
                || !vp.equals(new ViewPath(v_default))) {
            viewPath = new ViewPath(v_discarded).resolve(vp.getFileName());
            metaPath = new MetaPath(viewPath);
        } else {
            viewPath = new ViewPath(v_discarded);
            metaPath = new MetaPath(viewPath);
        }

        originalViewPath = vp;
        originalMetaPath = new MetaPath(vp);

        title = "";
        content = "";
        message = "";
        to = new HashSet<>();
        from = new HashSet<>();
        tags = new HashSet<>();
    }

    public DiscardedElement(MutableNote mn) {
        Path m_default = Paths.get(RestrictedLists.getMetaPathDefaultDirectoryName());
        Path m_discarded = Paths.get(RestrictedLists.getMetaPathDiscardedDirectoryName());
        if (!mn.getMetaPath().equals(new MetaPath())
                || !mn.getMetaPath().equals(new MetaPath(m_default))) {
            metaPath = new MetaPath(m_discarded).resolve(mn.getMetaPath().getFileName());
            viewPath = new ViewPath(metaPath);
        } else {
            metaPath = new MetaPath(m_discarded);
            viewPath = new ViewPath(metaPath);
        }

        originalMetaPath = mn.getMetaPath();
        originalViewPath = mn.getViewPath();

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
}