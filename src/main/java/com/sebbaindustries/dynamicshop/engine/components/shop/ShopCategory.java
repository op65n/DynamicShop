package com.sebbaindustries.dynamicshop.engine.components.shop;

import java.util.HashMap;

public interface ShopCategory {

    String getUUID();
    String getName();
    HashMap<Integer, ShopItem> getItems();

}
