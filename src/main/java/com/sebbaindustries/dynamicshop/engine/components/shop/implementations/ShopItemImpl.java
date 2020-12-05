package com.sebbaindustries.dynamicshop.engine.components.shop.implementations;

import com.moandjiezana.toml.TomlWriter;
import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopItem;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopMeta;
import org.bukkit.Material;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ShopItemImpl implements ShopItem {

    public ShopItemImpl(ItemStack iStack, ShopMeta meta) {
        this.meta = meta;
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
    private List<String> lore;
    private String displayName;
    private HashMap<String, Integer> enchants;
    private ShopMeta meta;


    @Override
    public ItemStack getBukkitItemStack() {
        ItemStack iStack = new ItemStack(material);

        ItemMeta iMeta = iStack.getItemMeta();
        if (lore != null) iMeta.setLore(lore);
        if (displayName != null) iMeta.setDisplayName(displayName);
        if (enchants.size() > 0) enchants.forEach((enchant, val) -> iMeta.addEnchant(new EnchantmentWrapper(enchant), val, true));

        iStack.setItemMeta(iMeta);
        return iStack;
    }

    @Override
    public ShopMeta getShopMeta() {
        return this.meta;
    }

    @Override
    public void setShopMeta(ShopMeta meta) {
        this.meta = meta;
    }

    @Override
    public void serialize() {
        TomlWriter writer = new TomlWriter.Builder()
                .indentValuesBy(2)
                .indentTablesBy(4)
                .build();
        try {
            writer.write(this, new File(Core.gCore().core.getDataFolder() + "/" + material.name().toLowerCase() + ".toml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
