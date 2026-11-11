package com.github.michaeldsa.aside.FileTraversal;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;

public class Traversers {
    public static ResolveViewPath resolveViewPath() {
        return new ResolveViewPath();
    }
    public static ResolveViewPath resolveViewPath(MetaPath startingPoint) {
        return new ResolveViewPath(startingPoint);
    }
    public static ResolveViewPath resolveViewPath(ViewPath startingPoint) {
        return new ResolveViewPath(startingPoint);
    }

    public static PurgeViewPathOrphans purgeViewPathOrphans() {
        return new PurgeViewPathOrphans();
    }
    public static PurgeViewPathOrphans purgeViewPathOrphans(ViewPath startingPoint) {
        return new PurgeViewPathOrphans(startingPoint);
    }
    public static PurgeViewPathOrphans purgeViewPathOrphans(MetaPath startingPoint) {
        return new PurgeViewPathOrphans(startingPoint);
    }

    public static DeleteCategory deleteCategory() {
        return new DeleteCategory();
    }
    public static DeleteCategory deleteCategory(MetaPath startingPoint) {
        return new DeleteCategory(startingPoint);
    }
    public static DeleteCategory deleteCategory(ViewPath startingPoint) {
        return new DeleteCategory(startingPoint);
    }

    public static DeleteBibliographyCategory deleteBibliographyCategory() { return new DeleteBibliographyCategory();}
    public static DeleteBibliographyCategory deleteBibligoraphyCategory(MetaPath startingPoint) { return new DeleteBibliographyCategory(startingPoint);}
    public static DeleteBibliographyCategory deleteBibligoraphyCategory(ViewPath startingPoint) { return new DeleteBibliographyCategory(startingPoint);}

    public static DeleteDiscardedCategory deleteDiscardedCategory() { return new DeleteDiscardedCategory(); }
    public static DeleteDiscardedCategory deleteDiscardedCategory(MetaPath startingPoint) { return new DeleteDiscardedCategory(startingPoint);}
    public static DeleteDiscardedCategory deleteDiscardedCategory(ViewPath startingPoint) { return new DeleteDiscardedCategory(startingPoint);}
}
