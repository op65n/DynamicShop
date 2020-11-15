package com.sebbaindustries.dynamicshop.engine;

import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.engine.components.ShopCategory;
import com.sebbaindustries.dynamicshop.messages.Message;
import com.sebbaindustries.dynamicshop.utils.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class DynEngine {

    private final Core core;

    public DynEngine(Core core) {
        this.core = core;
    }

    public List<ShopCategory> categories = new ArrayList<>();

    public void initialize() {
        Core.gCore().fileManager.generateCatDirs(core);

        Core.gCore().message = ObjectUtils.getGsonFile("messages", Message.class);
    }
}
