package com.sebbaindustries.dynamicshop.engine.components.shop;


import com.moandjiezana.toml.Toml;
import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.engine.components.shop.implementations.ShopItemImpl;
import org.bukkit.inventory.ItemStack;

import java.io.File;

public interface ShopItem extends com.sebbaindustries.dynamicshop.engine.structure.Serializable {

    ItemStack getBukkitItemStack();

    ShopMeta getShopMeta();
    void setShopMeta(ShopMeta meta);

    static ShopItem deserializeUnsafe(String file) {
        Toml toml = new Toml().read(new File(Core.gCore().core.getDataFolder() + "/" + file + ".toml"));

        return new ShopItemImpl.UnsafeComponentBuilder().build(toml, file + ".toml");
    }

    static ShopItem deserialize(String file) {
        return new Toml().read(new File(Core.gCore().core.getDataFolder() + "/" + file + ".toml")).to(ShopItemImpl.class);
    }
}
