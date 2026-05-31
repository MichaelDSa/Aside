package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePathElement.DiscardedBibliography;
import com.github.michaeldsa.aside.Pretty;
import com.github.michaeldsa.aside.Settings.Settings;

public class DiscardedBibliographyWriter extends DiscardedBibliographyPropertiesUtil {
    public DiscardedBibliographyWriter() {
        super();
    }
    public void write(DiscardedBibliography db) {
        setProperties(db);
        writeProperties(db.getMetaPath().getPath());
        writeToViewPath(db);
    }

    public void writeToViewPath(DiscardedBibliography db) {
        setProperties(db);
        writeViewPath(db.getMetaPath().getPath(), Pretty.formatDiscardedBibliography4ViewPath(db, Settings.getLineWidth().file()));
    }
}
