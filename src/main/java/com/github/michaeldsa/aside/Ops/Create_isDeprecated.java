package com.github.michaeldsa.aside.Ops;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Properties;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

public interface Create_isDeprecated<T> extends FileOps<T> {

//    T execute(T t);

    static Create_isDeprecated<MetaPath> begin(Create_isDeprecated<MetaPath> c) {
        return c;
    }
    default Create_isDeprecated<T> create(Create_isDeprecated<T> c) {
        return (mp) -> c.execute(execute(mp));
    }

    static MetaPath newCategory(MetaPath metaPath) {
        return Alg.CREATE_NEW_CATEGORY.execute(metaPath);
    }
    static MetaPath newNote(MetaPath parent) {
        return Alg.CREATE_NEW_NOTE.execute(parent);
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
            for (int i = 0; i < 60; i++) {
                try {
                    Thread.sleep(1000);
                    name = newNoteName(parent);

                    if (Files.notExists(name.getPath())) {
                        break;
                    } else {
                        System.out.print(".");
                        name = null;
                    }
                } catch (InterruptedException ex) {
                    System.out.printf("Thread.sleep() exception: %s%n", ex);
                }
            }
        }
        return Objects.requireNonNull(name, "Create.newNoteName(): failed to generate unique file name");
    }

    // implementations:
    enum Alg implements Create_isDeprecated<MetaPath> {
        CREATE_NEW_CATEGORY {
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
        CREATE_NEW_NOTE {
            @Override
            public MetaPath execute(MetaPath parent) {
                // one file to be created for MetaPath metadata
                // another file to be created for ViewPath

                // This just creates the files only.
                // to write content to note, use UPDATE_NOTE.


                // define the fields of the MetaPath file:
                Properties prop = new Properties();
                String[] fields = {"title", "to", "from", "tags", "content"};
                for(String field : fields) {
                    prop.setProperty(field, "");
                }

                if(Files.notExists(parent.getPath())) {
                    System.out.println("Not exists: " + parent.getPath());
                    return parent;
                }

                // generate time stamp and resolve to parent:
                MetaPath note = Create_isDeprecated.newNoteName(parent);

                // create .meta/ and view/ files
                if(Files.notExists(note.getPath())) {
                    try (OutputStream metadata = Files.newOutputStream(note.getPath(), CREATE, WRITE)) {

                        // create empty properties .txt file in aside_notes/.meta/
                        prop.store(metadata, null);

                        // if success, create empty .txt file in aside_notes/view/
                        if (Files.exists(note.getPath())) {
                            Files.createFile(new ViewPath(note).getPath());
                        }

                    } catch (IOException e) {
                        System.err.printf("unable to create file %s %s%n", note.getPath().toAbsolutePath(), e);
                    }
                } else {
                    System.err.printf("File already exists %s%n",note.getPath());
                }
                return note;
            }
        }

    }


}