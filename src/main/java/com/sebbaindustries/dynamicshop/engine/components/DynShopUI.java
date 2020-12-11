package com.sebbaindustries.dynamicshop.engine.components;

import com.moandjiezana.toml.Toml;
import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.engine.components.gui.cache.InventoryHolderCache;
import com.sebbaindustries.dynamicshop.engine.components.gui.cache.UICache;
import com.sebbaindustries.dynamicshop.engine.components.maintainer.ComponentManager;
import com.sebbaindustries.dynamicshop.log.PluginLogger;
import com.sebbaindustries.dynamicshop.utils.FileManager;
import org.jetbrains.annotations.NotNull;

public class DynShopUI {

    public DynShopUI() {
        load();
        invHolder = new InventoryHolderCache();
    }

    public void load() {

        ComponentManager.addComponent(this.getClass());

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
            // Toml casting to a cache, works perfect to my knowledge
            return new Toml().read(Core.gCore().fileManager.getFile(file)).to(UICache.class);
        } catch (Exception e) {
            /*
            This produces a big fucking wall of text, but it works okay.
            */
            PluginLogger.logWarn("Error happened while reading " + file.fileName  + " please check if you have setup the plugin correctly.");
            e.printStackTrace();
            PluginLogger.logWarn("Error happened while reading " + file.fileName  + " please check if you have setup the plugin correctly.");

            // Add failed component to the list
            ComponentManager.addComponent(this.getClass(),"Missing files in plugins/DynamicShop/shop/categories/ directory");
            return null;
        }
    }

    public UICache mainPageCache;
    public UICache storePageCache;
    public UICache transactionPageCache;

    public InventoryHolderCache invHolder;

}
