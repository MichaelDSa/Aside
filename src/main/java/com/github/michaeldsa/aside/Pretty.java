package com.github.michaeldsa.aside;

import com.github.michaeldsa.aside.AsidePathElement.AbstractNote;
import com.github.michaeldsa.aside.AsidePathElement.DiscardedElement;

import java.util.HashSet;

public class Pretty {

    private Pretty() {}

    public static void print(String string) {
        print(string, 80);
    }
    public static void print(String string, int width) {
        String regex = String.format("(.{1,%d})(\\s+|$)", width);
        string = string.replaceAll(regex, "$1%n");
        System.out.printf(string);
    }
    public static String format(String string, int width) {
        if (string != null) {
            String regex = String.format("(.{1,%d})(\\s+|$)", width);
            return string.replaceAll(regex, "$1\n");
        }
        return "";
    }
    public static String formatDiscardedElement4ViewPath(DiscardedElement de, int width) {
        String filename = format(de.getMetaPath().getPath().getFileName().toString(), width);
        String warning = format(de.getWarning(), width);
        String message = format(de.getMessage(), width);
        String title = format(de.getTitle(), width);
        String content = format(de.getContent(), width);
        String originalMetaPath = format(de.getOriginalMetaPath().getPath().toString(), width);
        String originalViewPath = format(de.getOriginalMetaPath().getPath().toString(), width);
        String to = format(hashSet2String(de.getTo()), width);
        String from = format(hashSet2String(de.getFrom()), width);
        String tags = format(hashSet2String(de.getTags()), width);


        String nl = "\n";
        filename += warning + nl;

        if(!message.isBlank()) {
            message = "MESSAGE:" + nl + message + "-".repeat(80) + nl.repeat(2);
        }
        if(!title.isBlank()) {
            title = "TITLE:" + nl + title + nl;
        }
        if(!content.isBlank()) {
            content = "CONTENT:" + nl + content + "-".repeat(80) + nl.repeat(2);
        }
        if(!originalMetaPath.isBlank()) {
            originalMetaPath = "ORIGINAL_METAPATH:" + nl + originalMetaPath + nl;
        }
        if(!originalViewPath.isBlank()) {
            originalViewPath = "ORIGINAL_VIEWPATH:" + nl + originalViewPath + "-".repeat(80) + nl.repeat(2);
        }
        if(!to.isBlank()) {
            to = "TO:" + nl + to + nl;
        }
        if(!from.isBlank()) {
            from = "FROM:" + nl + from + nl;
        }
        if(!tags.isBlank()) {
            tags = "TAGS:" + nl + tags + nl;
        }

        return filename + message + title + content + originalMetaPath + originalViewPath + to + from + tags;
    }
    public static String formatNote4ViewPath(AbstractNote note, int width) {
        // get all metadata as String via format().
        String filename = format(note.getViewPath().getPath().getFileName().toString(), width);
        String title = format(note.getTitle(), width);
        String content  = format(note.getContent(), width);
        String to = format(hashSet2String(note.getTo()), width);
        String from = format(hashSet2String(note.getFrom()), width);
        String tags = format(hashSet2String(note.getTags()), width);

        String nl = "\n";

        if (!filename.isBlank()) {
            filename += "-".repeat(80) + nl.repeat(2);
        }
        if (!title.isBlank()) {
            title = "TITLE:" + nl + title + nl;
        }
        if (!content.isBlank()) {
            content = "CONTENT:" + nl + content + "-".repeat(80) + nl.repeat(2);
        }
        if (!to.isBlank()) {
            to = "TO:" + nl + to + nl;
        }
        if (!from.isBlank()) {
            from = "FROM:" + nl + from + nl;
        }
        if (!tags.isBlank()) {
            tags = "TAGS:" + nl + tags + nl;
        }

        return filename + title + content + to + from + tags;
    }

    public static String hashSet2String(HashSet<String> hashSet) {
        if (hashSet == null || hashSet.isEmpty()) {
            return "";
        }
        return hashSet.toString()
                .replace("[", "")
                .replace("]", "")
                .replace(",", "");
    }
}
