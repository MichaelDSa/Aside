package com.github.michaeldsa.aside.Command;

import com.github.michaeldsa.aside.AsidePathElement.Bibliography;
import com.github.michaeldsa.aside.AsidePathElement.Note;
import com.github.michaeldsa.aside.Ops.Create;
import com.github.michaeldsa.aside.Ops.Delete;
import com.github.michaeldsa.aside.Ops.Retrieve;
import com.github.michaeldsa.aside.Ops.Update;
import com.github.michaeldsa.aside.Pretty;
import com.github.michaeldsa.aside.Settings.Settings;

public class Commands {

    public Command createNote(Note note) {
        return new CrudOpsCommand<>(Create.NOTE, Delete.NOTE, note);
    }
    public Command deleteNote(Note note) {
        return new CrudOpsCommand<>(Delete.NOTE, Create.NOTE, note);
    }

    public Command updateNote(Note note) {
        note = (Note) Retrieve.NOTE.execute(note);
        return new CrudOpsCommand<>(Update.NOTE, note, note.getInstanceWithSwappedStates());
    }
    // lambdas
    public Command viewNote(Note note) {
        return () -> System.out.println( Pretty.formatNote4ViewPath((Note) Retrieve.NOTE.execute(note), Settings.lineWidths().getStdoutLW()) );
    }
    public Command viewBibliography(Bibliography bibliography) {
        return () -> System.out.println( Pretty.formatBibliography4ViewPath((Bibliography) Retrieve.BIBLIOGRAPHY.execute(bibliography), Settings.lineWidths().getStdoutLW()) );
    }

}
