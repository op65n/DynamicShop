package tech.op65n.dynamicshop.engine.ui.cache;

import tech.op65n.dynamicshop.Core;
import tech.op65n.dynamicshop.engine.ui.interfaces.UserInterface;
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
        if (isPresent(player)) {
            userInterfaceHashMap.remove(player);
            Core.devLogger.log("Removed " + player + " from UI cache");
        }
    }

    public static void cache(Player player, UserInterface ui) {
        userInterfaceHashMap.put(player, ui);
        Core.devLogger.log("Added " + player + " to UI cache");
    }

}
