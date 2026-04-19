package com.github.michaeldsa.aside.PropertiesUtil;

import com.github.michaeldsa.aside.Pretty;

import java.util.Properties;

public abstract class DiscardedElementPropertiesUtil extends NotePropertiesUtil {
    // property names:
    protected final String warning_n = "warning";
    protected final String message_n = "message";
    protected final String originalMetaPath_n = "original_metapath";
    protected final String originalViewPath_n = "original_viewPath";

    protected String formatViewPathDiscardedElement(Properties properties) {
        String filename = getPropAsString(properties, filename_n);
        String warning = Pretty.format(getPropAsString(properties, warning_n), 80);
        String message = Pretty.format(getPropAsString(properties, message_n), 80);
        String title = Pretty.format(getPropAsString(properties, title_n), 80);
        String content = Pretty.format(getPropAsString(properties, content_n), 80);
        String originalMetaPath = Pretty.format(getPropAsString(properties, originalMetaPath_n), 80);
        String originalViewPath = Pretty.format(getPropAsString(properties, originalViewPath_n), 80);
        String to = Pretty.format(getPropAsString(properties, to_n), 80);
        String from = Pretty.format(getPropAsString(properties, from_n), 80);
        String tags = Pretty.format(getPropAsString(properties, tags_n), 80);


        String nl = "\n";
        if(!filename.isBlank()) {
            filename += nl;
        }
        if(!warning.isBlank()) {
            warning += nl;
        }
        if(!message.isBlank()) {
            message = "MESSAGE:" + nl + message + nl;
        }
        if(!title.isBlank()) {
            title = "TITLE:" + nl + title + nl;
        }
        if(!content.isBlank()) {
            content = "CONTENT:" + nl + content + nl + ('-' * 80) + nl;
        }
        if(!originalMetaPath.isBlank()) {
            originalMetaPath = "ORIGINAL_METAPATH:" + nl + originalMetaPath + nl;
        }
        if(!originalViewPath.isBlank()) {
            originalViewPath = "ORIGINAL_VIEWPATH:" + nl + originalViewPath + nl;
        }
        if(!to.isBlank()) {
            to = "TO:" + nl + to + nl;
        }
        if(!from.isBlank()) {
            from = "FROM:" + nl + from + nl;
        }
        if(!tags.isBlank()) {
            tags = "TO:" + nl + tags + nl;
        }

        return warning + message + title + content + originalMetaPath + originalViewPath + to + from + tags;
    }

}
