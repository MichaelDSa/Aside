package com.github.michaeldsa.aside.Testing.CommandPatternTest;

import java.util.ArrayList;
import java.util.List;

public class Invoker1 {

    private static class CmdPair {
        private final Cmd cmd;
        private final Cmd undo;
        CmdPair(Cmd cmd, Cmd und) {
            this.cmd = cmd;
            undo = und;
        }
        private boolean exec() {
            return cmd.execute();
        }
        private boolean undo() {
            return undo.execute();
        }
    }

    private final List<CmdPair> cmds = new ArrayList<>();
    private int i;

    public Invoker1() {
        i = -1;
    }

    public void execute(Cmd cmd, Cmd und) {
        CmdPair pair = new CmdPair(cmd, und);
        if(pair.exec()) {
            i++;
            cmds.add(i, pair);
        } else {
            System.out.println("Warning: exec returned false vale. You should verify the operation");
        }

        // if a pair is added to middle of the list,
        // remove potentially conflicting commands
        // that may occur after the inserted cmd.
        if(i < cmds.size() - 1) {
            level();
        }
    }

    private void level() {
        while(i < cmds.size() - 1) {
            cmds.removeLast();
        }
    }

    public void undo() {
        if(i > -1){
            if(!cmds.get(i).undo()) {
                System.out.println("Warning: undo returned false value. You should verify the operation");
            }
            i--;
        } else {
            System.out.println("nothing to undo");
        }
    }

    public void redo() {
        if(i < cmds.size() - 1) {
            i++;
            if(!cmds.get(i).exec()) {
                System.out.println("Warning: redo returned false value. You should verify the operation");
            }
        } else {
            System.out.println("nothing to redo");
        }
    }
}
