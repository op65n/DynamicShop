package com.sebbaindustries.dynamicshop.global;

import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.commands.CommandManager;
import com.sebbaindustries.dynamicshop.engine.DynEngine;
import com.sebbaindustries.dynamicshop.engine.structure.DirectoryStructure;
import com.sebbaindustries.dynamicshop.log.PluginLogger;
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
    //public Configuration configuration;
    public Message message;
    public CommandManager commandManager;
    public DynEngine dynEngine;
    public DirectoryStructure directoryStructure;


    public GlobalCore(Core core) {
        this.core = core;
        this.directoryStructure = new DirectoryStructure();
        this.fileManager = new FileManager(core);
        this.message = new Message();
    }

    /**
     * Terminates AdvancedAFK detection engine
     */
    public void terminate() {
        PluginLogger.log("Terminating plugin, please wait!");

        PluginLogger.log("Plugin was successfully terminated!");
    }

}
