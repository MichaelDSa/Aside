package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePathElement.DiscardedElement;

import java.util.Properties;

public class DiscardedElementWriter extends DiscardedElementPropertiesUtil {

    public DiscardedElementWriter() {
        properties = new Properties();
    }

    private void setProperties(DiscardedElement de) {
        String filename = de.getMetaPath().getFileName().toString();
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

    public void write(DiscardedElement de) {

        // set properties
        setProperties(de);

        // write to metapath
        writeProperties(properties, de.getMetaPath().getPath());

        // write to viewpath
        writeViewPath(de.getMetaPath().getPath(), formatViewPathDiscardedElement(properties));

    }

    public void writeToViewPath(DiscardedElement de) {
        writeViewPath(de.getMetaPath().getPath(), formatViewPathDiscardedElement(properties));
    }


}
