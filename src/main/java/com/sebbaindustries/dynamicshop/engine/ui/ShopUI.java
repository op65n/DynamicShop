package com.sebbaindustries.dynamicshop.engine.ui;

import com.moandjiezana.toml.Toml;
import com.rits.cloning.Cloner;
import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.engine.ui.cache.BuyPageUICache;
import com.sebbaindustries.dynamicshop.engine.ui.cache.MainPageUICache;
import com.sebbaindustries.dynamicshop.engine.ui.cache.SellPageUICache;
import com.sebbaindustries.dynamicshop.engine.ui.cache.StorePageUICache;
import com.sebbaindustries.dynamicshop.engine.components.maintainer.ComponentManager;
import com.sebbaindustries.dynamicshop.utils.FileManager;
import org.jetbrains.annotations.NotNull;

public class ShopUI {

    public ShopUI() {
        load();
    }

    public void load() {

        ComponentManager.addComponent(this.getClass());

        /*
        main_page.toml
         */
        mainPageCache = getUICacheFromToml(FileManager.PluginFiles.GUI_MAIN_PAGE, MainPageUICache.class);

        /*
        store_page.toml
         */
        storePageCache = getUICacheFromToml(FileManager.PluginFiles.GUI_STORE_PAGE, StorePageUICache.class);

        /*
        buy_page.toml
         */
        buyPageCache = getUICacheFromToml(FileManager.PluginFiles.GUI_BUY_PAGE, BuyPageUICache.class);

        /*
        sell_page.toml
         */
        sellPageCache = getUICacheFromToml(FileManager.PluginFiles.GUI_SELL_PAGE, SellPageUICache.class);

    }

    private <T> T getUICacheFromToml(final @NotNull FileManager.PluginFiles file, Class<T> tClass) {
        try {
            // Toml casting to a cache, works perfect to my knowledge
            return new Toml().read(Core.gCore().getFileManager().getFile(file)).to(tClass);
        } catch (Exception e) {
            /*
            This produces a big fucking wall of text, but it works okay.
            */
            Core.engineLogger.logWarn("Error happened while reading " + file.fileName + " please check if you have setup the plugin correctly.");
            e.printStackTrace();
            Core.engineLogger.logWarn("Error happened while reading " + file.fileName + " please check if you have setup the plugin correctly.");

            // Add failed component to the list
            ComponentManager.addComponent(this.getClass(), "Missing files in plugins/DynamicShop/shop/categories/ directory");
            return null;
        }
    }

    private MainPageUICache mainPageCache;
    private StorePageUICache storePageCache;
    private BuyPageUICache buyPageCache;
    private SellPageUICache sellPageCache;

    private final Cloner cloner = new Cloner();

    public MainPageUICache getMainPageCache() {
        // Java is retarded so we are forced to to this shit...
        return cloner.deepClone(mainPageCache);
    }

    public StorePageUICache getStorePageCache() {
        // Java is retarded so we are forced to to this shit...
        return cloner.deepClone(storePageCache);
    }

    public BuyPageUICache getBuyPageCache() {
        // Java is retarded so we are forced to to this shit...
        return cloner.deepClone(buyPageCache);
    }

    public SellPageUICache getSellPageCache() {
        // Java is retarded so we are forced to to this shit...
        return cloner.deepClone(sellPageCache);
    }

}
