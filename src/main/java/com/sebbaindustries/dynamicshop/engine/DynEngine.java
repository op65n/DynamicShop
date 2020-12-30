package com.sebbaindustries.dynamicshop.engine;

import com.moandjiezana.toml.Toml;
import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.commands.CommandManager;
import com.sebbaindustries.dynamicshop.engine.container.ShopContainer;
import com.sebbaindustries.dynamicshop.engine.ui.ShopUI;
import com.sebbaindustries.dynamicshop.engine.ui.listeners.InventoryListeners;
import com.sebbaindustries.dynamicshop.messages.Message;
import com.sebbaindustries.dynamicshop.utils.FileManager;

@Engine.Codename("Leptir")
@Engine.Version("1.0.0-R0.1-Alpha")
public class DynEngine implements Engine {

    private ShopContainer container;
    private ShopUI shopUI;
    private long uptime = -1L;

    @Override
    public long uptime() {
        return ((System.currentTimeMillis() - uptime) / 1000);
    }

    @Override
    public void initialize() {
        Core.gCore().setCommandManager(
                new CommandManager(Core.gCore().core)
        );
        Core.gCore().setMessage(
                new Toml().read(Core.gCore().getFileManager().getFile(FileManager.PluginFiles.MESSAGES)).to(Message.class)
        );

        this.container = new ShopContainer();
        this.shopUI = new ShopUI();

        /*
        GUI listeners
         */
        Core.gCore().core.getServer().getPluginManager().registerEvents(new InventoryListeners(), Core.gCore().core);

        uptime = System.currentTimeMillis();
    }

    @Override
    public void terminate() {

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
