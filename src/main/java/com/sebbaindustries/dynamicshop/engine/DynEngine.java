package com.sebbaindustries.dynamicshop.engine;

import com.google.common.collect.Multimap;
import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.commands.CommandManager;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopCategory;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopItem;
import com.sebbaindustries.dynamicshop.engine.extensions.ShopContainer;
import com.sebbaindustries.dynamicshop.engine.structure.DirectoryStructure;
import com.sebbaindustries.dynamicshop.messages.Message;
import com.sebbaindustries.dynamicshop.utils.FileManager;
import com.sebbaindustries.dynamicshop.utils.ObjectUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class DynEngine {

    public ShopContainer container;

    public void initialize() {
        Core.gCore().commandManager = new CommandManager(Core.gCore().core);
        Core.gCore().message = new Toml().read(Core.gCore().fileManager.getFile(FileManager.PluginFiles.MESSAGES)).to(Message.class);
        container = new ShopContainer();
    }
}
