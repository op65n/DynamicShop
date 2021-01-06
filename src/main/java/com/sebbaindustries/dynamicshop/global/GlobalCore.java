package com.sebbaindustries.dynamicshop.global;

import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.commands.CommandManager;
import com.sebbaindustries.dynamicshop.engine.Engine;
import com.sebbaindustries.dynamicshop.engine.structure.DirectoryStructure;
import com.sebbaindustries.dynamicshop.messages.Message;
import com.sebbaindustries.dynamicshop.utils.FileManager;
import lombok.Getter;
import lombok.Setter;

/**
 * @author SebbaIndustries
 * @version 1.0
 */
@Getter
@Setter
public class GlobalCore {

    public final Core core;

    private FileManager fileManager;
    //public Configuration configuration;
    private Message message;
    private CommandManager commandManager;
    private Engine engine;
    private DirectoryStructure directoryStructure;


    public GlobalCore(Core core) {
        this.core = core;
        this.directoryStructure = new DirectoryStructure();
        this.fileManager = new FileManager(core);
    }

    /**
     * Terminates AdvancedAFK detection engine
     */
    public void terminate() {
        Core.engineLogger.log("Terminating plugin, please wait!");
        engine.terminate();
        Core.engineLogger.log("Plugin was terminated!");
    }

}
