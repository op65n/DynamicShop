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
import java.util.Map;
import java.util.TreeMap;

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
        Background background = cache.getBackground();
        for (int i = 0; i < cache.getSize() * 9; i++) {
            inventory.setItem(i, UserInterfaceUtils.getBukkitItemStack(background.getMaterial(), background.getDisplay(), background.getLore()));
            mappedInventory.put(i, background);
        }

        /*
        Buttons
         */
        cache.getButton().forEach(button -> {
            inventory.setItem(button.getSlot(), UserInterfaceUtils.getBukkitItemStack(button.getMaterial(), button.getDisplay(), button.getLore()));
            mappedInventory.put(button.getSlot(), button);
        });

        /*
        Categories
         */
        cache.getCategory().forEach(category -> {
            inventory.setItem(category.getSlot(), UserInterfaceUtils.getBukkitItemStack(
                    background.getMaterial(), Color.format("&c&lMissing category"), Collections.singletonList("Please check the configuration!"))
            );
            mappedInventory.put(category.getSlot(), category);
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
        Object object = mappedInventory.get(slot);
        if (object == null);

        if (object instanceof Background) {
            System.out.println("back");
            return;
        }

        if (object instanceof Button) {
            System.out.println("button");
            Button button = (Button) object;

            switch (button.getOnClick()) {
                case EXIT, CLOSE, BACK -> close();
            }
            return;
        }

        if (object instanceof Category) {
            System.out.println("Cat");
        }
    }

    @Override
    public void onLeftClick(int slot) {

    }

    @Override
    public void onMiddleClick(int slot) {

    }

}
