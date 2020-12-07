package com.sebbaindustries.dynamicshop.engine.components;

import com.moandjiezana.toml.Toml;
import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.engine.components.gui.cache.UICache;
import com.sebbaindustries.dynamicshop.log.PluginLogger;
import com.sebbaindustries.dynamicshop.utils.FileManager;

public class DynShopUI {

    public DynShopUI() {
        load();
    }

    public void load() {
        /*
        main_page.toml
         */
        try {
            mainPageCache = new Toml().read(Core.gCore().fileManager.getFile(FileManager.PluginFiles.GUI_MAIN_PAGE)).to(UICache.class);
        } catch (Exception e) {
            PluginLogger.logWarn("Error happened while reading " + FileManager.PluginFiles.GUI_MAIN_PAGE.fileName  + " please check if you have setup the plugin correctly.");
            e.printStackTrace();
            PluginLogger.logWarn("Error happened while reading " + FileManager.PluginFiles.GUI_MAIN_PAGE.fileName  + " please check if you have setup the plugin correctly.");
            successfulSetup = false;
        }

        /*
        store_page.toml
         */
        try {
            storePageCache = new Toml().read(Core.gCore().fileManager.getFile(FileManager.PluginFiles.GUI_STORE_PAGE)).to(UICache.class);
        } catch (Exception e) {
            PluginLogger.logWarn("Error happened while reading " + FileManager.PluginFiles.GUI_STORE_PAGE.fileName  + " please check if you have setup the plugin correctly.");
            e.printStackTrace();
            PluginLogger.logWarn("Error happened while reading " + FileManager.PluginFiles.GUI_STORE_PAGE.fileName  + " please check if you have setup the plugin correctly.");
            successfulSetup = false;
        }

        /*
        transaction_page.toml
         */
        try {
            transactionPageCache = new Toml().read(Core.gCore().fileManager.getFile(FileManager.PluginFiles.GUI_TRANSACTION_PAGE)).to(UICache.class);
        } catch (Exception e) {
            PluginLogger.logWarn("Error happened while reading " + FileManager.PluginFiles.GUI_TRANSACTION_PAGE.fileName  + " please check if you have setup the plugin correctly.");
            e.printStackTrace();
            PluginLogger.logWarn("Error happened while reading " + FileManager.PluginFiles.GUI_TRANSACTION_PAGE.fileName  + " please check if you have setup the plugin correctly.");
            successfulSetup = false;
        }
    }

    public UICache mainPageCache;
    public UICache storePageCache;
    public UICache transactionPageCache;

    public boolean successfulSetup = true;

}
