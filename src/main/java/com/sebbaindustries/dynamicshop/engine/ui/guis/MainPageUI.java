package com.sebbaindustries.dynamicshop.engine.ui.guis;

import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopCategory;
import com.sebbaindustries.dynamicshop.engine.ui.cache.InventoryHolderCache;
import com.sebbaindustries.dynamicshop.engine.ui.cache.MainPageUICache;
import com.sebbaindustries.dynamicshop.engine.ui.components.ClickActions;
import com.sebbaindustries.dynamicshop.engine.ui.components.UIButton;
import com.sebbaindustries.dynamicshop.engine.ui.components.UICategory;
import com.sebbaindustries.dynamicshop.engine.ui.interfaces.BaseUI;
import com.sebbaindustries.dynamicshop.engine.ui.interfaces.Clickable;
import com.sebbaindustries.dynamicshop.engine.ui.interfaces.UserInterface;
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

    private final Player player;
    private final BaseUI cache;
    private final Map<Integer, Object> mappedInventory = new TreeMap<>();
    private Inventory inventory;

    public MainPageUI(Player player) {
        this.player = player;
        this.cache = Core.gCore().getEngine().ui().getMainPageCache();

        inventory = Bukkit.createInventory(null, cache.size() * 9, Color.format(cache.name()));

        // Update/flush cache
        InventoryHolderCache.cache(player, this);
    }

    @Override
    public void open() {
        player.openInventory(inventory);
        InventoryHolderCache.cache(player, this);
    }

    @Override
    public void update() {
        updateUISlots(false);

        cache.setSize(UserInterfaceUtils.calculateInventorySize(mappedInventory));

        inventory = Bukkit.createInventory(null, cache.size() * 9, Color.format(cache.name()));
        UserInterfaceUtils.setupInventory(inventory, mappedInventory, cache.size());

        InventoryHolderCache.cache(player, this);
    }

    @Override
    public void updateUISlots(boolean updateCurrent) {
        mappedInventory.clear();

        /*
        background
         */
        UserInterfaceUtils.createBackground(mappedInventory, cache.background(), cache.size());

        /*
        buttons
         */
        cache.buttons().forEach(button -> mappedInventory.put(button.getSlot(), button));

        /*
        Categories
         */
        List<ShopCategory> categoryCache = Core.gCore().getEngine().container().getPrioritizedCategoryList();
        ((MainPageUICache) cache).getCategory().forEach(uiCategory -> {
            if (categoryCache == null || categoryCache.isEmpty() || categoryCache.get(0) == null) return;
            uiCategory.setCategory(categoryCache.get(0));
            mappedInventory.put(uiCategory.getSlot(), uiCategory);
            categoryCache.remove(0);
        });

        if (!updateCurrent) return;
        UserInterfaceUtils.clearUI(player);
        UserInterfaceUtils.fillInventory(player, mappedInventory, cache.size());
        player.updateInventory();

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
