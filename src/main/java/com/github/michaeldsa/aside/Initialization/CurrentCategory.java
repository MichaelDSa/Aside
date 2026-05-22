package com.github.michaeldsa.aside.Initialization;

import com.github.michaeldsa.aside.AsidePath.MetaPath;
import com.github.michaeldsa.aside.AsidePath.ViewPath;
import com.github.michaeldsa.aside.AsidePathElement.Category;

public enum CurrentCategory {
    INSTANCE;
    private Category currentCategory;

    CurrentCategory(){
        RootPaths rp = RootPaths.INSTANCE;
        Initializer cfg = Initializer.INSTANCE;
        MetaPath mp = new MetaPath(rp.getMetapath());
        currentCategory = new Category(mp);
        // If 'last_category' of .config has an entry, reassign above values
    }

    public Category getCurrentCategory() { return this.currentCategory; }
    public MetaPath getCurrentMetaPath() { return this.currentCategory.getMetaPath(); }
    public ViewPath getCurrentViewPath() { return this.currentCategory.getViewPath(); }

    // A setter should be used on exit.
    public void setCategory(Category c) { currentCategory = c; }
    public void setCateogry(MetaPath m) { currentCategory = new Category(m); }
    public void setCategory(ViewPath v) { currentCategory = new Category(v); }
}
