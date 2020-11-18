package com.sebbaindustries.dynamicshop.engine;

import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.engine.components.ShopCategory;
import com.sebbaindustries.dynamicshop.messages.Message;
import com.sebbaindustries.dynamicshop.settings.Configuration;
import com.sebbaindustries.dynamicshop.utils.ObjectUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DynEngine {

    private final Core core;
    public List<ShopCategory> categories = new ArrayList<>();

    public DynEngine(Core core) {
        this.core = core;
    }

    public void initialize() {
        Core.gCore().fileManager.generateCatDirs(core);

        /*
        messages.js
         */
        File messages = new File(core.getDataFolder(), "messages.js");
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
