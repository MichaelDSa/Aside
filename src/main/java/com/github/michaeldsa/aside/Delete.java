package com.github.michaeldsa.aside;

public class Delete {

    private Delete() {}

    public static <T> MetaPath inThisCategory(Deleter<MetaPath> deleter, T category) {
        MetaPath mp = AsideUtils.asMetaPath(category);
        return deleter.delete(mp);
    }
}
