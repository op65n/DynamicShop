package com.sebbaindustries.dynamicshop.engine.components;

import com.moandjiezana.toml.Toml;
import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopCategory;
import com.sebbaindustries.dynamicshop.engine.components.shop.implementations.ShopCategoryImpl;
import com.sebbaindustries.dynamicshop.engine.structure.DirectoryStructure;
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
        List<String> files = FileUtils.getFilePathsInCategories();
        if (files == null || files.isEmpty()) {
            PluginLogger.logWarn("Plugin is not configured, maintenance mode is turned on!");
            // TODO: Add maintenance mode.
            return;
        }
        files.forEach(fileName -> {
            if (!fileName.endsWith(".toml")) return;
            try {
                ShopCategoryImpl shopCategoryImplementation = new Toml().read(new File(DirectoryStructure.Directory.CATEGORIES.path + fileName)).to(ShopCategoryImpl.class);
                ShopCategory shopCategory = (ShopCategory) shopCategoryImplementation;
                categoryHashMap.put(shopCategory.getUUID(), shopCategory);
            } catch (Exception e) {
                PluginLogger.logWarn("Error happened while reading " + fileName  + " please check if you have setup the plugin correctly.");
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
