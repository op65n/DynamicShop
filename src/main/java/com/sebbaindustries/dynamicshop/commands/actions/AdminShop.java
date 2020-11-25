package com.sebbaindustries.dynamicshop.commands.actions;

import com.sebbaindustries.dynamicshop.commands.components.CommandFactory;
import com.sebbaindustries.dynamicshop.commands.components.ICmd;
import com.sebbaindustries.dynamicshop.commands.components.ITab;
import com.sebbaindustries.dynamicshop.engine.components.MockItemStack;
import com.sebbaindustries.dynamicshop.utils.CPlayer;
import com.sebbaindustries.dynamicshop.utils.ObjectUtils;
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

        Player player = (Player) sender;
        player.sendMessage("&6fklhsdfi");

        CPlayer cPlayer = (CPlayer) player;
        cPlayer.sendMessage("&6gfgfgdgd");

        // Serialization
        ItemStack iStack = new ItemStack(player.getInventory().getItemInOffHand());
        MockItemStack itemStack = new MockItemStack(iStack);
        ObjectUtils.saveGsonFile("test", itemStack);

        MockItemStack genItemStack = ObjectUtils.getGsonFile("test", MockItemStack.class);

        ItemStack cItemStack = genItemStack.getItemStack();
        player.getInventory().setItemInMainHand(cItemStack);

    }

    @Override
    public List<String> complete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.singletonList(" ");
    }
}
