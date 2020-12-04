package com.sebbaindustries.dynamicshop.commands.actions;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.commands.components.CommandFactory;
import com.sebbaindustries.dynamicshop.commands.components.ICmd;
import com.sebbaindustries.dynamicshop.commands.components.ITab;
import com.sebbaindustries.dynamicshop.engine.components.ShopItem;
import com.sebbaindustries.dynamicshop.engine.extensions.ItemStackImpl;
import com.sebbaindustries.dynamicshop.messages.Message;
import com.sebbaindustries.dynamicshop.utils.FileManager;
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

        ShopItem shopItem = new ShopItem(iStack, 4.03, 2.66);


        TomlWriter writer = new TomlWriter();
        try {
            writer.write(shopItem, new File(Core.gCore().core.getDataFolder() + "/item.toml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ShopItem desShopItem = new Toml().read(Core.gCore().core.getDataFolder() + "/item.toml").to(ShopItem.class);
        System.out.println(ObjectUtils.deserializeObjectToString(desShopItem));

        ItemStack desItemStack = desShopItem.getItemStack().getBukkitItemStack();
        player.getInventory().setItemInMainHand(desItemStack);
    }

    @Override
    public List<String> complete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.singletonList(" ");
    }
}
