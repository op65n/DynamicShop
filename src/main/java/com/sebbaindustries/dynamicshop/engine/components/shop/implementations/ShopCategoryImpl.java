package com.sebbaindustries.dynamicshop.engine.components.shop.implementations;

import com.sebbaindustries.dynamicshop.engine.components.gui.components.UserInterfaceItem;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopCategory;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopItem;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class ShopCategoryImpl implements ShopCategory {

    int priority = -1;
    private String name = "GenericShopName";
    private UserInterfaceItem icon;
    private HashMap<Integer, ShopItemImpl> items = new HashMap<>();

    @Override
    public int priority() {
        return this.priority;
    }

    @Override
    public UUID getUUID() {
        return UUID.nameUUIDFromBytes(name.getBytes());
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public UserInterfaceItem icon() {
        return icon;
    }

    @Override
    public HashMap<Integer, ShopItem> getItems() {
        return (HashMap<Integer, ShopItem>) items.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, o -> (ShopItem) o.getValue()));
    }
}
