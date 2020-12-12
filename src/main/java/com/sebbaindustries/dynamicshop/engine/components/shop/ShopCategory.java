package com.sebbaindustries.dynamicshop.engine.components.shop;

import java.util.HashMap;
import java.util.UUID;

public interface ShopCategory {

    int priority();

    UUID getUUID();

    String getName();

    Icon icon();

    HashMap<Integer, ShopItem> getItems();

}
