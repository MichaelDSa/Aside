package com.github.michaeldsa.aside;

public enum CurrentCategory {
    INSTANCE;
    private MetaPath currentMetaPath;
    private ViewPath currentViewPath;

    CurrentCategory(){
        RootPaths rp = RootPaths.INSTANCE;
        Config cfg = Config.INSTANCE;
        currentMetaPath = new MetaPath(rp.getMetapath());
        currentViewPath = new ViewPath(rp.getViewpath());


    }
    public MetaPath getCurrentMetaPath(){
        return currentMetaPath;
    }
    public ViewPath getCurrentViewPath(){
        return currentViewPath;
    }
    public void setMetaPath(MetaPath mp) {
        currentMetaPath = mp;
    }
    public void setViewPath(ViewPath vp) {
        currentViewPath = vp;
    }
}
