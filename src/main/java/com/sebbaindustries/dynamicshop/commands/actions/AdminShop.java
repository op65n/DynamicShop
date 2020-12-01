package com.sebbaindustries.dynamicshop.commands.actions;

import com.moandjiezana.toml.TomlWriter;
import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.commands.components.CommandFactory;
import com.sebbaindustries.dynamicshop.commands.components.ICmd;
import com.sebbaindustries.dynamicshop.commands.components.ITab;
import com.sebbaindustries.dynamicshop.engine.extensions.ExtItemStack;
import com.sebbaindustries.dynamicshop.engine.extensions.ExtPlayer;
import com.sebbaindustries.dynamicshop.messages.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AdminShop extends CommandFactory implements ICmd, ITab {

    @Override
    public String command() {
        return "adminshop";
    }

    @Override
    public String permission() {
        return null;
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            // TODO: Add no console message
            return;
        }

        ExtPlayer extPlayer = new ExtPlayer(sender);

        // Serialization
        ItemStack iStack = new ItemStack(extPlayer.player.getInventory().getItemInOffHand());
        Map<String, Object> map = iStack.serialize();
        //itemStack.serialize();
        //String name = iStack.getType().name();
        //itemStack.dataDump();
        //extPlayer.sendMessage("&6Done!");
//
        //ExtItemStack newItemStack = new ExtItemStack();
        //newItemStack.deserialize(name);
        //newItemStack.dataDump();

        TomlWriter writer = new TomlWriter();
        System.out.println(writer.write(map));


    }

    @Override
    public List<String> complete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.singletonList(" ");
    }
}
