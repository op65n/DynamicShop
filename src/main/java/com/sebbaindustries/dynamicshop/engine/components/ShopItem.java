package com.sebbaindustries.dynamicshop.engine.components;

import com.moandjiezana.toml.TomlWriter;
import com.sebbaindustries.dynamicshop.engine.extensions.ItemStackImpl;
import com.sebbaindustries.dynamicshop.engine.extensions.ShopMeta;
import com.sebbaindustries.dynamicshop.engine.structure.Serializable;
import org.bukkit.inventory.ItemStack;

public class ShopItem implements Serializable<ShopItem> {

    public ShopItem(ItemStackImpl itemStack, ShopMeta shopMeta) {
        this.itemStack = itemStack;
        this.shopMeta = shopMeta;
    }

    public ShopItem(ItemStack iStack, double buyPrice, double sellPrice) {
        this.itemStack = new ItemStackImpl(new ItemStack(iStack));
        this.shopMeta = new ShopMeta(buyPrice, sellPrice);
    }

    private ItemStackImpl itemStack;
    private ShopMeta shopMeta;

    public ItemStackImpl getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStackImpl itemStack) {
        this.itemStack = itemStack;
    }

    public ShopMeta getShopMeta() {
        return shopMeta;
    }

    public void setShopMeta(ShopMeta shopMeta) {
        this.shopMeta = shopMeta;
    }

    @Override
    public void serialize(String fileName) {
        TomlWriter tomlWriter = new TomlWriter.Builder()
                .indentValuesBy(2)
                .indentTablesBy(4)
                .padArrayDelimitersBy(3)
                .build();

    }

    @Override
    public ShopItem deserialize() {
        return null;
    }
}
