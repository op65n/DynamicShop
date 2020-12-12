package com.sebbaindustries.dynamicshop.engine.components.shop.implementations;

import com.sebbaindustries.dynamicshop.engine.components.shop.Icon;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopCategory;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopItem;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ShopCategoryImpl implements ShopCategory {

    private final int priority = -1;
    private final String name = "GenericShopName";
    private Icon icon;
    private final HashMap<Integer, ShopItemImpl> items = new HashMap<>();

    @Override
    public int priority() {
        return this.priority;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Icon icon() {
        return icon;
    }

    @Override
    public HashMap<Integer, ShopItem> getItems() {
        return (HashMap<Integer, ShopItem>) items.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, o -> (ShopItem) o.getValue()));
    }
}
