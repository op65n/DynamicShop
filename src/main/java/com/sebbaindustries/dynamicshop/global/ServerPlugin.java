package com.sebbaindustries.dynamicshop.global;

import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.engine.DynEngine;
import com.sebbaindustries.dynamicshop.engine.components.maintainer.ComponentManager;
import com.sebbaindustries.dynamicshop.utils.Color;
import com.sebbaindustries.dynamicshop.utils.EngineUtils;

/**
 * @author SebbaIndustries
 * @version 1.0
 */
public class ServerPlugin {

    private Core core;

    public static ServerPlugin INSTANCE() {
        return new ServerPlugin();
    }

    private void coreDump() {
        Core.engineLogger.logSevere("Plugin core dumped due to illegal access of ServerPlugin class!");
        throw new IllegalAccessError("Plugin core dumped due to illegal access of ServerPlugin class!");
    }

    /**
     * Loads Core.class instance
     *
     * @param core Manin plugin class
     */
    public final void load(Core core) {
        this.core = core;
        Core.globalCore = new GlobalCore(core);
        Core.pluginLogger.log("GlobalCore class instance created, initializing plugin modules!");
    }

    /**
     * Initializes global core and setups static link
     */
    public final void initialize() {
        if (Core.globalCore == null) {
            coreDump();
            return;
        }
        Core.gCore().setEngine(
                new DynEngine()
        );

        if (!Core.gCore().getEngine().initialize()) return;
        ComponentManager.getInstance().logAll();

        Core.pluginLogger.log("=============================================");
        Core.pluginLogger.log("-----[           Dynamic Shop          ]-----");
        Core.pluginLogger.log("Version: " + EngineUtils.getVersion());
        Core.engineLogger.log("Engine Codename: " + EngineUtils.getCodename());
        Core.pluginLogger.log("Loaded categories: " + Core.gCore().getEngine().container().getPrioritizedCategoryList().size());
        Core.gCore().getEngine().container().getPrioritizedCategoryList().forEach(
                category -> Core.pluginLogger.log(" - " + category.getFileName() + " with " + category.getItems().size() + " items")
        );
        Core.engineLogger.log(Color.format("Color: &r■&0■&1■&2■&3■&4■&5■&6■&7■&8■&9■&a■&b■&c■&d■&e■&f■"));
        Core.pluginLogger.log("=============================================");
    }

    /**
     * Terminates the plugin instance
     */
    public final void terminate() {
        if (core == null) {
            coreDump();
            return;
        }
        Core.gCore().terminate();
    }

}
