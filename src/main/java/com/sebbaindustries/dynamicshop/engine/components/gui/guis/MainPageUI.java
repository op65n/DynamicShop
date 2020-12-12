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

    public MainPageUI(Player player) {
        this.player = player;

        UICache cache = Core.gCore().dynEngine.getShopUI().getMainPageCache();
        metaData = UserInterfaceUtils.setupMetaData(cache);
        background = UserInterfaceUtils.setupBackground(cache);
        inventorySlots = UserInterfaceUtils.setupBaseItemOrder(cache);
        inventory = UserInterfaceUtils.updateGUIFrame(metaData, inventorySlots, background);

        // Update/flush cache
        InventoryHolderCache.cache(player, this);
    }

    private final Player player;
    private Inventory inventory;

    private UIMetaData metaData;
    private final HashMap<Integer, UserInterfaceItem> inventorySlots;
    private final HashMap<Integer, ShopCategory> categories = new HashMap<>();
    private final UserInterfaceItem background;

    @Override
    public void open() {
        player.openInventory(inventory);
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
        Core.gCore().dynEngine.getContainer().getPrioritizedCategoryList().forEach(category -> {
            for (int slot : inventorySlots.keySet()) {
                UserInterfaceItem item = inventorySlots.get(slot);
                if (!item.isPlaceholder()) continue;
                item.setPlaceholder(false);
                item.setMaterial(category.icon().getIcon());
                item.setLore(category.icon().getLore());
                item.setDisplayName(category.getName());

                inventorySlots.put(slot, item);
                categories.put(slot, category);
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
            case CANCEL, CLOSE, BACK -> close();
            case OPEN -> {
                UserInterface ui = new StorePageUI(player, categories.get(slot));
                // Changing placeholder to a category name
                UIMetaData uiMetaData = ui.getMetaData();
                uiMetaData.setTitle(uiMetaData.getTitle().replace("%category%", categories.get(slot).getName()));
                ui.setMetaData(uiMetaData);
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
