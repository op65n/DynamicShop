package com.sebbaindustries.dynamicshop.engine.components.shop;

import java.util.HashMap;
import java.util.UUID;

public interface ShopCategory {

    UUID getUUID();
    String getName();
    HashMap<Integer, ShopItem> getItems();

}
