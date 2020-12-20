package com.sebbaindustries.dynamicshop.engine.components.gui.guis;

import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.engine.components.gui.cache.InventoryHolderCache;
import com.sebbaindustries.dynamicshop.engine.components.gui.cache.StorePageUICache;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.ClickActions;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.UIBackground;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.UIButton;
import com.sebbaindustries.dynamicshop.engine.components.gui.interfaces.BukkitItemStack;
import com.sebbaindustries.dynamicshop.engine.components.gui.interfaces.Clickable;
import com.sebbaindustries.dynamicshop.engine.components.gui.interfaces.UserInterface;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopCategory;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopItem;
import com.sebbaindustries.dynamicshop.utils.Color;
import com.sebbaindustries.dynamicshop.utils.ListUtils;
import com.sebbaindustries.dynamicshop.utils.UserInterfaceUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class StorePageUI implements UserInterface {

    public StorePageUI(Player player, ShopCategory category) {
        this.player = player;
        this.cache = Core.gCore().getEngine().instance().getShopUI().getStorePageCache();
        this.category = category;

        inventory = Bukkit.createInventory(null, cache.getSize() * 9, Color.format(cache.getName()));

        // Update/flush cache
        InventoryHolderCache.cache(player, this);
    }

    private final Player player;
    private Inventory inventory;

    private StorePageUICache cache;
    private ShopCategory category;
    private Map<Integer, Object> mappedInventory = new TreeMap<>();

    int collapseBy = 0;


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
            //inventory.setItem(i, UserInterfaceUtils.getBukkitItemStack(background));
            mappedInventory.put(i, background);
        }

        createItemsPanel();

        /*
        Buttons
         */
        cache.getButton().forEach(button -> {
            if (button.getSlot() > 26) {
                button.setSlot(button.getSlot()-collapseBy);
            }
            //inventory.setItem(button.getSlot(), UserInterfaceUtils.getBukkitItemStack(button));
            mappedInventory.put(button.getSlot(), button);
        });

        int newSize = 0;

        for (Map.Entry<Integer, Object> entry : mappedInventory.entrySet()) {
            if (entry.getValue() instanceof UIBackground) continue;
            if (newSize < entry.getKey()) newSize = entry.getKey();
        }

        newSize = (int) Math.ceil((double) newSize / 9.0);

        cache.setSize(newSize);

        inventory = Bukkit.createInventory(null, cache.getSize() * 9, Color.format(cache.getName()));
        mappedInventory.forEach((slot, item) -> {
            if (item instanceof BukkitItemStack) {
                BukkitItemStack bukkitItemStack = (BukkitItemStack) item;
                inventory.setItem(slot, UserInterfaceUtils.getBukkitItemStack(bukkitItemStack));
            }
        });

        InventoryHolderCache.cache(player, this);
    }

    private void createItemsPanel() {
        int cornerA = cache.getItems().getCornerA();
        int cornerB = cache.getItems().getCornerB();

        int columnStart = (cornerA+1) / 9;
        int columnEnd = (cornerB+1) / 9;

        int columnLength = Math.abs(columnStart - columnEnd) + 1;
        int rowLength = Math.abs(cornerA - (cornerB - ((columnLength-1) * 9))) + 1;

        ListUtils<ShopItem> listUtils = new ListUtils<>(category.getOrderedItemList());

        int itemCount = 0;

        for (int y = 0; y < columnEnd; y++) {
            for (int x = cornerA; x < rowLength+cornerA; x++) {
                ShopItem item = listUtils.getNext();
                if (item == null) continue;
                //inventory.setItem(x+(y*9), UserInterfaceUtils.getBukkitItemStack(item));
                mappedInventory.put(x+(y*9), item);
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
    public void onRightClick(int slot) {
        Object object = mappedInventory.get(slot);
        if (!UserInterfaceUtils.isClickable(object)) return;

        if (object instanceof UIButton) {
            Clickable button = (UIButton) object;
            buttonHandler(button.rightClick());
            return;
        }
    }

    @Override
    public void onLeftClick(int slot) {
        Object object = mappedInventory.get(slot);
        if (!UserInterfaceUtils.isClickable(object)) return;

        if (object instanceof UIButton) {
            Clickable button = (UIButton) object;
            buttonHandler(button.leftClick());
            return;
        }
    }

    @Override
    public void onMiddleClick(int slot) {
        Object object = mappedInventory.get(slot);
        if (!UserInterfaceUtils.isClickable(object)) return;

        if (object instanceof UIButton) {
            Clickable button = (UIButton) object;
            buttonHandler(button.middleClick());
            return;
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
}
