package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePathElement.DiscardedElement;

import java.util.Properties;

public class DiscardedElementWriter extends DiscardedElementPropertiesUtil {

    public DiscardedElementWriter() {
        properties = new Properties();
    }

    private String emptyIfNull(String str) {
        return str == null ? "" : str;
    }

    public void write(DiscardedElement de) {
        loadPropertiesFile(properties, de.getMetaPath().getPath());

        String title = emptyIfNull(de.getTitle());
        String content = emptyIfNull(de.getContent());
        String message = emptyIfNull(de.getMessage());
        String isCategory = String.valueOf(de.isCategory());
        String isNote = String.valueOf(de.isNote());
        String to = hashSetToString(de.getTo());
        String from = hashSetToString(de.getFrom());
        String tags = hashSetToString(de.getTags());

        properties.setProperty(title_n, title);
        properties.setProperty(content_n, content);
        properties.setProperty(message_n, message);
        properties.setProperty(isCategory_n, isCategory);
        properties.setProperty(isNote_n, isNote);
        properties.setProperty(to_n, to);
        properties.setProperty(from_n, from);
        properties.setProperty(tags_n, tags);

        writeProperties(properties, de.getMetaPath().getPath());

    }



}
