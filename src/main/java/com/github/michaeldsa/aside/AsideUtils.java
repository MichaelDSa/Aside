package com.github.michaeldsa.aside;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// Static utility methods
public class AsideUtils {

    private AsideUtils() {}

    public static <T> MetaPath asMetaPath(T name) {
        return switch (name) {
            case MetaPath mp ->
                    mp;
            case ViewPath vp ->
                    new MetaPath(vp.getPath());
            case Path p ->
                    new MetaPath(p);
            case String s ->
                    new MetaPath(Paths.get(s));
            default -> null;
        };
    }

    // test if both MetaPath and VeiwPath of a Category exist.
    public static boolean isCategory(MetaPath mp) {
        if(mp == null) { return false;}
        ViewPath vp = new ViewPath(mp);
        Path m_path = mp.getPath();
        Path v_path = vp.getPath();
        return Files.exists(m_path) && Files.exists(v_path);
    }
}
