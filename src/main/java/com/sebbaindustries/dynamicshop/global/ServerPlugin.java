package com.sebbaindustries.dynamicshop.global;

import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.engine.DynEngine;
import com.sebbaindustries.dynamicshop.log.PluginLogger;

import java.util.logging.Level;

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
        PluginLogger.logSevere("Plugin core dumped due to illegal access of ServerPlugin class!");
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
        PluginLogger.log("GlobalCore class instance created, initializing plugin modules!");
    }

    /**
     * Initializes global core and setups static link
     */
    public final void initialize() {
        if (Core.globalCore == null) {
            coreDump();
            return;
        }
        Core.gCore().dynEngine = new DynEngine();
        PluginLogger.log("Starting DynEngine instance!");
        Core.gCore().dynEngine.initialize();
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
