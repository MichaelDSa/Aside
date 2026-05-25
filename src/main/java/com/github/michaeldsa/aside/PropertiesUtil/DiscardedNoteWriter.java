package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePathElement.DiscardedNote;
import com.github.michaeldsa.aside.Pretty;
import com.github.michaeldsa.aside.Settings.Settings;

import java.util.Properties;

public class DiscardedNoteWriter extends DiscardedNotePropertiesUtil {

    public DiscardedNoteWriter() {
        properties = new Properties();
    }

    private void setProperties(DiscardedNote de) {
        String filename = de.getMetaPath().getPath().getFileName().toString();
        String warning = emptyIfNull(de.getWarning());
        String message = emptyIfNull(de.getMessage());
        String title = emptyIfNull(de.getTitle());
        String content = emptyIfNull(de.getContent());
        String original_mp = emptyIfNull(de.getOriginalMetaPath().toString());
        String original_vp = emptyIfNull(de.getOriginalViewPath().toString());
        String to = hashSetToString(de.getTo());
        String from = hashSetToString(de.getFrom());
        String tags = hashSetToString(de.getTags());

        properties.clear();

        properties.setProperty(filename_n, filename);
        properties.setProperty(warning_n, warning);
        properties.setProperty(message_n, message);
        properties.setProperty(title_n, title);
        properties.setProperty(content_n, content);
        properties.setProperty(originalMetaPath_n, original_mp);
        properties.setProperty(originalViewPath_n, original_vp);
        properties.setProperty(to_n, to);
        properties.setProperty(from_n, from);
        properties.setProperty(tags_n, tags);

    }

    public void write(DiscardedNote de) {

        // set properties
        setProperties(de);

        // write to metapath
        writeProperties(properties, de.getMetaPath().getPath());

        // write to viewpath
        writeToViewPath(de);

    }

    public void writeToViewPath(DiscardedNote de) {
        setProperties(de);
        writeViewPath(de.getMetaPath().getPath(), Pretty.formatDiscardedElement4ViewPath(de, Settings.getLineWidth().file()));
    }


}
