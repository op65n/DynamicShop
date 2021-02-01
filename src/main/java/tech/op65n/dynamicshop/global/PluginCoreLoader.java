package tech.op65n.dynamicshop.global;

import tech.op65n.dynamicshop.Core;
import tech.op65n.dynamicshop.engine.DynEngine;
import tech.op65n.dynamicshop.engine.components.maintainer.ComponentManager;
import tech.op65n.dynamicshop.utils.Color;
import tech.op65n.dynamicshop.utils.EngineUtils;

/**
 * @author SebbaIndustries
 * @version 1.0
 */
public final class PluginCoreLoader {

    private final Core core;

    public PluginCoreLoader(Core core) {
        this.core = core;
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
    public final void load() {
        Core.globalCore = new GlobalCore(core);
        Core.pluginLogger.log("GlobalCore class instance created, initializing plugin modules!");
    }

    /**
     * Initializes global core and setups static link
     */
    public final void enable() {
        if (Core.globalCore == null) {
            coreDump();
            return;
        }
        Core.gCore().setEngine(
                new DynEngine()
        );

        if (!Core.gCore().getEngine().initialize()) {
            core.getPluginLoader().disablePlugin(core);
            return;
        }
        ComponentManager.getInstance().logAll();

        Core.pluginLogger.log("=============================================");
        Core.pluginLogger.log("-----[           Dynamic Shop          ]-----");
        Core.pluginLogger.log("Version: " + EngineUtils.getVersion());
        Core.engineLogger.log("Engine Codename: " + EngineUtils.getCodename());
        Core.pluginLogger.log("Loaded categories: " + Core.gCore().getEngine().container().getPrioritizedCategoryList().size());
        Core.gCore().getEngine().container().getPrioritizedCategoryList().forEach(
                category -> Core.pluginLogger.log(" - " + category.getFilename() + " with " + category.getItem().size() + " items")
        );
        Core.engineLogger.log(Color.format("Color: &r■&0■&1■&2■&3■&4■&5■&6■&7■&8■&9■&a■&b■&c■&d■&e■&f■"));
        Core.pluginLogger.log("=============================================");

        Core.gCore().getEngine().reload();
    }

    /**
     * Terminates the plugin instance
     */
    public final void disable() {
        if (core == null) {
            coreDump();
            return;
        }
        Core.gCore().terminate();
    }

}
