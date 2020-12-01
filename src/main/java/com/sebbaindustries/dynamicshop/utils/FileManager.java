package com.sebbaindustries.dynamicshop.utils;

import com.sebbaindustries.dynamicshop.Core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author SebbaIndustries
 * @version 1.0
 */
public final class FileManager {

    /*
    configuration.toml
     */
    public File configuration;
    /*
    messages.toml
    */
    public File messages;
    /*
    shop_configuration.toml
    */
    public File shopConfig;

    public FileManager(Core core) {
        generateConfiguration(core);
        //generateMessages(core);
        generateBaseDirs(core);
        generateShopConfig(core);
        //generateREADME(core);
        generateGUIJson(core);
    }

    private boolean checkIfDirExists(Core core, String dirName) {
        return new File(core.getDataFolder() + "/" + dirName + "/").isDirectory();
    }

    public void generateBaseDirs(Core core) {
        try {
            if (!checkIfDirExists(core, "shop")) {
                Files.createDirectory(Paths.get(core.getDataFolder() + "/shop/"));
            }
            if (!checkIfDirExists(core, "shop/categories")) {
                Files.createDirectory(Paths.get(core.getDataFolder() + "/shop/categories/"));
            }
            if (!checkIfDirExists(core, "shop/statistics")) {
                Files.createDirectory(Paths.get(core.getDataFolder() + "/shop/statistics/"));
            }
            if (!checkIfDirExists(core, "shop/gui")) {
                Files.createDirectory(Paths.get(core.getDataFolder() + "/shop/gui/"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateConfiguration(Core core) {
        if (configuration == null) {
            configuration = new File(core.getDataFolder(), "configuration.toml");
        }
        if (!configuration.exists()) {
            core.saveResource("configuration.toml", false);
        }
    }

    /**
     * Generates messages.toml File
     */
    public final void generateMessages(Core core) {
        if (messages == null) {
            messages = new File(core.getDataFolder(), "messages.toml");
        }
        if (!messages.exists()) {
            core.saveResource("messages.toml", false);
        }
    }

    /**
     * Generates shop_configuration.toml File
     */
    public final void generateShopConfig(Core core) {
        if (shopConfig == null) {
            shopConfig = new File(core.getDataFolder(), "shop/shop_configuration.toml");
        }
        if (!shopConfig.exists()) {
            core.saveResource("shop/shop_configuration.toml", false);
        }
    }

    /**
     * Generates main_page.toml File
     */
    public final void generateGUIJson(Core core) {
        File main = new File(core.getDataFolder(), "shop/gui/main_page.toml");
        File store = new File(core.getDataFolder(), "shop/gui/store_page.toml");
        File transaction = new File(core.getDataFolder(), "shop/gui/transaction_page.toml");

        if (!main.exists()) {
            core.saveResource("shop/gui/main_page.toml", false);
        }
        if (!store.exists()) {
            core.saveResource("shop/gui/store_page.toml", false);
        }
        if (!transaction.exists()) {
            core.saveResource("shop/gui/transaction_page.toml", false);
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
