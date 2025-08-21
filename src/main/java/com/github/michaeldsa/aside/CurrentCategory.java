package com.github.michaeldsa.aside;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;

public enum CurrentCategory {
    INSTANCE;
    private MetaPath currentMetaPath;
    private ViewPath currentViewPath;

    CurrentCategory(){
        RootPaths rp = RootPaths.INSTANCE;
        Config cfg = Config.INSTANCE;
        currentMetaPath = new MetaPath(rp.getMetapath());
        currentViewPath = new ViewPath(rp.getViewpath());
        // If 'last_category' of .config has an entry, reassign above values
    }

    public MetaPath getCurrentMetaPath(){
        return currentMetaPath;
    }
    public ViewPath getCurrentViewPath(){
        return currentViewPath;
    }

    // these setters should be used on exit.
    public void setMetaPath(MetaPath mp) {
        currentMetaPath = mp;
    }
    public void setViewPath(ViewPath vp) {
        currentViewPath = vp;
    }
}
