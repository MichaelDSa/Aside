package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePathElement.DiscardedNote;
import com.github.michaeldsa.aside.Pretty;
import com.github.michaeldsa.aside.Settings.Settings;

public class DiscardedNotePropsWriter extends DiscardedNotePropsUtil {

    public DiscardedNotePropsWriter() {
        super();
    }

    public void write(DiscardedNote dn) {
        setProperties(dn);
        writeProperties(dn.getMetaPath().getPath());
        writeToViewPath(dn);
    }

    public void writeToViewPath(DiscardedNote dn) {
        setProperties(dn);
        writeViewPath(dn.getMetaPath().getPath(), Pretty.formatDiscardedElement4ViewPath(dn, Settings.getLineWidth().file()));
    }


}
