package com.sebbaindustries.dynamicshop.engine.components;

import com.moandjiezana.toml.Toml;
import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.engine.components.gui.cache.InventoryHolderCache;
import com.sebbaindustries.dynamicshop.engine.components.gui.cache.UICache;
import com.sebbaindustries.dynamicshop.log.PluginLogger;
import com.sebbaindustries.dynamicshop.utils.FileManager;
import org.jetbrains.annotations.NotNull;

public class DynShopUI {

    public DynShopUI() {
        load();
        invHolder = new InventoryHolderCache();
    }

    public void load() {
        /*
        main_page.toml
         */
        mainPageCache = getUICacheFromToml(FileManager.PluginFiles.GUI_MAIN_PAGE);

        /*
        store_page.toml
         */
        storePageCache = getUICacheFromToml(FileManager.PluginFiles.GUI_STORE_PAGE);

        /*
        transaction_page.toml
         */
        transactionPageCache = getUICacheFromToml(FileManager.PluginFiles.GUI_TRANSACTION_PAGE);

    }

    private UICache getUICacheFromToml(final @NotNull FileManager.PluginFiles file) {
        try {
            return new Toml().read(Core.gCore().fileManager.getFile(file)).to(UICache.class);
        } catch (Exception e) {
            PluginLogger.logWarn("Error happened while reading " + file.fileName  + " please check if you have setup the plugin correctly.");
            e.printStackTrace();
            PluginLogger.logWarn("Error happened while reading " + file.fileName  + " please check if you have setup the plugin correctly.");
            successfulSetup = false;
            return null;
        }
    }

    public UICache mainPageCache;
    public UICache storePageCache;
    public UICache transactionPageCache;

    public InventoryHolderCache invHolder;

    public boolean successfulSetup = true;

}
