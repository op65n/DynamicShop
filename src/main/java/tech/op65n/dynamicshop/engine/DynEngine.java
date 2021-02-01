package tech.op65n.dynamicshop.engine;

import com.moandjiezana.toml.Toml;
import tech.op65n.dynamicshop.Core;
import tech.op65n.dynamicshop.commands.CommandManager;
import tech.op65n.dynamicshop.database.DataSource;
import tech.op65n.dynamicshop.engine.cache.DataSourceCache;
import tech.op65n.dynamicshop.engine.cache.LocalCache;
import tech.op65n.dynamicshop.engine.container.ShopContainer;
import tech.op65n.dynamicshop.engine.task.Task;
import tech.op65n.dynamicshop.engine.ui.ShopUI;
import tech.op65n.dynamicshop.engine.ui.listeners.InventoryListeners;
import tech.op65n.dynamicshop.messages.Message;
import tech.op65n.dynamicshop.settings.Configuration;
import tech.op65n.dynamicshop.utils.FileManager;
import tech.op65n.dynamicshop.utils.ObjectUtils;

@Engine.Codename("Leptir")
@Engine.Version("1.0.0-R0.1-Alpha")
public class DynEngine implements Engine {

    private final LocalCache _lCache = new LocalCache();

    private ShopContainer container;
    private ShopUI shopUI;

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

        Core.gCore().setConfiguration(new Toml().read(Core.gCore().getFileManager().getFile(FileManager.PluginFiles.CONFIGURATION)).to(Configuration.class));

        if (!DataSource.setup(Core.gCore().getConfiguration().getDatabase())) {
            return false;
        }

        this.container = new ShopContainer();
        this.shopUI = new ShopUI();

        uptime = System.currentTimeMillis();

        Core.gCore().setCommandManager(new CommandManager(Core.gCore().core));
        Core.gCore().setMessage(new Toml().read(Core.gCore().getFileManager().getFile(FileManager.PluginFiles.MESSAGES)).to(Message.class));
        Core.gCore().core.getServer().getPluginManager().registerEvents(new InventoryListeners(), Core.gCore().core);

        return true;
    }

    @Override
    public void terminate() {
        ObjectUtils.saveGsonFile(".cache/startup_info.json", _lCache.getStartupInfo());
    }

    @Override
    public void reload() {
        Task.async(() -> Core.gCore().setShopCache(new DataSourceCache()));
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
