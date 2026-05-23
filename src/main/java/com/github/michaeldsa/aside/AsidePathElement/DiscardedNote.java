package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.Validation.ValidateAsidePath;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class DiscardedNote extends AbstractDiscardedElement {
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

    // Note metadata:
    private String title;
    private String content;
    private String message;
    private HashSet<String> to;
    private HashSet<String> from;
    private HashSet<String> tags;

    // constructors:
    public DiscardedNote(MetaPath mp) {
        // mp must end with a DiscardedNote filename.
        if(!ValidateAsidePath.DISCARDED_NOTE_NAME.test(mp)) {
            System.err.println("IllegalArgumentException: " + mp);
            throw new IllegalArgumentException("Invalid Path argument " + mp);
        }
        fileTypeName = fileTypeName_note;
        fileNamePrefix = fileNamePrefix_discardedNote;

        // reformat filename
        // DiscardedElement may only have parent .DISCARDED and DISCARDED.
        metaPath = discardedCategory.getMetaPath().resolve(renameMetaPathFileName(mp));
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

    public DiscardedNote(ViewPath vp) {
        // if vp has wrong filename
        if(argumentIsInvalid(vp)) {
            System.err.println("IllegalArgumentException: " + vp);
            throw new IllegalArgumentException("Invalid Path argument " + vp);
        }
        fileTypeName = fileTypeName_note;
        fileNamePrefix = fileNamePrefix_discardedNote;

        // FileName must be reformatted
        // DiscardedElement may only have parent .DISCARDED and DISCARDED.
        viewPath = discardedCategory.getViewPath().resolve(renameViewPathFileName(vp));
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

    public DiscardedNote(MutableNote mn) {
        // if somehow mn has bad filename:
        if(argumentIsInvalid(mn.getMetaPath())) {
            System.err.println("IllegalArgumentException: " + mn.getMetaPath());
            throw new IllegalArgumentException("Invalid Path argument " + mn.getMetaPath());
        }
        fileTypeName = fileTypeName_note;
        fileNamePrefix = fileNamePrefix_discardedNote;

        metaPath = discardedCategory.getMetaPath().resolve(renameMetaPathFileName(mn.getMetaPath().getFileName()));
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

    // getters & setters:

    public DiscardedNote setOriginalMetaPath(MetaPath original) {
        originalMetaPath = original;
        originalViewPath = new ViewPath(originalMetaPath);
        return this;
    }
    public DiscardedNote setOriginalViewPath(ViewPath original) {
        originalViewPath = original;
        originalMetaPath = new MetaPath(originalViewPath);
        return this;
    }

    // metadata getters:
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getWarning() { return warning;}
    public String getMessage() { return message; }
    public HashSet<String> getTo() { return to;}
    public HashSet<String> getFrom() { return from; }
    public HashSet<String> getTags() { return tags; }

    // metadata setters:
    public DiscardedNote setTitle(String title) {
        this.title = title;
        return this;
    }
    public DiscardedNote setContent(String content) {
        this.content = content;
        return this;
    }
    public DiscardedNote setMessage(String message) {
        this.message = message;
        return this;
    }
    public DiscardedNote appendToMessage(String message) {
        this.message += " " + message;
        return this;
    }
    public DiscardedNote prependToMessage(String message) {
        this.message = message + " " + this.message;
        return this;
    }
    public DiscardedNote setTo(HashSet<String> to) {
        this.to = to;
        return this;
    }
    public DiscardedNote setFrom(HashSet<String> from) {
        this.from = from;
        return this;
    }
    public DiscardedNote setTags(HashSet<String> tags) {
        this.tags = tags;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DiscardedNote that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(title, that.title) && Objects.equals(content, that.content) && Objects.equals(message, that.message) && Objects.equals(to, that.to) && Objects.equals(from, that.from) && Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, content, message, to, from, tags);
    }

    @Override
    public String toString() {
        return "DiscardedNote{" +
                "originalMetaPath=" + originalMetaPath +
                ", tags=" + tags +
                ", from=" + from +
                ", to=" + to +
                ", message='" + message + '\'' +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", originalViewPath=" + originalViewPath +
                ", fileTypeName='" + fileTypeName + '\'' +
                ", fileNamePrefix='" + fileNamePrefix + '\'' +
                ", metaPath=" + metaPath +
                ", viewPath=" + viewPath +
                ", nest=" + nest +
                '}';
    }
}