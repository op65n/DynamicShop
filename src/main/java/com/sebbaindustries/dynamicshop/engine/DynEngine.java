package com.sebbaindustries.dynamicshop.engine;

import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.commands.CommandManager;
import com.sebbaindustries.dynamicshop.messages.Message;
import com.sebbaindustries.dynamicshop.utils.ObjectUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DynEngine {

    public void initialize() {
        Core.gCore().commandManager = new CommandManager(Core.gCore().core);

        /*
        messages.js
         */
        File messages = new File(Core.gCore().core.getDataFolder(), "messages.js");
        if (!messages.exists()) {
            ObjectUtils.saveGsonFile("messages", new Message());
        }
        Core.gCore().message = ObjectUtils.getGsonFile("messages", Message.class);

        /*
        configuration.js
         */
        //File configuration = new File(core.getDataFolder(), "configuration.js");
        //if (!configuration.exists()) {
        //    ObjectUtils.saveGsonFile("configuration", new Configuration());
        //}
        //Core.gCore().configuration = ObjectUtils.getGsonFile("configuration", Configuration.class);
    }
}
