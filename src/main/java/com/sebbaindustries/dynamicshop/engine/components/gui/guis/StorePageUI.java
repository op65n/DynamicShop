package com.sebbaindustries.dynamicshop.engine.components.gui.guis;

import com.sebbaindustries.dynamicshop.engine.components.gui.components.UIMetaData;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.UserInterface;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.UserInterfaceItem;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class StorePageUI implements UserInterface {

    public StorePageUI(ShopCategory category) {

    }

    private Player player;
    private Inventory inventory;
    private UIMetaData metaData;
    private HashMap<Integer, UserInterfaceItem> inventorySlots;
    private final UserInterfaceItem background = null;

    @Override
    public void open(Player player) {

    }

    @Override
    public void update() {
    }


    @Override
    public void close() {

    }

    @Override
    public void onRightClick(int slot) {

    }

    @Override
    public void onLeftClick(int slot) {

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
