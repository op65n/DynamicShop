package com.sebbaindustries.dynamicshop.engine.components.gui.guis;

import com.rits.cloning.Cloner;
import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.engine.components.gui.cache.InventoryHolderCache;
import com.sebbaindustries.dynamicshop.engine.components.gui.cache.UICache;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.UIAction;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.UIMetaData;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.UserInterface;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.UserInterfaceItem;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopCategory;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopItem;
import com.sebbaindustries.dynamicshop.utils.ObjectUtils;
import com.sebbaindustries.dynamicshop.utils.UserInterfaceUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class StorePageUI implements UserInterface {

    public StorePageUI(Player player, ShopCategory category) {
        this.player = player;
        this.category = category;

        UICache cache = Core.gCore().dynEngine.getShopUI().getStorePageCache();
        metaData = UserInterfaceUtils.setupMetaData(cache);
        background = UserInterfaceUtils.setupBackground(cache);
        inventorySlots = UserInterfaceUtils.setupBaseItemOrder(cache);
        createShopArea();
        inventory = UserInterfaceUtils.updateGUIFrame(metaData, inventorySlots, background);

        // Update/flush cache
        InventoryHolderCache.cache(player, this);
    }

    private final Player player;
    private Inventory inventory;

    private final ShopCategory category;
    private UIMetaData metaData;
    private Map<Integer, UserInterfaceItem> inventorySlots;
    private final UserInterfaceItem background;


    private void createShopArea() {
        int pos1 = -1;
        int pos2 = -1;
        for (Map.Entry<Integer, UserInterfaceItem> item : inventorySlots.entrySet()) {
            if (item.getValue().isPlaceholder()) {
                if (pos1 == -1) {
                    pos1 = item.getKey();
                    continue;
                }
                pos2 = item.getKey();
                break;
            }
        }

        Cloner cloner = new Cloner();
        for (int i = pos1; i < pos2; i++) {
            inventorySlots.put(i, cloner.deepClone(inventorySlots.get(pos1)));
        }
    }

    @Override
    public void open() {
        player.openInventory(inventory);
        InventoryHolderCache.cache(player, this);
    }

    @Override
    public void update() {
        inventory = UserInterfaceUtils.updateGUIFrame(metaData, inventorySlots, background);
        fillItems();
        inventorySlots.forEach((position, item) -> inventory.setItem(position, item.getBukkitItemStack()));
        InventoryHolderCache.cache(player, this);
    }

    private void fillItems() {
        Map<Integer, ShopItem> sortedItemsMap = new TreeMap<>(category.getItems());

        sortedItemsMap.forEach((priority, item) -> {
            for (int slot : inventorySlots.keySet()) {
                UserInterfaceItem uiItem = inventorySlots.get(slot);
                if (!uiItem.isPlaceholder()) continue;
                uiItem.setPlaceholder(false);
                uiItem.setMaterial(item.getBukkitMaterial());
                uiItem.setDisplayName(item.getBukkitMaterial().name());

                inventorySlots.put(slot, uiItem);
                break;
            }
        });
    }


    @Override
    public void close() {
        player.closeInventory();
        InventoryHolderCache.removeIfPresent(player);
    }

    @Override
    public void onRightClick(int slot) {
        if (inventorySlots.get(slot) == null || inventorySlots.get(slot).isPlaceholder()) return;
        UIAction.Actions action = inventorySlots.get(slot).getOnRightClick().get();
        preformClick(action, slot);
    }

    @Override
    public void onLeftClick(int slot) {
        if (inventorySlots.get(slot) == null || inventorySlots.get(slot).isPlaceholder()) return;
        UIAction.Actions action = inventorySlots.get(slot).getOnLeftClick().get();
        preformClick(action, slot);
    }

    @Override
    public void onMiddleClick(int slot) {
        if (inventorySlots.get(slot) == null || inventorySlots.get(slot).isPlaceholder()) return;
        UIAction.Actions action = inventorySlots.get(slot).getOnMiddleClick().get();
        preformClick(action, slot);
    }

    private void preformClick(UIAction.Actions action, int slot) {
        if (action == null) return;
        switch (action) {
            case CANCEL, CLOSE -> close();
            case BACK -> {
                UserInterface ui = new MainPageUI(player);
                ui.update();
                ui.open();
            }
        }
    }

    @Override
    public void setMetaData(UIMetaData metaData) {
        this.metaData = metaData;
    }

    @Override
    public UIMetaData getMetaData() {
        return this.metaData;
    }
}
