package com.github.michaeldsa.aside.AsidePathElement;

import com.github.michaeldsa.aside.AsidePath.AsidePath;
import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;

import java.nio.file.Paths;
import java.util.Objects;

public abstract class AbstractDiscardedElement extends AsidePathElement {

    protected final DiscardedCategory discardedCategory = RestrictedLists.getDiscardedCategory();


    // warning should go in all discarded element properties files
    protected final String warning = "THIS IS A DISCARDED ELEMENT";

    // original paths of discarded elements:
    protected MetaPath originalMetaPath;
    protected ViewPath originalViewPath;

    // filetype name property values:
    protected String fileTypeName; // subclasses assign preset below:
    protected final String fileTypeName_note = "note";
    protected final String fileTypeName_bibliography = "bibliography";

    // filename prefixes:
    protected String fileNamePrefix;
    protected final String fileNamePrefix_discardedNote = ".d";
    protected final String fileNamePrefix_discardedBibliography = ".db";



    // bequeathed methods:

    public MetaPath getOriginalMetaPath() {
        return originalMetaPath;
    }
    public ViewPath getOriginalViewPath() {
        return originalViewPath;
    }
    public String getWarning() { return warning; }

    // abstract methods:

    public abstract AbstractDiscardedElement setOriginalMetaPath(MetaPath original);
    public abstract AbstractDiscardedElement setOriginalViewPath(ViewPath original);


    // methods for subclass constructor use:

    protected boolean startsWithDiscardedCategory(AsidePath ap) {
        boolean success = false;
        if (ap instanceof MetaPath mp) {
            success = mp.startsWith(discardedCategory.getMetaPath());
        } else if (ap instanceof ViewPath vp) {
            success = vp.startsWith(discardedCategory.getViewPath());
        }
        return success;
    }

    // return a filename that conforms to DiscardedElement filename format
    // fileNamePrefix must be assigned to preset var by each subclass!
    protected MetaPath renameMetaPathFileName(MetaPath mp) {
        // format to: `.dxxxxxx_xxxx_xx.txt`
        String prefix = fileNamePrefix;
        String fileName = mp.getPath().getFileName().toString();
        String remainder = fileName.substring(fileName.length() - 18); // 18: length of date stamp
        String newFileName = prefix + remainder;
        return new MetaPath(Paths.get(newFileName));
    }

    // fileNamePrefix must be assigned to preset var by each subclass!
    protected ViewPath renameViewPathFileName(ViewPath viewPath) {
        // format to:  `dxxxxxx_xxxx_xx.txt`
        String prefix = fileNamePrefix.substring(1); // remove dot
        String fileName = viewPath.getPath().getFileName().toString();
        String remainder = fileName.substring(fileName.length() - 18); // 18: length of date stamp
        String newFileName = prefix + remainder;
        return new ViewPath(Paths.get(newFileName));
    }


    // inherited, but not used.
    @Override
    public AbstractCategory getParentCategory() { return null; }
    @Override
    public AbstractCategory getStepParentCategory() { return null; }
    @Override
    public void setStepParentCategory(AbstractCategory newStepParent) { }
    @Override
    public boolean hasStepParent() { return false; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AbstractDiscardedElement that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(originalMetaPath, that.originalMetaPath) && Objects.equals(originalViewPath, that.originalViewPath) && Objects.equals(fileTypeName, that.fileTypeName) && Objects.equals(fileNamePrefix, that.fileNamePrefix);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), originalMetaPath, originalViewPath, fileTypeName, fileNamePrefix);
    }

    @Override
    public String toString() {
        return "AbstractDiscardedElement{" +
                "discardedCategory=" + discardedCategory +
                ", fileTypeName='" + fileTypeName + '\'' +
                ", fileNamePrefix='" + fileNamePrefix + '\'' +
                ", metaPath=" + metaPath +
                ", viewPath=" + viewPath +
                ", nest=" + nest +
                '}';
    }
}
