package com.sebbaindustries.dynamicshop.engine;

import com.moandjiezana.toml.Toml;
import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.commands.CommandManager;
import com.sebbaindustries.dynamicshop.database.DBSetup;
import com.sebbaindustries.dynamicshop.database.DataSource;
import com.sebbaindustries.dynamicshop.engine.cache.LocalCache;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopCategory;
import com.sebbaindustries.dynamicshop.engine.container.ShopContainer;
import com.sebbaindustries.dynamicshop.engine.task.Task;
import com.sebbaindustries.dynamicshop.engine.ui.ShopUI;
import com.sebbaindustries.dynamicshop.engine.ui.listeners.InventoryListeners;
import com.sebbaindustries.dynamicshop.messages.Message;
import com.sebbaindustries.dynamicshop.settings.Configuration;
import com.sebbaindustries.dynamicshop.utils.FileManager;
import com.sebbaindustries.dynamicshop.utils.FileUtils;
import com.sebbaindustries.dynamicshop.utils.ObjectUtils;

import java.util.*;

@Engine.Codename("Leptir")
@Engine.Version("1.0.0-R0.1-Alpha")
public class DynEngine implements Engine {

    private final LocalCache _lCache = new LocalCache();

    private ShopContainer container;
    private ShopUI shopUI;
    private DBSetup dbs;

    private Configuration configuration;
    private long uptime = -1L;

    @Override
    public long uptime() {
        return ((System.currentTimeMillis() - uptime) / 1000);
    }

    @Override
    public boolean initialize() {
        initLCache();
        if (_lCache.getStartupInfo().isInitStartup()) {
            Core.engineLogger.logWarn("DynamicShop requires initial configuration before you cen use it!");
            Core.engineLogger.logWarn("Please visit https://op65n.tech to read the wiki!");
            Core.engineLogger.logWarn("DynamicShop requires initial configuration before you cen use it!");
            _lCache.getStartupInfo().setInitStartup(false);
            return false;
        }

        this.configuration = new Toml().read(Core.gCore().getFileManager().getFile(FileManager.PluginFiles.CONFIGURATION)).to(Configuration.class);
        if (!DataSource.setup(configuration.getDatabase())) {
            return false;
        }
        _lCache.getStartupInfo().setDbReady(true);

        dbs = new DBSetup();

        this.container = new ShopContainer();
        Task.async(() -> {
            dbs.createTables();
            syncLCache();
        });

        this.shopUI = new ShopUI();


        uptime = System.currentTimeMillis();

        Core.gCore().setCommandManager(
                new CommandManager(Core.gCore().core)
        );
        Core.gCore().setMessage(
                new Toml().read(Core.gCore().getFileManager().getFile(FileManager.PluginFiles.MESSAGES)).to(Message.class)
        );
        /*
        GUI listeners
         */
        Core.gCore().core.getServer().getPluginManager().registerEvents(new InventoryListeners(), Core.gCore().core);

        return true;
    }

    private void initLCache() {
        if (!FileUtils.fileExists(".cache/startup_info.json")) {
            ObjectUtils.saveGsonFile(".cache/startup_info.json", _lCache.getStartupInfo());
        }
        _lCache.setStartupInfo(ObjectUtils.getGsonFile(".cache/startup_info.json", LocalCache.StartupInfo.class));

        if (!FileUtils.fileExists(".cache/category_file_info.json")) {
            ObjectUtils.saveGsonFile(".cache/category_file_info.json", _lCache.getCategoryFileInfo());
        }
        _lCache.setCategoryFileInfo(ObjectUtils.getGsonFile(".cache/category_file_info.json", LocalCache.CategoryFileInfo.class));
    }

    private void syncLCache() {
        Collection<ShopCategory> cached = _lCache.getCategoryFileInfo().getCategories();
        Collection<ShopCategory> loaded = container.getPrioritizedCategoryList();

        Collection<ShopCategory> differentCached = new HashSet<>(loaded);
        Collection<ShopCategory> differentLoaded = new HashSet<>(cached);

        cached.forEach(cachedEntry -> loaded.forEach(loadedEntry -> {
            if (loadedEntry.getFileName().equals(cachedEntry.getFileName())) {
                differentCached.remove(loadedEntry);
                differentLoaded.remove(cachedEntry);
            }
        }));


        Core.engineLogger.logWarn("Different cached");
        differentCached.forEach(category -> System.out.println(category.getFileName()));
        dbs.createCategories(new ArrayList<>(differentCached));


        Core.engineLogger.logWarn("Different loaded");
        differentLoaded.forEach(loadedCategoryName -> System.out.println(loadedCategoryName.getFileName()));

        _lCache.getCategoryFileInfo().setCategories(new ArrayList<>(container.getPrioritizedCategoryList()));

    }

    @Override
    public void terminate() {
        ObjectUtils.saveGsonFile(".cache/startup_info.json", _lCache.getStartupInfo());
        ObjectUtils.saveGsonFile(".cache/category_file_info.json", _lCache.getCategoryFileInfo());
    }

    @Override
    public void reload() {

    }

    @Override
    public ShopContainer container() {
        return container;
    }

    @Override
    public ShopUI ui() {
        return shopUI;
    }


}
