package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePathElement.AbstractBibliography;
import com.github.michaeldsa.aside.Pretty;
import com.github.michaeldsa.aside.Settings.Settings;

public class BibliographyWriter extends BibliographyPropertiesUtil {

    public BibliographyWriter() {
        super();
    }

    public void write(AbstractBibliography bib) {
        setProperties(bib);
        writeProperties(bib.getMetaPath().getPath());
        writeToViewPath(bib);

    }

    public void writeToViewPath(AbstractBibliography bib) {
        setProperties(bib);
        writeViewPath(bib.getMetaPath().getPath(), Pretty.formatBibliography4ViewPath(bib, Settings.getLineWidth().file()));
    }
}
