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
}
