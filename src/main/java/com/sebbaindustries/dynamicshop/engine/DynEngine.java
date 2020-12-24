package com.sebbaindustries.dynamicshop.engine;

import com.moandjiezana.toml.Toml;
import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.commands.CommandManager;
import com.sebbaindustries.dynamicshop.engine.components.DynShopContainer;
import com.sebbaindustries.dynamicshop.engine.components.DynShopUI;
import com.sebbaindustries.dynamicshop.engine.components.gui.listeners.InventoryListeners;
import com.sebbaindustries.dynamicshop.messages.Message;
import com.sebbaindustries.dynamicshop.utils.FileManager;
import lombok.Getter;


public class DynEngine implements Engine {

    @Getter
    private DynShopContainer container;
    @Getter
    private DynShopUI shopUI;

    @Override
    public String codename() {
        return "Leptir";
    }

    @Override
    public String version() {
        return "1.0.0-R0.1";
    }

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

        this.container = new DynShopContainer();
        this.shopUI = new DynShopUI();

        /*
        GUI listeners
         */
        Core.gCore().core.getServer().getPluginManager().registerEvents(new InventoryListeners(), Core.gCore().core);

        engine = this;
        uptime = System.currentTimeMillis();
    }

    @Override
    public void terminate() {

    }

    @Override
    public void reload() {

    }

    private static long uptime = -1L;

    private static DynEngine engine;

    @Override
    public DynEngine instance() {
        return engine;
    }


}
