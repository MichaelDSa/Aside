package com.github.michaeldsa.aside.Ops;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePathElement.AsidePathElement;
import com.github.michaeldsa.aside.AsidePathElement.Note;
import com.github.michaeldsa.aside.FileTraversal.Traversers;
import com.github.michaeldsa.aside.PropertiesUtil.PropUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public enum Update implements CrudOps<AsidePathElement,AsidePathElement> {

    NOTE (ape -> {
        Note note;
        Path currentStatePath;
        Path previousStatePath;
        Path parentCategoryPath;
        boolean updatePath;
        boolean categoryExists;

        if (ape instanceof Note) {
            note = new Note((Note) ape);
        } else {
            System.err.println("Update.NOTE: can only operate on Note type. Type provided: " + ape.getClass().getSimpleName());
            return ape;
        }

        // Operation fails if previousState is null, or if fields of this state and previousState have the same values
        if (note.getUpdateStatus()) {
            currentStatePath = note.getMetaPath().getPath();
            previousStatePath = note.getInstanceWithSwappedStates().getMetaPath().getPath();
            parentCategoryPath = note.getParentCategory().getMetaPath().getPath();
            updatePath = !currentStatePath.equals(previousStatePath);
            categoryExists = Files.exists(parentCategoryPath);
        } else {
            String filename = note.getMetaPath().getPath().getFileName().toString();
            System.err.println("Update.NOTE: Note " + filename + " has not been updated. \nThe update status of  " + note.getMetaPath() + "  is  " + note.getUpdateStatus());
            return ape;
        }

        // previousState must have a metaPath of a file that exists.
        if (Files.notExists(previousStatePath)) {
            System.err.println("Update.NOTE: Note does not exist: \ncurrent state: " + currentStatePath + "\nprevious state: "  + previousStatePath);
            return ape;
        }

        // write new data if category exists
        if (categoryExists) {

            PropUtils.writeNote(note);

        } else {
            System.err.println("Update.NOTE: Parent Category does not exist: " + parentCategoryPath);
            return ape;
        }

        // delete old data if necessary:
        if (updatePath) {
            try {
                Traversers.deleteCategory(new MetaPath(previousStatePath)).traverse_and_purgeViewPathOrphans();
            } catch (IOException e ) {
                System.err.println("Update.NOTE: IOException while deleting " + note.getMetaPath() + "\n" + e.getMessage());
                return ape;
            }
        }
        return ape;
    });


    // class boilerplate
    private final CrudOps<AsidePathElement,AsidePathElement> fops;

    Update(CrudOps<AsidePathElement,AsidePathElement> fops) {
        this.fops = fops;
    }

    @Override
    public AsidePathElement execute(AsidePathElement ape) {
        return fops.execute(ape);
    }

    public static void updateViewPath(){
        /*
        recursively update the ViewPath starting from viewpath root,
        ensuring that all qualifying directories and files in MetaPath
        have a corresponding ViewPath dir/file, and any ViewPath 
        dir/file for which there is no corresponding MetaPath element
        is deleted. The goal is to match the MetaPath tree structure.
        However, unqualified dir/files may exist in the MetaPath 
        without being recognized by Aside
         */
        try {
            Traversers.resolveViewPath().traverse();
            Traversers.purgeViewPathOrphans().traverse();
        } catch (IOException e) {
            System.err.println("IOException in Update.updateViewPath().\n" + e.getMessage());
        }
    }
}
