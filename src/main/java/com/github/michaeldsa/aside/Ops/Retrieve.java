package com.github.michaeldsa.aside.Ops;

import com.github.michaeldsa.aside.AsidePathElement.*;
import com.github.michaeldsa.aside.PropertiesUtil.PropUtils;

import java.nio.file.Files;

public enum Retrieve implements CrudOps<AsidePathElement, AsidePathElement> {


    BIBLIOGRAPHY (ape -> {
        Bibliography bibliography;
        if (ape instanceof Bibliography) {
            bibliography = new Bibliography((Bibliography) ape);
        } else {
            System.err.println("Retrieve.BIBLIOGRAPHY: can ony operate on Bibliography types\ntype provided: " + ape.getClass().getSimpleName());
            return ape;
        }
        if (Files.notExists(bibliography.getMetaPath().getPath())){
            System.err.println("Retrieve.BIBLIOGRAPHY: file does not exists: " + bibliography.getMetaPath());
            return bibliography;
        }
        PropUtils.retrieveBibliography(bibliography);
        return bibliography;
    }),
    DISCARDED_BIBLIOGRAPHY (ape -> {
        DiscardedBibliography discardedBibliography;
        if (ape instanceof DiscardedBibliography) {
            discardedBibliography = new DiscardedBibliography((DiscardedBibliography) ape);
        } else {
            System.err.println("Retrieve.DISCARDED_BIBLIOGRAPHY: can ony operate on DiscardedBibliography types\ntype provided: " + ape.getClass().getSimpleName());
            return ape;
        }
        if (Files.notExists(discardedBibliography.getMetaPath().getPath())) {
            System.err.println("Retrieve.DISCARDED_BIBLIOGRAPHY: file does not exists: " + discardedBibliography.getMetaPath());
            return discardedBibliography;
        }
        PropUtils.retrieveDiscardedBibliography(discardedBibliography);
        return discardedBibliography;
    }),
    DISCARDED_NOTE (ape -> {
        DiscardedNote discardedNote;
        if (ape instanceof DiscardedNote) {
            discardedNote = new DiscardedNote((DiscardedNote) ape);
        } else {
            System.err.println("Retrieve.DISCARDED_NOTE: can ony operate on DiscardedNote types\ntype provided: " + ape.getClass().getSimpleName());
            return ape;
        }
        if (Files.notExists(discardedNote.getMetaPath().getPath())) {
            System.err.println("Retrieve.DISCARDED_NOTE: file does not exists: " + discardedNote.getMetaPath());
            return discardedNote;
        }
        PropUtils.retrieveDiscardedNote(discardedNote);
        return discardedNote;
    }),
    NOTE (ape -> {
        Note note;
        if (ape instanceof Note) {
            note = new Note((Note) ape);
        } else {
            System.err.println("Retrieve.NOTE: can ony operate on Note types\ntype provided: " + ape.getClass().getSimpleName());
            return ape;
        }
        if (Files.notExists(note.getMetaPath().getPath())) {
            System.err.println("Retrieve.NOTE: file does not exists: " + note.getMetaPath());
            return note;
        }
        PropUtils.retrieveNote(note);
        return note;
    });

    private final CrudOps<AsidePathElement, AsidePathElement> fops;
    Retrieve(CrudOps<AsidePathElement, AsidePathElement> fops) {
        this.fops = fops;
    }
    @Override
    public AsidePathElement execute(AsidePathElement ape) {
        return fops.execute(ape);
    }
}
