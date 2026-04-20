package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.AsidePathElement.DiscardedElement;

import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Properties;

public class DiscardedElementReader extends DiscardedElementPropertiesUtil{

    public DiscardedElementReader() {
        properties = new Properties();
    }


    private MetaPath getOriginalMetaPathProp(Properties properties) {
        return new MetaPath(Paths.get(getPropAsString(properties, originalMetaPath_n)));
    }
    private ViewPath getOriginalViewPathProp(Properties properties) {
        return new ViewPath(Paths.get(getPropAsString(properties, originalViewPath_n)));
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

        loadPropertiesFile(properties, de.getMetaPath().getPath());

        // assign all fields from properties
        de.setOriginalMetaPath(getOriginalMetaPathProp(properties))
                .setOriginalViewPath(getOriginalViewPathProp(properties))
                .setMessage(getMessageProp(properties))
                .setTitle(getTitleProp(properties))
                .setContent(getContentProp(properties))
                .setTo(getToProp(properties))
                .setFrom(getFromProp(properties))
                .setTags(getTagsProp(properties));

    }
}
