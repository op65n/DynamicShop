package com.sebbaindustries.dynamicshop.engine.components;

import com.moandjiezana.toml.Toml;
import com.sebbaindustries.dynamicshop.engine.components.maintainer.ComponentManager;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopCategory;
import com.sebbaindustries.dynamicshop.engine.components.shop.implementations.ShopCategoryImpl;
import com.sebbaindustries.dynamicshop.engine.structure.DirectoryStructure;
import com.sebbaindustries.dynamicshop.log.PluginLogger;
import com.sebbaindustries.dynamicshop.utils.FileUtils;

import java.io.File;
import java.util.*;

public class DynShopContainer {

    public DynShopContainer() {
        load();
    }

    public void load() {
        ComponentManager.addComponent(this.getClass());

        // Check if there are files inside directory, if there are no files terminate the startup sequence
        List<String> files = FileUtils.getCategoryFilePaths();
        if (files == null || files.isEmpty()) {
            PluginLogger.logWarn("Plugin is not configured, maintenance mode is turned on!");
            // Add failed component to the list
            ComponentManager.addComponent(this.getClass(),"Missing files in plugins/DynamicShop/shop/categories/ directory");
            return;
        }

        // Clear the hashmap, necessary for the reloads
        categoryHashMap.clear();

        // We iterate thru all file names, check if they end in .toml just to be sure
        files.forEach(fileName -> {
            // I dont thing any warns here are needed, this may be changed in the future tho.
            if (!fileName.endsWith(".toml")) return;

            // This caches every exception because I'm too lazy to find every one manually.
            // It works okay-ish for error handling, maybe do a cleaner implementation in the future?
            try {
                // Thanks to toml we can load classes to interfaces fairly easily.
                ShopCategory shopCategory = new Toml().read(
                        new File(DirectoryStructure.Directory.CATEGORIES.path + fileName)
                ).to(ShopCategoryImpl.class);

                categoryHashMap.put(shopCategory.getUUID(), shopCategory);
                PluginLogger.log("File " + fileName + " loaded successfully!");

            } catch (Exception e) {
                /*
                This produces a big fucking wall of text, but it works okay.
                 */
                PluginLogger.logWarn("Error happened while reading " + fileName  + " please check if you have setup the plugin correctly.");
                e.printStackTrace();
                PluginLogger.logWarn("Error happened while reading " + fileName  + " please check if you have setup the plugin correctly.");
                // Add failed component to the list
                ComponentManager.addComponent(this.getClass(), "Error in plugins/DynamicShop/shop/categories/ directory inside " + fileName + "file");
            }
        });
    }

    private final HashMap<UUID, ShopCategory> categoryHashMap = new HashMap<>();

    /**
     * Returns sorted list of categories based on priority value, base priority
     * value is -1, sorting works from lowest to highest
     *
     * @return Sorted ArrayList with ShopCategory component
     */
    public List<ShopCategory> getPrioritizedCategoryList() {
        List<ShopCategory> categories = new ArrayList<>(categoryHashMap.values());
        categories.sort(Comparator.comparing(ShopCategory::priority));
        return categories;
    }

}
