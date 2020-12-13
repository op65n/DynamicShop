package com.sebbaindustries.dynamicshop.utils;

import com.sebbaindustries.dynamicshop.engine.components.gui.cache.UICache;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.UIMetaData;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.UserInterfaceItem;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class UserInterfaceUtils {

    public static UIMetaData setupMetaData(UICache cache) {
        UIMetaData metaData = new UIMetaData();
        metaData.setTitle(Color.format(cache.getGuiName()));
        metaData.setRows(cache.getSize());
        return metaData;
    }

    public static UserInterfaceItem setupBackground(UICache cache) {
        return cache.getBackground();
    }

    public static Map<Integer, UserInterfaceItem> setupBaseItemOrder(UICache cache) {
        return new TreeMap<>(cache.getItem());
    }

    public static Inventory updateGUIFrame(UIMetaData metaData, Map<Integer, UserInterfaceItem> slots, UserInterfaceItem background) {
        // Dynamic GUI sizing
        if (metaData.getRows() == -1) {
            int guiRows = 1;
            for (int position : slots.keySet()) {
                double potentialGUISize = Math.ceil((double) position / 9.0);
                if (guiRows < potentialGUISize) guiRows = (int) potentialGUISize;
            }
            metaData.setRows(guiRows);
        }

        Inventory inventory = Bukkit.createInventory(null, metaData.getRows() * 9, Color.format(metaData.getTitle()));

        // Background covering
        if (background == null) return inventory;
        for (int i = 0; i < metaData.getRows() * 9; i++) {
            inventory.setItem(i, new ItemStack(background.getBukkitItemStack()));
        }

        return inventory;
    }

}
