package com.sebbaindustries.dynamicshop.engine.components.gui.guis;

import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.UIMetaData;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.UserInterface;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.UserInterfaceItem;
import com.sebbaindustries.dynamicshop.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class MainPageUI implements UserInterface, Listener {

    public MainPageUI() {
        metaData = new UIMetaData();
        String guiName = Core.gCore().dynEngine.shopUI.mainPageCache.guiName;
        metaData.setTitle(Color.format(guiName));

        UserInterfaceItem background = Core.gCore().dynEngine.shopUI.mainPageCache.background;
        if (background != null) this.background = background;

        inventorySlots = Core.gCore().dynEngine.shopUI.mainPageCache.item;
        sizeGUI();
        inventory = Bukkit.createInventory(null, metaData.getRows()*9, metaData.getTitle());
    }

    private Player player;
    private Inventory inventory;
    private UIMetaData metaData;
    private HashMap<Integer, UserInterfaceItem> inventorySlots;
    private UserInterfaceItem background = null;

    private void sizeGUI() {
        int size = Core.gCore().dynEngine.shopUI.mainPageCache.size;
        if (size == -1) {
            int guiRows = 1;
            for (int position : inventorySlots.keySet()) {
                double potentialGUISize = Math.ceil((double) position / 9.0);
                if (guiRows < potentialGUISize) guiRows = (int) potentialGUISize;
            }
            metaData.setRows(guiRows);
            return;
        }
        metaData.setRows(size);
    }

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
        fillBackground();
        inventorySlots.forEach((position, item) -> inventory.setItem(position, item.getBukkitItemStack()));
    }

    private void fillBackground() {
        if (this.background == null) return;
        for (int i = 0; i < metaData.getRows()*9; i++) {
            inventory.setItem(i, new ItemStack(background.getBukkitItemStack()));
        }
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
    public void onInventoryDrag(final InventoryDragEvent e) {
        System.out.println("test");
        if (e.getInventory() == inventory || player.getInventory() == e.getInventory()) {
            System.out.println("canceled");
            e.setCancelled(true);
        }
    }
}
