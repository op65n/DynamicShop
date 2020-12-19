package com.sebbaindustries.dynamicshop.utils;

import com.sebbaindustries.dynamicshop.engine.components.gui.interfaces.BukkitItemStack;
import com.sebbaindustries.dynamicshop.engine.components.gui.interfaces.Clickable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class UserInterfaceUtils {


    public static ItemStack getBukkitItemStack(BukkitItemStack bukkitItemStack) {
        // Create new ItemStack instance
        ItemStack iStack = new ItemStack(bukkitItemStack.material());
        ItemMeta iMeta = iStack.getItemMeta();

        /*
        Item lore
         */
        if (bukkitItemStack.lore() != null && !bukkitItemStack.lore().isEmpty()) {
            List<String> coloredLore = new ArrayList<>();
            bukkitItemStack.lore().forEach(loreLine -> coloredLore.add(Color.format(loreLine)));
            iMeta.setLore(coloredLore);
        }

        /*
        Item display name
         */
        if (bukkitItemStack.display() != null) iMeta.setDisplayName(Color.format(bukkitItemStack.display()));

        iStack.setItemMeta(iMeta);
        return iStack;
    }

    public static boolean isClickable(Object object) {
        return object instanceof Clickable;
    }

}
