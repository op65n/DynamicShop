package com.sebbaindustries.dynamicshop.engine.components.shop;

import com.sebbaindustries.dynamicshop.engine.components.gui.components.UserInterfaceItem;

import java.util.HashMap;
import java.util.UUID;

public interface ShopCategory {

    int priority();
    UUID getUUID();
    String getName();
    UserInterfaceItem icon();
    HashMap<Integer, ShopItem> getItems();

}
