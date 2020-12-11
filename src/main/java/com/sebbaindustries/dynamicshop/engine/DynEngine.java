package com.sebbaindustries.dynamicshop.engine;

import com.moandjiezana.toml.Toml;
import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.commands.CommandManager;
import com.sebbaindustries.dynamicshop.engine.components.DynShopContainer;
import com.sebbaindustries.dynamicshop.engine.components.DynShopUI;
import com.sebbaindustries.dynamicshop.engine.components.gui.listeners.InventoryListeners;
import com.sebbaindustries.dynamicshop.messages.Message;
import com.sebbaindustries.dynamicshop.utils.FileManager;

public class DynEngine {

    public DynShopContainer container;
    public DynShopUI shopUI;

    public void initialize() {
        Core.gCore().commandManager = new CommandManager(Core.gCore().core);
        Core.gCore().message = new Toml().read(Core.gCore().fileManager.getFile(FileManager.PluginFiles.MESSAGES)).to(Message.class);

        this.container = new DynShopContainer();
        this.shopUI = new DynShopUI();

        /*
        GUI listeners
         */
        Core.gCore().core.getServer().getPluginManager().registerEvents(new InventoryListeners(), Core.gCore().core);
    }


}
