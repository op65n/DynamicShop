package com.sebbaindustries.dynamicshop.engine.components.shop;

import com.sebbaindustries.dynamicshop.engine.components.shop.implementations.ShopItemImpl;

import java.util.HashMap;
import java.util.UUID;

public interface ShopCategory {

    UUID getUUID();
    String getName();
    HashMap<Integer, ShopItemImpl> getItems();

}
