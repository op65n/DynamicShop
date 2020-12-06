package com.sebbaindustries.dynamicshop.engine.components.shop;

import com.sebbaindustries.dynamicshop.engine.components.shop.implementations.ShopItemImpl;

import java.util.HashMap;

public interface ShopCategory {

    String getUUID();
    String getName();
    HashMap<Integer, ShopItemImpl> getItems();

}
