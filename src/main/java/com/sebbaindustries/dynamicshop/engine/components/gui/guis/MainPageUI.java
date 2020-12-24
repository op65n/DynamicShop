package com.sebbaindustries.dynamicshop.engine.components.gui.guis;

import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.engine.components.gui.cache.InventoryHolderCache;
import com.sebbaindustries.dynamicshop.engine.components.gui.cache.MainPageUICache;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.ClickActions;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.UIBackground;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.UIButton;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.UICategory;
import com.sebbaindustries.dynamicshop.engine.components.gui.interfaces.Clickable;
import com.sebbaindustries.dynamicshop.engine.components.gui.interfaces.UserInterface;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopCategory;
import com.sebbaindustries.dynamicshop.utils.Color;
import com.sebbaindustries.dynamicshop.utils.UserInterfaceUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;

import java.util.List;
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
    private final Inventory inventory;
    private final MainPageUICache cache;
    private final Map<Integer, Object> mappedInventory = new TreeMap<>();


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

            if (category == null) {
                inventory.setItem(uiCategory.getSlot(), UserInterfaceUtils.getBukkitItemStack(new ShopCategory()));
                mappedInventory.put(uiCategory.getSlot(), uiCategory);
                return;
            }

            uiCategory.setCategory(category);

            inventory.setItem(uiCategory.getSlot(), UserInterfaceUtils.getBukkitItemStack(category));
            mappedInventory.put(uiCategory.getSlot(), uiCategory);
        });


        InventoryHolderCache.cache(player, this);
    }

    @Override
    public void updateUISlots(boolean updateCurrent) {

    }

    @Override
    public void close() {
        player.closeInventory();
        InventoryHolderCache.removeIfPresent(player);
    }

    @Override
    public void onClick(int slot, ClickType clickType) {
        Object object = mappedInventory.get(slot);
        if (!UserInterfaceUtils.isClickable(object)) return;

        if (object instanceof UIButton) {
            Clickable button = (UIButton) object;
            switch (clickType) {
                case RIGHT -> buttonHandler(button.rightClick());
                case LEFT -> buttonHandler(button.leftClick());
                case MIDDLE -> buttonHandler(button.middleClick());
            }
            return;
        }

        if (object instanceof UICategory) {
            Clickable category = (UICategory) object;
            switch (clickType) {
                case RIGHT -> categoryHandler(category.rightClick(), slot);
                case LEFT -> categoryHandler(category.leftClick(), slot);
                case MIDDLE -> categoryHandler(category.middleClick(), slot);
            }
        }
    }

    private void buttonHandler(ClickActions action) {
        switch (action) {
            case EXIT, CLOSE, BACK -> close();
        }
    }

    private void categoryHandler(ClickActions action, int slot) {
        switch (action) {
            case EXIT, CLOSE, BACK -> close();
            case OPEN -> {
                UICategory category = (UICategory) mappedInventory.get(slot);
                if (category.getCategory() == null) return;
                UserInterface ui = new StorePageUI(player, category.getCategory());
                ui.update();
                ui.open();
            }
        }
    }

}
