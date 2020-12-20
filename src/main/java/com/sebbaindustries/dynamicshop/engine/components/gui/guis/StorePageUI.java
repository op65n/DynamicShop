package com.sebbaindustries.dynamicshop.engine.components.gui.guis;

import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.engine.components.gui.cache.InventoryHolderCache;
import com.sebbaindustries.dynamicshop.engine.components.gui.cache.StorePageUICache;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.ClickActions;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.UIBackground;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.UIButton;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.UIShopItem;
import com.sebbaindustries.dynamicshop.engine.components.gui.interfaces.Clickable;
import com.sebbaindustries.dynamicshop.engine.components.gui.interfaces.UserInterface;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopCategory;
import com.sebbaindustries.dynamicshop.utils.Color;
import com.sebbaindustries.dynamicshop.utils.UserInterfaceUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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

        createItemsPanel();

        InventoryHolderCache.cache(player, this);
    }

    private void createItemsPanel() {
        int cornerA = cache.getItems().getCornerA();
        int cornerB = cache.getItems().getCornerB();

        int collumStart = (cornerA+1) / 9;
        int collumEnd = (cornerB+1) / 9;

        int collumLength = Math.abs(collumStart - collumEnd) + 1;
        int rowLength = Math.abs(cornerA - (cornerB - ((collumLength-1) * 9))) + 1;

        System.out.println("CornerA: " + cornerA);
        System.out.println("CornerB: " + cornerB);
        System.out.println("collumStart: " + collumStart);
        System.out.println("collumEnd: " + collumEnd);
        System.out.println("collumLength: " + collumLength);
        System.out.println("rowLength: " + rowLength);

        for (int x = cornerA; x < rowLength; x++) {
            for (int y = collumStart; y < collumEnd; y++) {
                System.out.println(x*y);
                UIShopItem shopItem = new UIShopItem();
                inventory.setItem(x*y, new ItemStack(Material.ACACIA_BOAT));
                mappedInventory.put(x*y, shopItem);
            }
        }
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
