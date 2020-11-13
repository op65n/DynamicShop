package com.sebbaindustries.dynamicshop.engine;

import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.engine.components.ShopCategory;
import com.sebbaindustries.dynamicshop.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

public class DynEngine {

    private final Core core;

    public DynEngine(Core core) {
        this.core = core;
    }

    public List<ShopCategory> categories = new ArrayList<>();

    public void initialize() {
        Core.gCore().fileManager.generateCatDirs(core);
        for (ShopCategory category : categories) {
            FileUtils.cdCat().saveGsonFile(category.getName() + "/item_file", "Test");
        }
        FileUtils.cdGUI().saveGsonFile("main", "Test");
        FileUtils.cdGUI().saveGsonFile("store_page", "Test");
        FileUtils.cdGUI().saveGsonFile("transaction_page", "Test");
    }
}
