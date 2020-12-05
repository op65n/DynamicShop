package com.sebbaindustries.dynamicshop.engine.components.shop;


import com.moandjiezana.toml.Toml;
import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.engine.components.shop.implementations.ShopItemImpl;
import org.bukkit.inventory.ItemStack;

public interface ShopItem extends com.sebbaindustries.dynamicshop.engine.structure.Serializable {

    ItemStack getBukkitItemStack();

    ShopMeta getShopMeta();
    void setShopMeta(ShopMeta meta);

    static ShopItem deserialize(String file) {
        Toml toml = new Toml().read(Core.gCore().core.getDataFolder() + "/" + file + ".toml");

        return new ShopItemImpl.UnsafeComponentBuilder().build(toml, file + ".toml");
    }
}
