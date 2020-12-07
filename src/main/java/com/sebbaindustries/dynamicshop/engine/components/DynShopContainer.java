package com.sebbaindustries.dynamicshop.engine.components;

import com.moandjiezana.toml.Toml;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopCategory;
import com.sebbaindustries.dynamicshop.engine.components.shop.implementations.ShopCategoryImpl;
import com.sebbaindustries.dynamicshop.engine.structure.DirectoryStructure;
import com.sebbaindustries.dynamicshop.log.PluginLogger;
import com.sebbaindustries.dynamicshop.utils.FileUtils;
import com.sebbaindustries.dynamicshop.utils.ObjectUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class DynShopContainer {

    public DynShopContainer() {
        load();
    }

    public void load() {
        List<String> files = FileUtils.getFilePathsInCategories();
        if (files == null || files.isEmpty()) {
            PluginLogger.logWarn("Plugin is not configured, maintenance mode is turned on!");
            successfulSetup = false;
            return;
        }
        categoryHashMap.clear();
        files.forEach(fileName -> {
            if (!fileName.endsWith(".toml")) return;
            try {
                PluginLogger.log("Loading " + fileName + "!");
                ShopCategory shopCategory = (ShopCategory) new Toml().read(new File(DirectoryStructure.Directory.CATEGORIES.path + fileName)).to(ShopCategoryImpl.class);
                categoryHashMap.put(shopCategory.getUUID(), shopCategory);
            } catch (Exception e) {
                PluginLogger.logWarn("Error happened while reading " + fileName  + " please check if you have setup the plugin correctly.");
                e.printStackTrace();
                PluginLogger.logWarn("Error happened while reading " + fileName  + " please check if you have setup the plugin correctly.");
                successfulSetup = false;
            }
        });
    }

    public boolean successfulSetup = true;

    public HashMap<UUID, ShopCategory> categoryHashMap = new HashMap<>();

    public void addCategory(ShopCategory category) {
        categoryHashMap.put(category.getUUID(), category);
    }

    public void dataDump() {
        System.out.println(ObjectUtils.deserializeObjectToString(this));
    }

}
