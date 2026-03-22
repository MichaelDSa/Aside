package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.Validation.ValidateAsidePath;

import java.nio.file.Path;
import java.nio.file.Paths;

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
    "originalMetaPath" and "originalViewPath".
     */

    private final MetaPath originalMetaPath;
    private final ViewPath originalViewPath;

    private final boolean isNote;
    private final boolean isCategory;

    public DiscardedElement() {
        metaPath = new MetaPath(Paths.get(RestrictedLists.getMetaPathDiscardedDirectoryName()));
        viewPath = new ViewPath(metaPath);
        isNote = false;
        isCategory = false;
        originalMetaPath = metaPath;
        originalViewPath = viewPath;
    }

    public DiscardedElement(Category cat) {
        Path m_default = Paths.get(RestrictedLists.getMetaPathDefaultDirectoryName());
        Path m_discarded = Paths.get(RestrictedLists.getMetaPathDiscardedDirectoryName());
        MetaPath mp = cat.getMetaPath();
        // no metapath root or default (contents of default ok)
        if (!mp.equals(new MetaPath())
                || !mp.getPath().equals(m_default)) {
            metaPath = new MetaPath(m_discarded).resolve(mp.getFileName());
            viewPath = new ViewPath(metaPath);
        } else {
            metaPath = new MetaPath(m_discarded);
            viewPath = new ViewPath(metaPath);
        }
        isNote = false;
        isCategory = true;
        originalMetaPath = cat.getMetaPath();
        originalViewPath = cat.getViewPath();

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

        isNote = ValidateAsidePath.NOTE_NAME.test(mp);
        isCategory = ValidateAsidePath.CATEGORY_NAME.test(mp);
        originalMetaPath = mp;
        originalViewPath = new ViewPath(mp);
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

        isNote = ValidateAsidePath.NOTE_NAME.test(viewPath);
        isCategory = ValidateAsidePath.CATEGORY_NAME.test(vp);
        originalViewPath = vp;
        originalMetaPath = new MetaPath(originalViewPath);
    }

    public DiscardedElement(MutableNote mn) {
        Path m_default = Paths.get(RestrictedLists.getMetaPathDefaultDirectoryName());
        Path m_discarded = Paths.get(RestrictedLists.getMetaPathDiscardedDirectoryName());
        if (!mn.getMetaPath().equals(new MetaPath()) || !mn.getMetaPath().equals(new MetaPath(m_default))) {
            metaPath = new MetaPath(m_discarded).resolve(mn.getMetaPath().getFileName());
            viewPath = new ViewPath(metaPath);
        } else {
            metaPath = new MetaPath(m_discarded);
            viewPath = new ViewPath(metaPath);
        }

        isNote = true;
        isCategory = false;
        originalMetaPath = mn.getMetaPath();
        originalViewPath = mn.getViewPath();
    }

    public boolean isCategory() {
        return isCategory;
    }

    public boolean isNote() {
        return isNote;
    }

    public MetaPath getOriginalMetaPath() {
        return originalMetaPath;
    }

    public ViewPath getOriginalViewPath() {
        return originalViewPath;
    }

    @Override
    public Category getParentCategory() {
        return null;
    }

    @Override
    public Category getStepParentsCategory() {
        return null;
    }

    @Override
    public void setStepParentsCategory(Category newStepParents) {

    }

    @Override
    public boolean hasStepParents() {
        return false;
    }
}