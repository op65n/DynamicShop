package com.sebbaindustries.dynamicshop.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class UserInterfaceUtils {

    public static ItemStack getBukkitItemStack(Material material, String display, List<String> lore) {
        // Create new ItemStack instance
        ItemStack iStack = new ItemStack(material);
        ItemMeta iMeta = iStack.getItemMeta();

        /*
        Item lore
         */
        if (lore != null && !lore.isEmpty()) {
            List<String> coloredLore = new ArrayList<>();
            lore.forEach(loreLine -> coloredLore.add(Color.format(loreLine)));
            iMeta.setLore(coloredLore);
        }

        /*
        Item display name
         */
        if (display != null) iMeta.setDisplayName(Color.format(display));

        iStack.setItemMeta(iMeta);
        return iStack;
    }

}
