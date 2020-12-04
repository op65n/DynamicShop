package com.sebbaindustries.dynamicshop.engine.components;

import com.sebbaindustries.dynamicshop.engine.extensions.ItemStackImpl;
import com.sebbaindustries.dynamicshop.engine.extensions.ShopMeta;
import org.bukkit.inventory.ItemStack;

public class ShopItem {

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
}
