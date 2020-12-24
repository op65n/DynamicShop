package com.sebbaindustries.dynamicshop.utils;

import com.sebbaindustries.dynamicshop.engine.components.gui.components.UIBackground;
import com.sebbaindustries.dynamicshop.engine.components.gui.interfaces.BukkitItemStack;
import com.sebbaindustries.dynamicshop.engine.components.gui.interfaces.Clickable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class UserInterfaceUtils {


    public static ItemStack getBukkitItemStack(BukkitItemStack bukkitItemStack) {
        // Create new ItemStack instance
        ItemStack iStack = new ItemStack(bukkitItemStack.material());
        iStack.setAmount(bukkitItemStack.amount());
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

    public static Map<Integer, Object> createBackground(UIBackground background, final int cache) {
        Map<Integer, Object> mappedInventory = new TreeMap<>();
        if (background != null && background.getMaterial() != null) {
            for (int i = 0; i < cache * 9; i++) {
                mappedInventory.put(i, background);
            }
        }
        return mappedInventory;
    }

    public static void clearUI(Player player) {
        player.getOpenInventory().getTopInventory().clear();
    }

    public static int calculateInventorySize(final Map<Integer, Object> mappedInventory) {
        int newSize = 0;
        for (Map.Entry<Integer, Object> entry : mappedInventory.entrySet()) {
            if (entry.getValue() instanceof UIBackground) continue;
            if (newSize < entry.getKey()) newSize = entry.getKey();
        }

        newSize = (int) Math.ceil((double) newSize / 9.0);
        return newSize;
    }

    public static void fillInventory(final Player player, final Map<Integer, Object> mappedInventory, final int cache) {
        mappedInventory.forEach((slot, item) -> {
            if (slot > cache * 9 - 1) return;
            if (item instanceof BukkitItemStack) {
                BukkitItemStack bukkitItemStack = (BukkitItemStack) item;
                player.getOpenInventory().setItem(slot, getBukkitItemStack(bukkitItemStack));
            }
        });
        player.updateInventory();
    }

    public static Inventory setupInventory(Inventory inventory, final Map<Integer, Object> mappedInventory, final int cache) {
        mappedInventory.forEach((slot, item) -> {
            if (slot > cache * 9 - 1) return;
            if (item instanceof BukkitItemStack) {
                BukkitItemStack bukkitItemStack = (BukkitItemStack) item;
                inventory.setItem(slot, UserInterfaceUtils.getBukkitItemStack(bukkitItemStack));
            }
        });
        return inventory;
    }

}
