package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePathElement.DiscardedElement;

import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Properties;

public class DiscardedElementReader extends DiscardedElementPropertiesUtil{

    public DiscardedElementReader() { }


    private String getOriginalMetaPathProp(Properties properties) {
        return getPropAsString(properties, originalMetaPath_n);
    }
    private String getOriginalViewPathProp(Properties properties) {
        return getPropAsString(properties, originalViewPath_n);
    }
    private String getIsCategoryProp(Properties properties) {
        return getPropAsString(properties, isCategory_n);
    }
    private String getIsNoteProp(Properties properties) {
        return getPropAsString(properties, isNote_n);
    }
    private String getTitleProp(Properties properties) {
        return getPropAsString(properties, title_n);
    }
    private String getContentProp(Properties properties) {
        return getPropAsString(properties,content_n);
    }
    private String getMessageProp(Properties properties) {
        return getPropAsString(properties,message_n);
    }
    private HashSet<String> getToProp(Properties properties) {
        return getPropAsHashSet(properties,to_n);
    }
    private HashSet<String> getFromProp(Properties properties) {
        return getPropAsHashSet(properties,from_n);
    }
    private HashSet<String> getTagsProp(Properties properties) {
        return getPropAsHashSet(properties,tags_n);
    }

    public void read(DiscardedElement de) {
        // Local Properties instead; No need for field var.
        Properties properties = getFileProperties(de.getMetaPath().getPath());

        // get original MetaPath from de properties file, assign
        // to new DiscardedElement. Will have same metaPath/viewPath
        // fields as param. That way field stays final.
        de = new DiscardedElement(new MetaPath(Paths.get(getOriginalMetaPathProp(properties))));

        // assign all fields from property
        de.setTitle(getTitleProp(properties))
                .setContent(getContentProp(properties))
                .setMessage(getMessageProp(properties))
                .setTo(getToProp(properties))
                .setFrom(getFromProp(properties))
                .setTags(getTagsProp(properties));

    }
}
