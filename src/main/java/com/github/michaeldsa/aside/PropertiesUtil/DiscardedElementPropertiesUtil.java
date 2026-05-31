package com.github.michaeldsa.aside.PropertiesUtil;

public abstract class DiscardedElementPropertiesUtil extends PropertiesUtil {
    /* DiscardedElementPropertiesUtil is the parent
    of all retriever/writer & PropertyUtil classes of
    DiscardedElements. It's purpose is to declare constants shared
    by these classes instead of redundantly re-assigning them in
    each class.*/

    // all property key names shared by subclasses:
    protected final String warning_k = "warning";
    protected final String message_k = "message";
    protected final String originalFileType_k = "original_filetype";
    protected final String originalMetaPath_k = "original_metaPath";
    protected final String originalViewPath_k = "original_viewPath";

    protected DiscardedElementPropertiesUtil() {
        super();
    }
}
