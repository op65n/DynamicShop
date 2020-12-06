package com.sebbaindustries.dynamicshop.commands.actions;

import com.moandjiezana.toml.TomlWriter;
import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.commands.components.CommandFactory;
import com.sebbaindustries.dynamicshop.commands.components.ICmd;
import com.sebbaindustries.dynamicshop.commands.components.ITab;
import com.sebbaindustries.dynamicshop.engine.DynEngine;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopItem;
import com.sebbaindustries.dynamicshop.utils.ObjectUtils;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Buy extends CommandFactory implements ICmd, ITab {

    @Override
    public String command() {
        return "buy";
    }

    @Override
    public String permission() {
        return null;
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] args) {
        TomlWriter writer = new TomlWriter.Builder()
                .build();

        TomlWriter writer1 = new TomlWriter.Builder()
                .indentTablesBy(2)
                .build();

        TomlWriter writer2 = new TomlWriter.Builder()
                .indentTablesBy(2)
                .indentValuesBy(2)
                .build();

        TomlWriter writer3 = new TomlWriter.Builder()
                .indentValuesBy(2)
                .build();

        try {
            writer.write(DynEngine.items, new File(Core.gCore().core.getDataFolder() + "/" + "temp" + ".toml"));
            writer1.write(DynEngine.items, new File(Core.gCore().core.getDataFolder() + "/" + "temp1" + ".toml"));
            writer2.write(DynEngine.items, new File(Core.gCore().core.getDataFolder() + "/" + "temp2" + ".toml"));
            writer3.write(DynEngine.items, new File(Core.gCore().core.getDataFolder() + "/" + "temp3" + ".toml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> complete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.singletonList(" ");
    }
}
