package com.github.michaeldsa.aside;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

public interface Create<T> {

    T execute(T t);


    static MetaPath newCategory(MetaPath metaPath) {
        return Alg.NEW_CATEGORY.execute(metaPath);
    }
    static MetaPath newNote(MetaPath parent) {
        return Alg.NEW_NOTE.execute(parent);
    }

    // generate a MetaPath that ends with the unique file name formatted for notes.
    private static MetaPath newNoteName(MetaPath parent) {
        // generate date stamp String starting with `.` and ending with `.txt`.
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd_hhmm_ss");
        String note_name = "." + now.format(formatter) + ".txt";

        // Make MetaPath of the note name resolved to parent
        MetaPath name = new MetaPath(Paths.get(note_name));
        name = parent.resolve(name);

        // edge case: resolve naming conflict
        if (Files.exists(name.getPath())) {
            for (int i = 0; i < 25; i++) {
                try {
                    Thread.sleep(1000);
                    name = newNoteName(parent);

                    if (name != null && Files.notExists(name.getPath())) {
                        break;
                    }
                } catch (InterruptedException ex) {
                    System.out.printf("Thread.sleep() exception: %s%n", ex);
                }
            }
        }
        return name != null && Files.notExists(name.getPath()) ? name : null;
    }

    // implementations:
    enum Alg implements Create<MetaPath> {
        NEW_CATEGORY {
            @Override
            public MetaPath execute(MetaPath mp) {
                if(Files.notExists(mp.getPath())) {
                    try {
                        Files.createDirectories(mp.getPath());
                        if (Files.exists(mp.getPath())) {
                            Files.createDirectories(new ViewPath(mp).getPath());
                        }
                    } catch (IOException ex) {
                        System.err.printf("unable to create directories %s%n", mp);
                        return null;
                    }
                }
                return mp;
            }
        },
        NEW_NOTE {
            @Override
            public MetaPath execute(MetaPath parent) {

                // define the fields of the MetaPath file:
                Properties prop = new Properties();
                String[] fields = {"title", "to", "from", "tags", "content"};
                for(String field : fields) {
                    prop.setProperty(field, "");
                }

                // generate time stamp and resolve to parent:
                MetaPath note = Create.newNoteName(parent);
                // create .meta/ and view/ files
                if(note != null && Files.notExists(note.getPath())) {
                    try (OutputStream metadata = Files.newOutputStream(note.getPath(), CREATE, WRITE)) {

                        // create empty properties .txt file in aside_notes/.meta/
                        prop.store(metadata, null);

                        // if success, create empty .txt file in aside_notes/view/
                        if (Files.exists(note.getPath())) {
                            Files.createFile(new ViewPath(note).getPath());
                        }

                    } catch (IOException e) {
                        System.err.printf("unable to create file %s %s%n", note.getPath(), e);
                    }
                } else {
                    System.err.printf("File already exists %s%n", note != null ? note.getPath() : null);
                }
                return note;
            }
        }

    }


}