package com.sebbaindustries.dynamicshop.utils;

import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.engine.components.ShopCategory;
import com.sebbaindustries.dynamicshop.engine.components.ShopItem;
import org.bukkit.Material;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author SebbaIndustries
 * @version 1.0
 */
public final class FileManager {

    /*
    configuration.properties
     */
    public File configuration;
    /*
    messages.properties
    */
    public File messages;
    /*
    shop_configuration.js
    */
    public File shopConfig;

    public FileManager(Core core) {
        generateConfiguration(core);
        generateMessages(core);
        generateBaseDirs(core);
        generateShopConfig(core);
        //generateREADME(core);
        generateGUIJson(core);
    }

    private boolean checkIfDirExists(Core core, String dirName) {
        return new File(core.getDataFolder() + "/" + dirName + "/").isDirectory();
    }

    public void generateCatDirs(Core core) {
        List<?> categories = ObjectUtils.getGsonFile("shop/shop_configuration", List.class);
        if (categories == null) return;

        categories.forEach(cat -> {
            ShopCategory category = ObjectUtils.getClassFromGson(cat, ShopCategory.class);
            Core.gCore().dynEngine.categories.add(category);
            if (!checkIfDirExists(core,category.getName())) {
                try {
                    Files.createDirectory(Paths.get(core.getDataFolder() + "/shop/categories/" + category.getName() + "/"));
                    ObjectUtils.saveGsonFile("shop/categories/" + category.getName() + "/dirt", new ShopItem(0.9, 0.4, Material.DIRT, "&6Dirt"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void generateBaseDirs(Core core) {
        try {
            if (!checkIfDirExists(core,"shop")) {
                Files.createDirectory(Paths.get(core.getDataFolder() + "/shop/"));
            }
            if (!checkIfDirExists(core,"shop/categories")) {
                Files.createDirectory(Paths.get(core.getDataFolder() + "/shop/categories/"));
            }
            if (!checkIfDirExists(core,"shop/statistics")) {
                Files.createDirectory(Paths.get(core.getDataFolder() + "/shop/statistics/"));
            }
            if (!checkIfDirExists(core,"shop/gui")) {
                Files.createDirectory(Paths.get(core.getDataFolder() + "/shop/gui/"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateConfiguration(Core core) {
        if (configuration == null) {
            configuration = new File(core.getDataFolder(), "configuration.properties");
        }
        if (!configuration.exists()) {
            core.saveResource("configuration.properties", false);
        }
    }

    /**
     * Generates messages.properties File
     */
    public final void generateMessages(Core core) {
        if (messages == null) {
            messages = new File(core.getDataFolder(), "messages.properties");
        }
        if (!messages.exists()) {
            core.saveResource("messages.properties", false);
        }
    }

    /**
     * Generates shop_configuration.js File
     */
    public final void generateShopConfig(Core core) {
        if (shopConfig == null) {
            shopConfig = new File(core.getDataFolder(), "shop/shop_configuration.js");
        }
        if (!shopConfig.exists()) {
            core.saveResource("shop/shop_configuration.js", false);
        }
    }

    /**
     * Generates main_page.js File
     */
    public final void generateGUIJson(Core core) {
        File main = new File(core.getDataFolder(), "shop/gui/main_page.js");
        File store = new File(core.getDataFolder(), "shop/gui/store_page.js");
        File transaction = new File(core.getDataFolder(), "shop/gui/transaction_page.js");

        if (!main.exists()) {
            core.saveResource("shop/gui/main_page.js", false);
        }
        if (!store.exists()) {
            core.saveResource("shop/gui/store_page.js", false);
        }
        if (!transaction.exists()) {
            core.saveResource("shop/gui/transaction_page.js", false);
        }
    }

    /**
     * Generates README.md File
     */
    public final void generateREADME(Core core) {
        File README = new File(core.getDataFolder(), "README.md");

        if (!README.exists()) {
            core.saveResource("README.md", false);
        }
    }

}
