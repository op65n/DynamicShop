package com.sebbaindustries.dynamicshop.engine.components.shop.implementations;

import com.sebbaindustries.dynamicshop.engine.components.shop.ShopCategory;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopItem;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public HashMap<Integer, ShopItem> getItems() {
        //HashMap<Integer, ShopItem> integerShopItemHashMap = new HashMap<>();
        //items.forEach((num, item) -> integerShopItemHashMap.put(num, (ShopItem) item));
        //return integerShopItemHashMap;
        return (HashMap<Integer, ShopItem>) items.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, o -> (ShopItem) o.getValue()));
    }
}
