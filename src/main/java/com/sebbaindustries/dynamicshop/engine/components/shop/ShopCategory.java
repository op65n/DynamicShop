package com.sebbaindustries.dynamicshop.engine.components.shop;

import java.util.HashMap;

public interface ShopCategory {

    int priority();

    String getName();

    Icon icon();

    HashMap<Integer, ShopItem> getItems();

}
