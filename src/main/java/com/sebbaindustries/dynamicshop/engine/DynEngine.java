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

    private Configuration configuration;
    private long uptime = -1L;

    @Override
    public long uptime() {
        return ((System.currentTimeMillis() - uptime) / 1000);
    }

    @Override
    public boolean initialize() {
        _lCache.init();
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

        this.container = new ShopContainer();

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

    @Override
    public void terminate() {
        ObjectUtils.saveGsonFile(".cache/startup_info.json", _lCache.getStartupInfo());
        ObjectUtils.saveGsonFile(".cache/category_file_info.json", _lCache.getCategoryFileInfo());
        ObjectUtils.saveGsonFile(".cache/id_info.json", _lCache.getIdInfo());
    }

    @Override
    public void reload() {
        Task.async(() -> {
            if (!_lCache.getStartupInfo().isDbReady()) return;
            DBSetup.createTables();
            _lCache.syncLCache();
        });
    }

    @Override
    public ShopContainer container() {
        return container;
    }

    @Override
    public LocalCache _LCACHE() {
        return _lCache;
    }

    @Override
    public ShopUI ui() {
        return shopUI;
    }


}
