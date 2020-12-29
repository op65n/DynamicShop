package com.sebbaindustries.dynamicshop.engine.ui.guis;

import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.engine.ui.cache.InventoryHolderCache;
import com.sebbaindustries.dynamicshop.engine.ui.cache.StorePageUICache;
import com.sebbaindustries.dynamicshop.engine.ui.components.ClickActions;
import com.sebbaindustries.dynamicshop.engine.ui.components.UIBackground;
import com.sebbaindustries.dynamicshop.engine.ui.components.UIButton;
import com.sebbaindustries.dynamicshop.engine.ui.interfaces.BukkitItemStack;
import com.sebbaindustries.dynamicshop.engine.ui.interfaces.Clickable;
import com.sebbaindustries.dynamicshop.engine.ui.interfaces.UserInterface;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopCategory;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopItem;
import com.sebbaindustries.dynamicshop.utils.Color;
import com.sebbaindustries.dynamicshop.utils.ListUtils;
import com.sebbaindustries.dynamicshop.utils.UserInterfaceUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;

import java.util.Map;
import java.util.TreeMap;

public class StorePageUI implements UserInterface {

    public StorePageUI(Player player, ShopCategory category) {
        this.player = player;
        this.cache = Core.gCore().getEngine().ui().getStorePageCache();
        this.category = category;

        inventory = Bukkit.createInventory(null, cache.size() * 9, Color.format(cache.name()));

        // Update/flush cache
        InventoryHolderCache.cache(player, this);
    }

    private final Player player;
    private Inventory inventory;

    private final StorePageUICache cache;
    private final ShopCategory category;
    private final Map<Integer, Object> mappedInventory = new TreeMap<>();

    int collapseBy = 0;


    @Override
    public void open() {
        player.openInventory(inventory);
        InventoryHolderCache.cache(player, this);
    }

    @Override
    public void update() {
        updateUISlots(false);

        createItemsPanel();

        cache.setSize(UserInterfaceUtils.calculateInventorySize(mappedInventory));

        inventory = Bukkit.createInventory(null, cache.size() * 9, Color.format(cache.name()));
        UserInterfaceUtils.setupInventory(inventory, mappedInventory, cache.size());

        InventoryHolderCache.cache(player, this);
    }

    @Override
    public void updateUISlots(boolean updateCurrent) {
        /*
        background
         */
        UserInterfaceUtils.createBackground(mappedInventory, cache.background(), cache.size());

        /*
        panel
         */
        createItemsPanel();

        /*
        buttons
         */
        cache.buttons().forEach(button -> {
            if (button.getSlot() > 26) {
                button.setSlot(button.getSlot() - collapseBy);
            }
            mappedInventory.put(button.getSlot(), button);
        });

        if (!updateCurrent) return;
        UserInterfaceUtils.clearUI(player);
        UserInterfaceUtils.fillInventory(player, mappedInventory, cache.size());
        player.updateInventory();
    }

    /*
    Blackbox
     */
    private void createItemsPanel() {
        int cornerA = cache.getItems().getCornerA();
        int cornerB = cache.getItems().getCornerB();

        int columnStart = (cornerA + 1) / 9;
        int columnEnd = (cornerB + 1) / 9;

        int columnLength = Math.abs(columnStart - columnEnd) + 1;
        int rowLength = Math.abs(cornerA - (cornerB - ((columnLength - 1) * 9))) + 1;

        ListUtils<ShopItem> listUtils = new ListUtils<>(category.getOrderedItemList());

        int itemCount = 0;

        for (int y = 0; y < columnEnd; y++) {
            for (int x = cornerA; x < rowLength + cornerA; x++) {
                ShopItem item = listUtils.getNext();
                if (item == null) continue;
                item.setOnLeftClick(cache.getItems().getOnLeftClick());
                item.setOnRightClick(cache.getItems().getOnRightClick());
                item.setOnMiddleClick(cache.getItems().getOnMiddleClick());
                item.setOnClick(cache.getItems().getOnClick());
                mappedInventory.put(x + (y * 9), item);
                itemCount++;
            }
        }

        int usedColumns = (int) Math.ceil((double) itemCount / 9.0);

        if (usedColumns >= columnLength) return;
        if (cache.getItems().isCollapsed()) collapseBy = (columnLength - usedColumns) * 9;

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
        if (object instanceof ShopItem) {
            Clickable item = (ShopItem) object;
            switch (clickType) {
                case RIGHT -> itemHandler(item.rightClick(), slot);
                case LEFT -> itemHandler(item.leftClick(), slot);
                case MIDDLE -> itemHandler(item.middleClick(), slot);
            }
        }
    }

    private void buttonHandler(ClickActions action) {
        switch (action) {
            case EXIT, CLOSE -> close();
            case BACK -> {
                UserInterface ui = new MainPageUI(player);
                ui.update();
                ui.open();
            }
        }
    }

    private void itemHandler(ClickActions action, int slot) {
        switch (action) {
            case SELL -> {
                UserInterface ui = new SellPageUI(player, (ShopItem) this.mappedInventory.get(slot), category);
                ui.update();
                ui.open();
            }
            case BUY -> {
                UserInterface ui = new BuyPageUI(player, (ShopItem) this.mappedInventory.get(slot), category);
                ui.update();
                ui.open();
            }
        }
    }
}
