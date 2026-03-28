package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePathElement.DiscardedElement;

import java.util.Properties;

public class DiscardedElementWriter extends DiscardedElementPropertiesUtil {

    public DiscardedElementWriter(DiscardedElement de) {
        m_path = de.getMetaPath().getPath();
        v_path = de.getViewPath().getPath();
        properties = new Properties();
        properties.setProperty(title_n, de.getTitle());
        properties.setProperty(content_n, de.getContent());
        properties.setProperty(message_n, de.getMessage());
        properties.setProperty(to_n, super.hashSetToString(de.getTo()));
        properties.setProperty(from_n, super.hashSetToString(de.getFrom()));
        properties.setProperty(tags_n, super.hashSetToString(de.getTags()));
        writeProperties();
    }



}
