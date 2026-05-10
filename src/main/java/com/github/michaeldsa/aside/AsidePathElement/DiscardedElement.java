package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.AsidePath;
import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.Validation.ValidateAsidePath;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class DiscardedElement extends AsidePathElement {
    /*
    DiscardedElement is meant to access the DISCARDED permanent Category, which
    is otherwise inaccessible by Category or AbstractNote subclasses, and can
    store only files that can be read by DiscardedElementReader. DISCARDED is
    used when an the user is questioning whether to delete an AsidePathElement,
    such as MutableNote, or Bibliography (MutableBib). Categories
    cannot be 'discarded', or moved to DISCARDED, or be written by
    DiscardedElementWriter. A DiscardedElement may be restored back to it's
    original Category. If the original Category exist in the sandbox, it can
    be re-written to its old Category. If the old urls no longer exist, they
    may either be re-written, or a new url (within the AsidePath sandbox)
    may be specified by the user.
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

    // methods for constructor use:
    private boolean hasIllegalArgument(AsidePath ap) {
        return !ValidateAsidePath.NOTE_NAME.test(ap)
                && !ValidateAsidePath.BIBLIOGRAPHY_NAME.test(ap)
                && !ValidateAsidePath.DISCARDED_ELEMENT_NAME.test(ap);
    }

    // constructors:
    private DiscardedElement() {
        metaPath = new MetaPath(Paths.get(RestrictedLists.getMetaPathDiscardedDirectoryName()));
        viewPath = new ViewPath(Paths.get(RestrictedLists.getViewPathDiscardedDirectoryName()));
        originalMetaPath = metaPath;
        originalViewPath = viewPath;
        nest = new ArrayList<>();
    }

    public DiscardedElement(MetaPath mp) {
        // if mp has wrong filename:
        if(hasIllegalArgument(mp)) {
            System.err.println("IllegalArgumentException: " + mp);
            throw new IllegalArgumentException("Invalid Path argument " + mp);
        }
        Path m_discarded = Paths.get(RestrictedLists.getMetaPathDiscardedDirectoryName());

        // reformat filename
        // DiscardedElement may only have parent .DISCARDED and DISCARDED.
        metaPath = new MetaPath(m_discarded).resolve(renameMetaPathFileName(mp));
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
        // if vp has wrong filename
        if(hasIllegalArgument(vp)) {
            System.err.println("IllegalArgumentException: " + vp);
            throw new IllegalArgumentException("Invalid Path argument " + vp);
        }
        Path v_discarded = Paths.get(RestrictedLists.getViewPathDiscardedDirectoryName());

        // DiscardedElement must use an existing note
        if(!ValidateAsidePath.NOTE_NAME.test(vp) && !ValidateAsidePath.BIBLIOGRAPHY_NAME.test(vp)) {
            System.err.println("IllegalArgumentException: " + vp);
            throw new IllegalArgumentException("Invalid Path argument " + vp);
        }

        // FileName must be reformatted
        // DiscardedElement may only have parent .DISCARDED and DISCARDED.
        viewPath = new ViewPath(v_discarded).resolve(renameViewPathFileName(vp));
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
        // if somehow mn has wrong filename:
        if(hasIllegalArgument(mn.getMetaPath())) {
            System.err.println("IllegalArgumentException: " + mn.getMetaPath());
            throw new IllegalArgumentException("Invalid Path argument " + mn.getMetaPath());
        }
        Path m_discarded = Paths.get(RestrictedLists.getMetaPathDiscardedDirectoryName());

        if(!ValidateAsidePath.NOTE_NAME.test(mn.getMetaPath())) {
            System.err.println("IllegalArgumentException: " + mn);
            throw new IllegalArgumentException("Invalid Path argument " + mn.getMetaPath());
        }
        metaPath = new MetaPath(m_discarded).resolve(renameMetaPathFileName(mn.getMetaPath().getFileName()));
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

    // Methods for constructor use:
    // return a filename that conforms to DiscardedElement filename format
    private MetaPath renameMetaPathFileName(MetaPath mp) {
        // format to: `.dxxxxxx_xxxx_xx.txt`
        String prefix = ".d";
        String remainder = mp.getPath().getFileName().toString().substring(1);
        String fileName = prefix + remainder;
        return new MetaPath(Paths.get(fileName));
    }
    private ViewPath renameViewPathFileName(ViewPath viewPath) {
        // format to:  `dxxxxxx_xxxx_xx.txt`
        String prefix = "d";
        String remainder = viewPath.getPath().getFileName().toString();
        String fileName = prefix + remainder;
        return new ViewPath(Paths.get(fileName));
    }

    // get empty final DiscardedElement:
    public static DiscardedElement getEmptyDiscardedElement() {
        return new DiscardedElement();
    }



    // getters & setters:

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
    public AbstractCategory getParentCategory() { return null; }

    @Override
    public AbstractCategory getStepParentCategory() { return null; }

    @Override
    public void setStepParentCategory(AbstractCategory newStepParent) { }

    @Override
    public boolean hasStepParent() {
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