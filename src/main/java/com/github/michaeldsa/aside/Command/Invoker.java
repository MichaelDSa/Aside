package com.github.michaeldsa.aside.Command;

import com.github.michaeldsa.aside.Testing.Test;

import java.util.ArrayList;
import java.util.List;

public class Invoker {
    private int index;
    private final List<UndoableCommand> undoableCommands;

    public Invoker() {
        index = -1;
        undoableCommands = new ArrayList<>();
    }

    public void execute(Command command) {
        command.exec();
        if(command instanceof UndoableCommand undoableCommand) {
            index++;
            undoableCommands.add(index, undoableCommand);
        }
        if (index < undoableCommands.size() - 1) {
            level();
        }
    }

    private void level() {
        while (index < undoableCommands.size() - 1) {
            undoableCommands.removeLast();
        }
    }

    public void undo() {
        if (index > -1) {
            undoableCommands.get(index).undo();
            index--;
        } else {
            System.err.println("Nothing to undo");
        }
    }

    public void redo() {
        if (index < undoableCommands.size() - 1) {
            index++;
            undoableCommands.get(index).exec();
        } else {
            System.err.println("Nothing to redo");
        }
    }
}
