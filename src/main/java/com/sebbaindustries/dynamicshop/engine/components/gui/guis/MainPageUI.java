package com.sebbaindustries.dynamicshop.engine.components.gui.guis;

import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.engine.components.gui.cache.InventoryHolderCache;
import com.sebbaindustries.dynamicshop.engine.components.gui.cache.MainPageUICache;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.*;
import com.sebbaindustries.dynamicshop.engine.components.gui.interfaces.UserInterface;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopCategory;
import com.sebbaindustries.dynamicshop.utils.Color;
import com.sebbaindustries.dynamicshop.utils.UserInterfaceUtils;
import jdk.jfr.Category;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;

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

        /*
        Categories
         */
        List<ShopCategory> categories = Core.gCore().getEngine().instance().getContainer().getPrioritizedCategoryList();
        cache.getCategory().forEach(uiCategory -> {
            ShopCategory category = null;
            for (ShopCategory shopCategory : categories) {
                category = shopCategory;
                categories.remove(shopCategory);
                break;
            }
            uiCategory.setCategory(category);

            if (category == null) {
                category = new ShopCategory();
            }

            inventory.setItem(uiCategory.getSlot(), UserInterfaceUtils.getBukkitItemStack(category));
            mappedInventory.put(uiCategory.getSlot(), category);
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

        if (object == null || object instanceof UIBackground) {
            return;
        }

        if (object instanceof UIButton) {
            UIButton button = (UIButton) object;

            if (button.getOnRightClick() == ClickActions.NA) {
                buttonHandler(button.getOnClick());
                return;
            }
            buttonHandler(button.getOnRightClick());
            return;
        }

        if (object instanceof UICategory) {
        }
    }

    @Override
    public void onLeftClick(int slot) {
        Object object = mappedInventory.get(slot);

        if (object == null || object instanceof UIBackground) {
            return;
        }

        if (object instanceof UIButton) {
            UIButton button = (UIButton) object;

            if (button.getOnLeftClick() == ClickActions.NA) {
                buttonHandler(button.getOnClick());
                return;
            }
            buttonHandler(button.getOnLeftClick());
            return;
        }

        if (object instanceof UICategory) {
        }
    }

    @Override
    public void onMiddleClick(int slot) {
        Object object = mappedInventory.get(slot);

        if (object == null || object instanceof UIBackground) {
            return;
        }

        if (object instanceof UIButton) {
            UIButton button = (UIButton) object;

            if (button.getOnMiddleClick() == ClickActions.NA) {
                buttonHandler(button.getOnClick());
                return;
            }
            buttonHandler(button.getOnMiddleClick());
            return;
        }

        if (object instanceof UICategory) {
        }
    }

    void buttonHandler(ClickActions action) {
        switch (action) {
            case EXIT, CLOSE, BACK -> close();
        }
    }

}
