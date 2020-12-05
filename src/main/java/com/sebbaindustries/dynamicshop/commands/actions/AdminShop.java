package com.sebbaindustries.dynamicshop.commands.actions;

import com.moandjiezana.toml.TomlWriter;
import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.commands.components.CommandFactory;
import com.sebbaindustries.dynamicshop.commands.components.ICmd;
import com.sebbaindustries.dynamicshop.commands.components.ITab;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopItem;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopMeta;
import com.sebbaindustries.dynamicshop.engine.components.shop.implementations.ShopItemImpl;
import com.sebbaindustries.dynamicshop.utils.ObjectUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
        ItemStack iStack = new ItemStack(player.getInventory().getItemInOffHand());
        ItemMeta iMeta = iStack.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add("line 0");
        lore.add("line 1");
        lore.add("line 2");
        lore.add("line 3");
        iMeta.setLore(lore);
        iStack.setItemMeta(iMeta);

        ShopItemImpl item = new ShopItemImpl(iStack, new ShopMeta(4.44, 3.33));
        ShopItem shopItem = item;

        System.out.println(ObjectUtils.deserializeObjectToString(shopItem.getBukkitItemStack()));
        shopItem.serialize();
    }

    @Override
    public List<String> complete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.singletonList(" ");
    }
}
