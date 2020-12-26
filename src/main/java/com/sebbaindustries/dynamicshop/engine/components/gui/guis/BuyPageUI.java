package com.sebbaindustries.dynamicshop.engine.components.gui.guis;

import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.engine.components.gui.cache.BuyPageUICache;
import com.sebbaindustries.dynamicshop.engine.components.gui.cache.InventoryHolderCache;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.ClickActions;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.UIButton;
import com.sebbaindustries.dynamicshop.engine.components.gui.interfaces.Clickable;
import com.sebbaindustries.dynamicshop.engine.components.gui.interfaces.UserInterface;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopCategory;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopItem;
import com.sebbaindustries.dynamicshop.utils.Color;
import com.sebbaindustries.dynamicshop.utils.UserInterfaceUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

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

    private final Player player;
    private Inventory inventory;
    private final BuyPageUICache cache;
    private Map<Integer, Object> mappedInventory = new TreeMap<>();
    private ShopItem selectedItem;
    private final ShopCategory backPage;

    @Override
    public void open() {
        player.openInventory(inventory);
        InventoryHolderCache.cache(player, this);
    }

    @Override
    public void update() {
        updateUISlots(false);

        cache.setSize(UserInterfaceUtils.calculateInventorySize(mappedInventory));

        inventory = Bukkit.createInventory(null, cache.getSize() * 9, Color.format(cache.getName()));
        UserInterfaceUtils.setupInventory(inventory, mappedInventory, cache.getSize());

        InventoryHolderCache.cache(player, this);
    }

    @Override
    public void updateUISlots(boolean updateCurrent) {
        mappedInventory.clear();

        /*
        background
         */
        mappedInventory = UserInterfaceUtils.createBackground(cache.getBackground(), cache.getSize());

        /*
        buttons
         */
        cache.getButton().forEach(button -> {
            if (button.getOnClick() == ClickActions.SET && button.getAmount() == selectedItem.getAmount()) return;
            if (button.getOnClick() == ClickActions.ADD && button.getAmount() + selectedItem.getAmount() > selectedItem.getMaterial().getMaxStackSize()) return;
            if (button.getOnClick() == ClickActions.REMOVE && selectedItem.getAmount() - button.getAmount() < 1) return;
            mappedInventory.put(button.getSlot(), button);
        });

        /*
        item
         */
        selectedItem.setDisplay(Color.format(cache.getItem().getDisplay()));
        selectedItem.setLore(cache.getItem().getColoredLore());
        UserInterfaceUtils.applyItemPlaceholders(selectedItem);
        mappedInventory.put(cache.getItem().getSlot(), selectedItem);

        InventoryHolderCache.cache(player, this);

        if (!updateCurrent) return;
        UserInterfaceUtils.clearUI(player);
        UserInterfaceUtils.fillInventory(player, mappedInventory, cache.getSize());
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
                case RIGHT -> buttonHandler(button.rightClick(), slot);
                case LEFT -> buttonHandler(button.leftClick(), slot);
                case MIDDLE -> buttonHandler(button.middleClick(), slot);
            }
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
                if (button.getAmount() + selectedItem.getAmount() <= button.getMaterial().getMaxStackSize()) {
                    selectedItem.setAmount(selectedItem.getAmount() + button.getAmount());
                }
                updateUISlots(true);
            }
            case REMOVE -> {
                UIButton button = (UIButton) mappedInventory.get(slot);
                if (selectedItem.getAmount() - button.getAmount() > 0) {
                    selectedItem.setAmount(selectedItem.getAmount() - button.getAmount());
                }
                updateUISlots(true);
            }
            case SET -> {
                UIButton button = (UIButton) mappedInventory.get(slot);
                selectedItem.setAmount(button.getAmount());
                updateUISlots(true);
            }
        }
    }

}
