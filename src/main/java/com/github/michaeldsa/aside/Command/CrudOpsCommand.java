package com.github.michaeldsa.aside.Command;

import com.github.michaeldsa.aside.Ops.CrudOps;

public class CrudOpsCommand<T,R> implements UndoableCommand {
    CrudOps<T,R> execCrudOps;
    CrudOps<T,R> undoCrudOps;
    T execApe;
    T undoApe;

    public CrudOpsCommand(CrudOps<T,R> execCrudOps, CrudOps<T,R> undoCrudOps, T execApe, T undoApe) {
        this.execCrudOps = execCrudOps;
        this.undoCrudOps = undoCrudOps;
        this.execApe = execApe;
        this.undoApe = undoApe;
    }
    public CrudOpsCommand(CrudOps<T,R> execCrudOps, CrudOps<T,R> undoCrudOps, T execApe) {
        this.execCrudOps = execCrudOps;
        this.undoCrudOps = undoCrudOps;
        this.execApe = execApe;
        this.undoApe = execApe;
    }
    public CrudOpsCommand(CrudOps<T,R> execCrudOps, T execApe, T undoApe) {
        this.execCrudOps = execCrudOps;
        this.undoCrudOps = execCrudOps;
        this.execApe = execApe;
        this.undoApe = undoApe;
    }

    @Override
    public void exec() { execCrudOps.execute(execApe); }
    @Override
    public void undo() { undoCrudOps.execute(undoApe); }
}
