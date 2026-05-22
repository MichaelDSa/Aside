package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;

public class AbstractDiscardedElement extends AsidePathElement {
    protected MetaPath originalMetaPath;
    protected ViewPath originalViewPath;

    // filetype name property values:
    protected final String fileTypeName_note = "note";
    protected final String fileTypeName_bibliography = "bibliography";

    // filename prefixes:
    protected final String fileNamePrefix_discardedNote = ".d";
    protected final String fileNamePrefix_discardedBibliography = ".db";


    // inherited, but not used.
    @Override
    public AbstractCategory getParentCategory() { return null; }
    @Override
    public AbstractCategory getStepParentCategory() { return null; }
    @Override
    public void setStepParentCategory(AbstractCategory newStepParent) { }
    @Override
    public boolean hasStepParent() { return false; }
}
