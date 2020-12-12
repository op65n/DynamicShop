package com.sebbaindustries.dynamicshop.engine.components.shop;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public interface ShopItem {

    ItemStack getBukkitItemStack();

    Material getBukkitMaterial();

    Double buyPrice();

    void setBuyPrice(Double price);

    Double sellPrice();

    void setSellPrice(Double price);

    ShopMeta getShopMeta();

    void setShopMeta(ShopMeta meta);
}
