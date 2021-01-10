package tech.op65n.dynamicshop.engine.ui.listeners;

import tech.op65n.dynamicshop.engine.ui.cache.InventoryHolderCache;
import tech.op65n.dynamicshop.engine.ui.interfaces.UserInterface;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.jetbrains.annotations.NotNull;

/**
 * @author sebba
 * @version 1.0
 */
public class InventoryListeners implements @NotNull Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(final @NotNull InventoryClickEvent e) {

        final Player player = (Player) e.getWhoClicked();

        // Check if player is present in DynamicShop inventory holding cache
        if (InventoryHolderCache.isPresent(player)) {
            e.setCancelled(true);
            UserInterface ui = InventoryHolderCache.cached().get(player);
            // Preform click actions
            if (e.isLeftClick()) ui.onClick(e.getRawSlot(), ClickType.LEFT);
            if (e.isRightClick()) ui.onClick(e.getRawSlot(), ClickType.RIGHT);
            if (e.getClick().isCreativeAction()) ui.onClick(e.getRawSlot(), ClickType.MIDDLE);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClose(final @NotNull InventoryCloseEvent e) {
        InventoryHolderCache.removeIfPresent((Player) e.getPlayer());
    }

}
