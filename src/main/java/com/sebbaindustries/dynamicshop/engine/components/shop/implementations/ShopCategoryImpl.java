package com.sebbaindustries.dynamicshop.engine.components.shop.implementations;

import com.sebbaindustries.dynamicshop.engine.components.shop.ShopCategory;

import java.util.HashMap;
import java.util.UUID;

public class ShopCategoryImpl implements ShopCategory {

    private String name = "GenericShopName";
    private HashMap<Integer, ShopItemImpl> items = new HashMap<>();

    @Override
    public UUID getUUID() {
        return UUID.nameUUIDFromBytes(name.getBytes());
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public HashMap<Integer, ShopItemImpl> getItems() {
        return this.items;
    }
}
