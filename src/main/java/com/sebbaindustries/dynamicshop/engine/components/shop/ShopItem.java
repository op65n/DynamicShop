package com.sebbaindustries.dynamicshop.engine.components.shop;


import org.bukkit.inventory.ItemStack;

public interface ShopItem extends com.sebbaindustries.dynamicshop.engine.structure.Serializable {

    ItemStack getBukkitItemStack();

    ShopMeta getShopMeta();
    void setShopMeta(ShopMeta meta);

    static ShopItem deserialize() {
        return null;
    }
}
