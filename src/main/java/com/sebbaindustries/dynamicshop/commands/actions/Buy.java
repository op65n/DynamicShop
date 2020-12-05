package com.sebbaindustries.dynamicshop.commands.actions;

import com.moandjiezana.toml.TomlWriter;
import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.commands.components.CommandFactory;
import com.sebbaindustries.dynamicshop.commands.components.ICmd;
import com.sebbaindustries.dynamicshop.commands.components.ITab;
import com.sebbaindustries.dynamicshop.engine.DynEngine;
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

        System.out.println(ObjectUtils.deserializeObjectToString(DynEngine.items));
        System.out.println(" ");
        System.out.println(ObjectUtils.deserializeObjectToString(DynEngine.items.get("Item")));
        try {
            writer.write(DynEngine.items.get("Item"), new File(Core.gCore().core.getDataFolder() + "/" + "temp" + ".toml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> complete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.singletonList(" ");
    }
}
