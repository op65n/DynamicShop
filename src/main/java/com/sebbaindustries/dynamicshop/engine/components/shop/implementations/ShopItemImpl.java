package com.sebbaindustries.dynamicshop.engine.components.shop.implementations;

import com.sebbaindustries.dynamicshop.engine.components.shop.ShopItem;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopMeta;
import com.sebbaindustries.dynamicshop.log.PluginLogger;
import org.bukkit.Material;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;

public class ShopItemImpl implements ShopItem {

    public ShopItemImpl(ItemStack iStack) {
        this.material = iStack.getType();

        if (!iStack.hasItemMeta()) return;

        ItemMeta iMeta = iStack.getItemMeta();
        if (iMeta.hasLore()) this.lore = iMeta.getLore();
        if (iMeta.hasDisplayName()) this.displayName = iMeta.getDisplayName();
        if (iMeta.hasEnchants()) {
            enchants = new HashMap<>();
            iMeta.getEnchants().forEach((enchant, val) -> this.enchants.put(enchant.getKey().getKey(), val));
        }

    }

    private Material material;
    private Double buyPrice;
    private Double sellPrice;
    private List<String> lore;
    private String displayName;
    private HashMap<String, Integer> enchants;
    private ShopMeta meta;


    @Override
    public ItemStack getBukkitItemStack() {
        ItemStack iStack = new ItemStack(Material.ACACIA_BOAT);

        ItemMeta iMeta = iStack.getItemMeta();
        if (lore != null) iMeta.setLore(lore);
        if (displayName != null) iMeta.setDisplayName(displayName);
        if (enchants != null) enchants.forEach((enchant, val) -> iMeta.addEnchant(new EnchantmentWrapper(enchant), val, true));

        iStack.setItemMeta(iMeta);
        return iStack;
    }

    @Override
    public Material getBukkitMaterial() {
        if (material == null) {
            PluginLogger.logSevere("Null item");
            return Material.ACACIA_BOAT;
        }
        return this.material;
    }

    @Override
    public Double buyPrice() {
        return this.buyPrice;
    }

    @Override
    public void setBuyPrice(Double price) {
        this.buyPrice = price;
    }

    @Override
    public Double sellPrice() {
        return this.sellPrice;
    }

    @Override
    public void setSellPrice(Double price) {
        this.sellPrice = price;
    }

    @Override
    public ShopMeta getShopMeta() {
        if (meta == null) return new ShopMeta();
        return this.meta;
    }

    @Override
    public void setShopMeta(ShopMeta meta) {
        this.meta = meta;
    }

}
