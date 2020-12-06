package com.sebbaindustries.dynamicshop.engine.components.shop.implementations;

import com.sebbaindustries.dynamicshop.engine.components.shop.ShopCategory;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopItem;

import java.util.HashMap;
import java.util.UUID;

public class ShopCategoryImpl implements ShopCategory {

    private String name = "GenericShopName";
    private HashMap<Integer, ShopItem> items = new HashMap<>();

    @Override
    public String getUUID() {
        return UUID.fromString(name).toString();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public HashMap<Integer, ShopItem> getItems() {
        return this.items;
    }

    public void addItem(ShopItem item) {
        int nextInt = 0;
        if (!items.isEmpty()) {
            for (int i : items.keySet()) {
                if (i >= nextInt) {
                    nextInt = i+1;
                }
            }
        }
        items.put(nextInt, item);
    }
}
