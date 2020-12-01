package com.sebbaindustries.dynamicshop.commands.actions;

import com.sebbaindustries.dynamicshop.commands.components.CommandFactory;
import com.sebbaindustries.dynamicshop.commands.components.ICmd;
import com.sebbaindustries.dynamicshop.commands.components.ITab;
import com.sebbaindustries.dynamicshop.engine.extensions.ExtItemStack;
import com.sebbaindustries.dynamicshop.engine.extensions.ExtPlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import java.util.Collections;
import java.util.List;

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
        ExtItemStack itemStack = new ExtItemStack(iStack);
        itemStack.serialize();
        String name = iStack.getType().name();
        itemStack.dataDump();
        extPlayer.sendMessage("&6Done!");

        ExtItemStack newItemStack = new ExtItemStack();
        newItemStack.deserialize(name);
        newItemStack.dataDump();



    }

    @Override
    public List<String> complete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.singletonList(" ");
    }
}
