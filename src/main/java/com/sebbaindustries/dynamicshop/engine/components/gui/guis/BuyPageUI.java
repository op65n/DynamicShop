package com.sebbaindustries.dynamicshop.engine.components.gui.guis;

import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.engine.components.gui.cache.BuyPageUICache;
import com.sebbaindustries.dynamicshop.engine.components.gui.cache.InventoryHolderCache;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.ClickActions;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.UIBackground;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.UIButton;
import com.sebbaindustries.dynamicshop.engine.components.gui.interfaces.BukkitItemStack;
import com.sebbaindustries.dynamicshop.engine.components.gui.interfaces.Clickable;
import com.sebbaindustries.dynamicshop.engine.components.gui.interfaces.UserInterface;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopCategory;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopItem;
import com.sebbaindustries.dynamicshop.utils.Color;
import com.sebbaindustries.dynamicshop.utils.UserInterfaceUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Map;
import java.util.TreeMap;

public class BuyPageUI implements UserInterface {

    public BuyPageUI(Player player, ShopItem item, ShopCategory backPage) {
        this.player = player;
        this.cache = Core.gCore().getEngine().instance().getShopUI().getBuyPageCache();
        this.selectedItem = item;
        this.backPage = backPage;

        inventory = Bukkit.createInventory(null, cache.getSize() * 9, Color.format(cache.getName()));

        // Update/flush cache
        InventoryHolderCache.cache(player, this);
    }

    private Player player;
    private Inventory inventory;
    private BuyPageUICache cache;
    private Map<Integer, Object> mappedInventory = new TreeMap<>();
    private ShopItem selectedItem;
    private int itemAmount = 1;
    private ShopCategory backPage;

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
        // TODO: Background an all UI's
        UIBackground background = cache.getBackground();
        if (background != null && background.getMaterial() != null) {
            for (int i = 0; i < cache.getSize() * 9; i++) {
                inventory.setItem(i, UserInterfaceUtils.getBukkitItemStack(background));
                mappedInventory.put(i, background);
            }
        }

        /*
        Buttons
         */
        cache.getButton().forEach(button -> {
            inventory.setItem(button.getSlot(), UserInterfaceUtils.getBukkitItemStack(button));
            mappedInventory.put(button.getSlot(), button);
        });

        /*
        Item
         */
        selectedItem.setAmount(itemAmount);
        mappedInventory.put(cache.getItemSlot(), selectedItem);

        int newSize = 0;

        for (Map.Entry<Integer, Object> entry : mappedInventory.entrySet()) {
            if (entry.getValue() instanceof UIBackground) continue;
            if (newSize < entry.getKey()) newSize = entry.getKey();
        }

        newSize = (int) Math.ceil((double) newSize / 9.0);

        cache.setSize(newSize);

        inventory = Bukkit.createInventory(null, cache.getSize() * 9, Color.format(cache.getName()));
        mappedInventory.forEach((slot, item) -> {
            if (slot > cache.getSize()*9-1) return;
            if (item instanceof BukkitItemStack) {
                BukkitItemStack bukkitItemStack = (BukkitItemStack) item;
                inventory.setItem(slot, UserInterfaceUtils.getBukkitItemStack(bukkitItemStack));
            }
        });

        player.openInventory(inventory);
        InventoryHolderCache.cache(player, this);
    }

    @Override
    public void updateUISlots() {
        selectedItem.setAmount(itemAmount);
        mappedInventory.put(cache.getItemSlot(), selectedItem);
        mappedInventory.forEach((slot, item) -> {
            if (slot > cache.getSize()*9-1) return;
            if (item instanceof BukkitItemStack) {
                BukkitItemStack bukkitItemStack = (BukkitItemStack) item;
                player.getOpenInventory().setItem(slot, UserInterfaceUtils.getBukkitItemStack(bukkitItemStack));
            }
        });
        player.updateInventory();
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
            buttonHandler(button.rightClick(), slot);
            return;
        }
    }

    @Override
    public void onLeftClick(int slot) {
        Object object = mappedInventory.get(slot);
        if (!UserInterfaceUtils.isClickable(object)) return;
        if (object instanceof UIButton) {
            Clickable button = (UIButton) object;
            buttonHandler(button.leftClick(), slot);
            return;
        }
    }

    @Override
    public void onMiddleClick(int slot) {
        Object object = mappedInventory.get(slot);
        if (!UserInterfaceUtils.isClickable(object)) return;
        if (object instanceof UIButton) {
            Clickable button = (UIButton) object;
            buttonHandler(button.middleClick(), slot);
            return;
        }
    }

    private void buttonHandler(ClickActions action, int slot) {
        switch (action) {
            case EXIT, CLOSE -> close();
            case BACK -> {
                UserInterface ui = new StorePageUI(player, backPage);
                ui.update();
                ui.open();
            }
            case ADD -> {
                UIButton button = (UIButton) mappedInventory.get(slot);
                if (button.getAmount() + itemAmount <= button.getMaterial().getMaxStackSize()) {
                    itemAmount += button.getAmount();
                }
                updateUISlots();
            }
            case REMOVE -> {
                UIButton button = (UIButton) mappedInventory.get(slot);
                if (itemAmount - button.getAmount() > 0) {
                    itemAmount -= button.getAmount();
                }
                updateUISlots();
            }
            case SET -> {
                UIButton button = (UIButton) mappedInventory.get(slot);
                itemAmount = button.getAmount();
                updateUISlots();
            }
        }
    }

}
