package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.AsidePathElement.DiscardedElement;

import java.nio.file.Paths;
import java.util.HashSet;

public class DiscardedElementReader extends DiscardedElementPropertiesUtil{

    public DiscardedElementReader(DiscardedElement de) {

        properties = getFileProperties(de.getMetaPath().getPath());

        // get original MetaPath from de properties file, assign to new DiscardedElement. Will have same metaPath/viewPath fields as param.
        de = new DiscardedElement(new MetaPath(Paths.get(getOriginalMetaPathProp())));
        de.setTitle(getTitleProp())
                .setContent(getContentProp())
                .setMessage(getMessageProp())
                .setTo(getToProp())
                .setFrom(getFromProp())
                .setTags(getTagsProp());
    }

    private String getStringProp(String prop) {
        String val = properties.getProperty(prop);
        if (val == null) {
            val = "";
        }
        return val;
    }
    private HashSet<String> getHashSetProp(String prop) {
        HashSet<String> val = toHashSet(properties.getProperty(prop));
        if (val == null) {
            val = new HashSet<>();
        }
        return val;
    }
    private String getOriginalMetaPathProp() {
        return getStringProp(originalMetaPath_n);
    }
    private String getOriginalViewPathProp() {
        return getStringProp(originalViewPath_n);
    }
    private String getTitleProp() {
        return getStringProp(title_n);
    }
    private String getContentProp() {
        return getStringProp(content_n);
    }
    private String getMessageProp() {
        return getStringProp(message_n);
    }
    private HashSet<String> getToProp() {
        return getHashSetProp(to_n);
    }
    private HashSet<String> getFromProp() {
        return getHashSetProp(from_n);
    }
    private HashSet<String> getTagsProp() {
        return getHashSetProp(tags_n);
    }
}
