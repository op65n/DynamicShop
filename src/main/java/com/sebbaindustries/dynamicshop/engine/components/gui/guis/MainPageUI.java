package com.sebbaindustries.dynamicshop.engine.components.gui.guis;

import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.engine.components.gui.cache.InventoryHolderCache;
import com.sebbaindustries.dynamicshop.engine.components.gui.cache.MainPageUICache;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.Background;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.Button;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.Category;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.UserInterface;
import com.sebbaindustries.dynamicshop.utils.Color;
import com.sebbaindustries.dynamicshop.utils.UserInterfaceUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

public class MainPageUI implements UserInterface {

    public MainPageUI(Player player) {
        this.player = player;
        this.cache = Core.gCore().getEngine().instance().getShopUI().getMainPageCache();

        inventory = Bukkit.createInventory(null, cache.getSize() * 9, Color.format(cache.getName()));

        // Update/flush cache
        InventoryHolderCache.cache(player, this);
    }

    private final Player player;
    private Inventory inventory;
    private MainPageUICache cache;


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
        Background background = cache.getBackground();
        for (int i = 0; i < cache.getSize() * 9; i++) {
            inventory.setItem(i, UserInterfaceUtils.getBukkitItemStack(background.getMaterial(), background.getDisplay(), background.getLore()));
        }

        /*
        Buttons
         */
        for (Button button : cache.getButton()) {
            inventory.setItem(button.getSlot(), UserInterfaceUtils.getBukkitItemStack(button.getMaterial(), button.getDisplay(), button.getLore()));
        }

        /*
        Categories
         */
        for (Category category : cache.getCategory()) {
            inventory.setItem(category.getSlot(), UserInterfaceUtils.getBukkitItemStack(background.getMaterial(), Color.format("&c&lMissing category"), Collections.singletonList("Please check the configuration!")));
        }


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
