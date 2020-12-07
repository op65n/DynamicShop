package com.sebbaindustries.dynamicshop.engine.components.gui.guis;

import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.UIMetaData;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.UserInterface;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.UserInterfaceItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class MainPage implements UserInterface, Listener {

    public MainPage() {
        metaData = new UIMetaData();
        inventory = Bukkit.createInventory(null, metaData.getRows()*9, metaData.getTitle());
    }

    private Player player;
    private Inventory inventory;
    private UIMetaData metaData;
    private HashMap<Integer, UserInterfaceItem> inventorySlots = Core.gCore().dynEngine.shopUI.mainPageCache.item;

    @Override
    public void open(Player player) {
        player.openInventory(inventory);
        this.player = player;
    }

    @Override
    public void draw() {

    }

    @Override
    public void clear() {

    }

    @Override
    public void update() {
        inventory = Bukkit.createInventory(null, metaData.getRows()*9, metaData.getTitle());
        // TODO: Add slot limiter
        inventorySlots.forEach((position, item) -> inventory.setItem(position, item.getBukkitItemStack()));
    }

    @Override
    public void close() {
        player.closeInventory();
    }

    @Override
    public void setMetaData(UIMetaData metaData) {
        this.metaData = metaData;
    }

    @Override
    public UIMetaData getMetaData() {
        return this.metaData;
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {

        final Player p = (Player) e.getWhoClicked();

        // Using slots click is a best option for your inventory click's
        p.sendMessage("You clicked at slot " + e.getRawSlot());
    }

    // Cancel dragging in our inventory
    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory() == inventory) {
            e.setCancelled(true);
        }
    }
}
