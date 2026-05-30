package com.github.michaeldsa.aside;

import com.github.michaeldsa.aside.AsidePathElement.AbstractBibliography;
import com.github.michaeldsa.aside.AsidePathElement.AbstractNote;
import com.github.michaeldsa.aside.AsidePathElement.Author;
import com.github.michaeldsa.aside.AsidePathElement.DiscardedNote;

import java.util.ArrayList;
import java.util.HashSet;

public class Pretty {

    private Pretty() {}
    public static String comma = ",  "; // comma delimiter

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

    public static String authorCollection2String(ArrayList<Author> authors) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < authors.size(); i++) {
            sb.append(i + 1).append(") ").append(authors.get(i).getDisplayName()).append("\n");
        }

        return sb.toString();
    }

    public static String formatBibliography4ViewPath(AbstractBibliography bib, int width) {

        String filename = format(bib.getViewPath().getPath().getFileName().toString(), width);
        String authors = format(authorCollection2String(bib.getAuthors()), width);
        String title = format(bib.getTitle(), width);
        String publishers = format(collections2String(bib.getPublishers()), width);
        String yearPublished = format(collection2String(bib.getYearPublished()), width);
        String comment = format(bib.getComment(), width);
        String references = format(collections2String(bib.getReferences()), width);
        String isbn = format(collections2String(bib.getIsbn()), width);
        String doi = format(collections2String(bib.getDoi()), width);
        String url = format(collections2String(bib.getUrl()), width);
        String arXiv_ID = format(collections2String(bib.getArXiv_ID()), width);
        String adsBibcode = format(collections2String(bib.getAds_Bibcode()), width);

        String nl = "\n";

        if (!filename.isBlank()) {
            filename += "-".repeat(width) + nl.repeat(2);
        }
        if (!title.isBlank()) {
            title = "TITLE:" + nl + title + nl;
        }
        if (!authors.isBlank()) {
            authors = "AUTHORS:" + nl + authors + nl;
        }
        if (!publishers.isBlank()) {
            publishers = "PUBLISHERS:" + nl + publishers + nl;
        }
        if (!yearPublished.isBlank()) {
            yearPublished = "YEAR PUBLISHED:" + nl + yearPublished + nl;
        }
        if (!comment.isBlank()) {
            comment = "COMMENT:" + nl + comment + nl;
        }
        if (!references.isBlank()) {
            references = "REFERENCED BY:" + nl + references + nl;
        }
        if (!isbn.isBlank()) {
            isbn = "ISBN:" + nl + isbn + nl;
        }
        if (!doi.isBlank()) {
            doi = "DOI:" + nl + doi + nl;
        }
        if (!url.isBlank()) {
            url = "URL:" + nl + url + nl;
        }
        if (!arXiv_ID.isBlank()) {
            arXiv_ID = "ARXIV ID:" + nl + arXiv_ID + nl;
        }
        if (!adsBibcode.isBlank()) {
            adsBibcode = "ADS BIBCODE:" + nl + adsBibcode + nl;
        }

        return filename + title + authors + publishers + yearPublished + comment + references + isbn + doi + url + arXiv_ID + adsBibcode;
    }
    public static String formatDiscardedElement4ViewPath(DiscardedNote de, int width) {
        String filename = format(de.getViewPath().getPath().getFileName().toString(), width);
        String warning = format(de.getWarning(), width);
        String message = format(de.getMessage(), width);
        String title = format(de.getTitle(), width);
        String content = format(de.getContent(), width);
        String originalMetaPath = format(de.getOriginalMetaPath().getPath().toString(), width);
        String originalViewPath = format(de.getOriginalMetaPath().getPath().toString(), width);
        String to = format(collections2String(de.getTo()), width);
        String from = format(collections2String(de.getFrom()), width);
        String tags = format(collections2String(de.getTags()), width);
        String bibliographies = format(collections2String(de.getBibliographies()), width);


        String nl = "\n";
        filename += warning + nl;

        if(!message.isBlank()) {
            message = "MESSAGE:" + nl + message + "-".repeat(width) + nl.repeat(2);
        }
        if(!title.isBlank()) {
            title = "TITLE:" + nl + title + nl;
        }
        if(!content.isBlank()) {
            content = "CONTENT:" + nl + content + "-".repeat(width) + nl.repeat(2);
        }
        if(!originalMetaPath.isBlank()) {
            originalMetaPath = "ORIGINAL_METAPATH:" + nl + originalMetaPath + nl;
        }
        if(!originalViewPath.isBlank()) {
            originalViewPath = "ORIGINAL_VIEWPATH:" + nl + originalViewPath + "-".repeat(width) + nl.repeat(2);
        }
        if(!bibliographies.isBlank()) {
            bibliographies = "BIBLIOGRAPHY:" + nl + bibliographies + nl;
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

        return filename + message + title + content + originalMetaPath + originalViewPath + bibliographies + to + from + tags;
    }
    public static String formatNote4ViewPath(AbstractNote note, int width) {
        // get all metadata as String via format().
        String filename = format(note.getViewPath().getPath().getFileName().toString(), width);
        String title = format(note.getTitle(), width);
        String content  = format(note.getContent(), width);
        String to = format(collections2String(note.getTo()), width);
        String from = format(collections2String(note.getFrom()), width);
        String tags = format(collections2String(note.getTags()), width);
        String bibliographies = format(collections2String(note.getBibliographies()), width);

        String nl = "\n";

        if (!filename.isBlank()) {
            filename += "-".repeat(width) + nl.repeat(2);
        }
        if (!title.isBlank()) {
            title = "TITLE:" + nl + title + nl;
        }
        if (!content.isBlank()) {
            content = "CONTENT:" + nl + content + "-".repeat(width) + nl.repeat(2);
        }
        if (!bibliographies.isBlank()) {
            bibliographies = "BIBLIOGRAPHY:" + nl + bibliographies + nl;
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

        return filename + title + content + bibliographies + to + from + tags;
    }

    public static String collections2String(HashSet<String> hashSet) {
        if (hashSet == null || hashSet.isEmpty()) {
            return "";
        }
        return String.join(comma, hashSet);
    }

    public static String collection2String(HashSet<Integer> hashSet) {
        HashSet<String> strings = new HashSet<>();
        for (Integer i : hashSet) {
            strings.add(String.valueOf(i));
        }
        return collections2String(strings);
    }

    public static String collection2String(ArrayList<String> arrayList) {
        if (arrayList == null || arrayList.isEmpty()) {
            return "";
        }

        return String.join(comma, arrayList);
    }
}
