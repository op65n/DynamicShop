package com.sebbaindustries.dynamicshop.engine;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.commands.CommandManager;
import com.sebbaindustries.dynamicshop.messages.Message;
import com.sebbaindustries.dynamicshop.utils.ObjectUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
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
            TomlWriter tomlWriter = new TomlWriter();
            try {
                tomlWriter.write(new Message(), new File(Core.gCore().core.getDataFolder() + "/msg.toml"));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        Core.gCore().message = ObjectUtils.getGsonFile("messages", Message.class);

        /*
        configuration.toml
         */
        //File configuration = new File(core.getDataFolder(), "configuration.toml");
        //if (!configuration.exists()) {
        //    ObjectUtils.saveGsonFile("configuration", new Configuration());
        //}
        //Core.gCore().configuration = ObjectUtils.getGsonFile("configuration", Configuration.class);
    }
}
