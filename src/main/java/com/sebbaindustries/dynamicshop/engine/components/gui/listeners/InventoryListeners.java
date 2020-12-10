package com.sebbaindustries.dynamicshop.engine.components.gui.listeners;

import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.UserInterface;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.jetbrains.annotations.NotNull;

public class InventoryListeners implements @NotNull Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(final InventoryClickEvent e) {

        final Player player = (Player) e.getWhoClicked();

        if (Core.gCore().dynEngine.shopUI.invHolder.userInterfaceHashMap.containsKey(player)) {
            e.setCancelled(true);
            UserInterface ui = Core.gCore().dynEngine.shopUI.invHolder.cache().get(player);
            if (e.isLeftClick()) ui.onLeftClick(e.getRawSlot());
            if (e.isRightClick()) ui.onRightClick(e.getRawSlot());
            if (e.getClick().isCreativeAction()) ui.onMiddleClick(e.getRawSlot());
            ui.update();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClose(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        Core.gCore().dynEngine.shopUI.invHolder.userInterfaceHashMap.remove(player);
    }

    // Cancel dragging in our inventory
    //@EventHandler
    //public void onInventoryDrag(final InventoryDragEvent e) {
    //e.getWhoClicked().sendMessage("drag event");
    //System.out.println("drag event");
    //e.setCancelled(true);
    //}

}
