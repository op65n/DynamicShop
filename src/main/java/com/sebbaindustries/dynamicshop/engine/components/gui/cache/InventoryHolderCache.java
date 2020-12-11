package com.sebbaindustries.dynamicshop.engine.components.gui.cache;

import com.sebbaindustries.dynamicshop.engine.components.gui.components.UserInterface;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class InventoryHolderCache {

    private static final HashMap<Player, UserInterface> userInterfaceHashMap = new HashMap<>();

    public static HashMap<Player, UserInterface> cached() {
        return userInterfaceHashMap;
    }

    public static boolean isPresent(Player player) {
        return userInterfaceHashMap.get(player) != null;
    }

    public static void removeIfPresent(Player player) {
        if (isPresent(player)) userInterfaceHashMap.remove(player);
    }

    public static void cache(Player player, UserInterface ui) {
        userInterfaceHashMap.put(player, ui);
    }

}
