package com.sebbaindustries.dynamicshop.engine;

import com.moandjiezana.toml.Toml;
import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.commands.CommandManager;
import com.sebbaindustries.dynamicshop.database.DBSetup;
import com.sebbaindustries.dynamicshop.database.DataSource;
import com.sebbaindustries.dynamicshop.engine.cache.LocalCache;
import com.sebbaindustries.dynamicshop.engine.container.ShopContainer;
import com.sebbaindustries.dynamicshop.engine.ui.ShopUI;
import com.sebbaindustries.dynamicshop.engine.ui.listeners.InventoryListeners;
import com.sebbaindustries.dynamicshop.messages.Message;
import com.sebbaindustries.dynamicshop.settings.Configuration;
import com.sebbaindustries.dynamicshop.utils.FileManager;
import com.sebbaindustries.dynamicshop.utils.FileUtils;
import com.sebbaindustries.dynamicshop.utils.ObjectUtils;

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
        initLCache();
        System.out.println(ObjectUtils.deserializeObjectToString(_lCache));
        if (_lCache.getStartupInfo().isInitStartup()) {
            Core.engineLogger.logWarn("DynamicShop requires initial configuration before you cen use it!");
            Core.engineLogger.logWarn("Please visit https://op65n.tech to read the wiki!");
            Core.engineLogger.logWarn("DynamicShop requires initial configuration before you cen use it!");
            _lCache.getStartupInfo().setInitStartup(false);
            Core.gCore().core.getPluginLoader().disablePlugin(Core.gCore().core);
            return false;
        }

        Core.gCore().setCommandManager(
                new CommandManager(Core.gCore().core)
        );
        Core.gCore().setMessage(
                new Toml().read(Core.gCore().getFileManager().getFile(FileManager.PluginFiles.MESSAGES)).to(Message.class)
        );
        this.configuration = new Toml().read(Core.gCore().getFileManager().getFile(FileManager.PluginFiles.CONFIGURATION)).to(Configuration.class);

        this.container = new ShopContainer();
        this.shopUI = new ShopUI();

        /*
        GUI listeners
         */
        Core.gCore().core.getServer().getPluginManager().registerEvents(new InventoryListeners(), Core.gCore().core);

        uptime = System.currentTimeMillis();

        DataSource.setup(configuration.getDatabase());

        DBSetup dbs = new DBSetup();
        dbs.create();

        return true;
    }

    private void initLCache() {
        if (!FileUtils.fileExists(".cache/startup_info.json")) {
            System.out.println("Creating cache");
            ObjectUtils.saveGsonFile(".cache/startup_info.json", _lCache.getStartupInfo());
        }
        _lCache.setStartupInfo(ObjectUtils.getGsonFile(".cache/startup_info.json", LocalCache.StartupInfo.class));
    }

    @Override
    public void terminate() {
        ObjectUtils.saveGsonFile(".cache/startup_info.json", _lCache.getStartupInfo());
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
