package com.sebbaindustries.dynamicshop.global;

import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.commands.CommandManager;
import com.sebbaindustries.dynamicshop.engine.DynEngine;
import com.sebbaindustries.dynamicshop.messages.Message;
import com.sebbaindustries.dynamicshop.settings.Configuration;
import com.sebbaindustries.dynamicshop.utils.FileManager;

import java.util.logging.Level;

/**
 * @author SebbaIndustries
 * @version 1.0
 */
public class GlobalCore {

    public final Core core;

    public FileManager fileManager;
    public Configuration configuration;
    public Message message;
    public CommandManager commandManager;
    public DynEngine dynEngine;


    public GlobalCore(Core core) {
        this.core = core;

        this.fileManager = new FileManager(core);
    }

    /**
     * Logs normal #INFO style message to the console
     *
     * @param message String message, color will be default
     */
    @SuppressWarnings("unused")
    public void log(String message) {
        core.getLogger().log(Level.INFO, message);
    }

    /**
     * Logs normal #ERROR style message to the console
     *
     * @param message String message, color will be red
     */
    @SuppressWarnings("unused")
    public void logSevere(String message) {
        core.getLogger().log(Level.SEVERE, message);
    }

    /**
     * Logs normal #WARN style message to the console
     *
     * @param message String message, color will be yellow
     */
    @SuppressWarnings("unused")
    public void logWarn(String message) {
        core.getLogger().log(Level.WARNING, message);
    }

    /**
     * Terminates AdvancedAFK detection engine
     */
    public void terminate() {
        log("Terminating plugin, please wait!");

        log("Plugin was successfully terminated!");
    }

}
