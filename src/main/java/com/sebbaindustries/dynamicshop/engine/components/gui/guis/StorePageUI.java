package com.sebbaindustries.dynamicshop.engine.components.gui.guis;

import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.engine.components.gui.cache.InventoryHolderCache;
import com.sebbaindustries.dynamicshop.engine.components.gui.cache.StorePageUICache;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.UIBackground;
import com.sebbaindustries.dynamicshop.engine.components.gui.interfaces.UserInterface;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopCategory;
import com.sebbaindustries.dynamicshop.utils.Color;
import com.sebbaindustries.dynamicshop.utils.UserInterfaceUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class StorePageUI implements UserInterface {

    public StorePageUI(Player player, ShopCategory category) {
        this.player = player;
        this.cache = Core.gCore().getEngine().instance().getShopUI().getStorePageCache();

        inventory = Bukkit.createInventory(null, cache.getSize() * 9, Color.format(cache.getName()));

        // Update/flush cache
        InventoryHolderCache.cache(player, this);
    }

    private final Player player;
    private Inventory inventory;

    private StorePageUICache cache;
    private Map<Integer, Object> mappedInventory = new TreeMap<>();



    @Override
    public void open() {
        player.openInventory(inventory);
        InventoryHolderCache.cache(player, this);
    }

    @Override
    public void update() {
         /*
        background
         */
        UIBackground background = cache.getBackground();
        for (int i = 0; i < cache.getSize() * 9; i++) {
            inventory.setItem(i, UserInterfaceUtils.getBukkitItemStack(background));
            mappedInventory.put(i, background);
        }

        /*
        Buttons
         */
        cache.getButton().forEach(button -> {
            inventory.setItem(button.getSlot(), UserInterfaceUtils.getBukkitItemStack(button));
            mappedInventory.put(button.getSlot(), button);
        });

        InventoryHolderCache.cache(player, this);
    }


    @Override
    public void close() {
        player.closeInventory();
        InventoryHolderCache.removeIfPresent(player);
    }

    @Override
    public void onRightClick(int slot) {

    }

    @Override
    public void onLeftClick(int slot) {

    }

    @Override
    public void onMiddleClick(int slot) {

    }
}
