package com.sebbaindustries.dynamicshop.engine.components;

import com.moandjiezana.toml.Toml;
import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopCategory;
import com.sebbaindustries.dynamicshop.engine.components.shop.implementations.ShopCategoryImpl;
import com.sebbaindustries.dynamicshop.log.PluginLogger;
import com.sebbaindustries.dynamicshop.messages.Message;
import com.sebbaindustries.dynamicshop.utils.FileManager;
import com.sebbaindustries.dynamicshop.utils.FileUtils;
import com.sebbaindustries.dynamicshop.utils.ObjectUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class DynShopContainer {

    public DynShopContainer() {
        List<String> paths = FileUtils.getFilePathsInCategories();
        if (paths == null) {
            PluginLogger.logWarn("Plugin is not configured, maintenance mode is turned on!");
            // TODO: Add maintenance mode.
            return;
        }
        paths.forEach(path -> {
            if (!path.endsWith(".toml")) return;
            try {
                ShopCategoryImpl shopCategoryImplementation = new Toml().read(new File(path)).to(ShopCategoryImpl.class);
                ShopCategory shopCategory = (ShopCategory) shopCategoryImplementation;
                categoryHashMap.put(shopCategory.getUUID(), shopCategory);
            } catch (Exception e) {
                PluginLogger.logWarn("Error happened while reading " + path  + " please check if you have setup the plugin correctly.");
            }

        });
    }

    public HashMap<String, ShopCategory> categoryHashMap = new HashMap<>();

    public void addCategory(ShopCategory category) {
        categoryHashMap.put(category.getUUID(), category);
    }

    public void dump() {
        System.out.println(ObjectUtils.deserializeObjectToString(this));
    }

}
