package com.sebbaindustries.dynamicshop.engine.components.gui.guis;

import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.engine.components.gui.cache.InventoryHolderCache;
import com.sebbaindustries.dynamicshop.engine.components.gui.cache.UICache;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.UIAction;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.UIMetaData;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.UserInterface;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.UserInterfaceItem;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopCategory;
import com.sebbaindustries.dynamicshop.utils.ObjectUtils;
import com.sebbaindustries.dynamicshop.utils.UserInterfaceUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class MainPageUI implements UserInterface {

    public MainPageUI() {
        UICache cache = Core.gCore().dynEngine.shopUI.getMainPageCache();
        System.out.println(ObjectUtils.deserializeObjectToString(cache));
        metaData = UserInterfaceUtils.setupMetaData(cache);
        background = UserInterfaceUtils.setupBackground(cache);
        inventorySlots = UserInterfaceUtils.setupBaseItemOrder(cache);
        inventory = UserInterfaceUtils.updateGUIFrame(metaData, inventorySlots, background);

        // Update/flush cache
        InventoryHolderCache.cache(player, this);
    }

    private Player player;
    private Inventory inventory;

    private UIMetaData metaData;
    private final HashMap<Integer, UserInterfaceItem> inventorySlots;
    private final HashMap<Integer, ShopCategory> categories = new HashMap<>();
    private final UserInterfaceItem background;

    @Override
    public void open(Player player) {
        player.openInventory(inventory);
        this.player = player;
        InventoryHolderCache.cache(player, this);
    }

    @Override
    public void update() {
        inventory = UserInterfaceUtils.updateGUIFrame(metaData, inventorySlots, background);
        fillCategories();
        inventorySlots.forEach((position, item) -> inventory.setItem(position, item.getBukkitItemStack()));
        InventoryHolderCache.cache(player, this);
    }

    private void fillCategories() {
        // Category list is already ordered when we get it
        Core.gCore().dynEngine.container.getPrioritizedCategoryList().forEach(category -> inventorySlots.entrySet().stream().anyMatch(entry -> {
            if (entry.getValue().isPlaceholder()) {
                // Update item with ShopCategory data
                UserInterfaceItem itm = entry.getValue();
                itm.setPlaceholder(false);
                itm.setMaterial(category.icon().getIcon());
                itm.setLore(category.icon().getLore());
                itm.setDisplayName(category.getName());

                // Update item slot
                inventorySlots.put(entry.getKey(), itm);
                // categories are put into another hashmap for quick access
                categories.put(entry.getKey(), category);
                return true;
            }
            return false;
        }));
    }

    @Override
    public void close() {
        player.closeInventory();
        InventoryHolderCache.removeIfPresent(player);
    }

    @Override
    public void onRightClick(int slot) {
        if (inventorySlots.get(slot) == null) return;
        UIAction.Actions action = inventorySlots.get(slot).getOnRightClick().get();
        if (action == null) return;
        switch (action) {
            case CLOSE -> close();
            case OPEN -> {
                UserInterface ui = new StorePageUI(categories.get(slot));
                ui.update();
                ui.open(player);
            }
        }
    }

    @Override
    public void onLeftClick(int slot) {
        if (inventorySlots.get(slot) == null) return;
        UIAction.Actions action = inventorySlots.get(slot).getOnLeftClick().get();
        if (action == null) return;
        switch (action) {
            case CLOSE -> close();
            case OPEN -> {
                UserInterface ui = new StorePageUI(categories.get(slot));
                ui.update();
                ui.open(player);
            }
        }
    }

    @Override
    public void onMiddleClick(int slot) {

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
