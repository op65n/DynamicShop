package com.sebbaindustries.dynamicshop.engine.components.shop.implementations;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopItem;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopMeta;
import com.sebbaindustries.dynamicshop.log.PluginLogger;
import org.bukkit.Material;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopItemImpl implements ShopItem {

    private ShopItemImpl() {

    }

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

    public String generateFileName() {
        return material.name().toLowerCase();
    }


    @Override
    public ItemStack getBukkitItemStack() {
        ItemStack iStack = new ItemStack(material);

        ItemMeta iMeta = iStack.getItemMeta();
        if (lore != null) iMeta.setLore(lore);
        if (displayName != null) iMeta.setDisplayName(displayName);
        if (enchants != null) enchants.forEach((enchant, val) -> iMeta.addEnchant(new EnchantmentWrapper(enchant), val, true));

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
                .indentTablesBy(4)
                .build();
        try {
            writer.write(this, new File(Core.gCore().core.getDataFolder() + "/" + material.name().toLowerCase() + ".toml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class UnsafeComponentBuilder {

        private Toml toml;
        private String file;
        private ShopItemImpl item = new ShopItemImpl();

        public ShopItemImpl build(Toml toml, String file) {
            this.toml = toml;
            this.file = file;
            item.material = getMaterial();
            setLore();
            setDisplayName();
            setEnchants();
            setMeta();

            return item;
        }

        private Material getMaterial() {
            String materialTomlString = toml.getString("material");
            if (materialTomlString == null) {
                materialTomlString = "DIRT";
                PluginLogger.logSevere("Null material string in file " + file);
            }
            Material material = Material.matchMaterial(materialTomlString);
            if (material == null) {
                material = Material.DIRT;
                PluginLogger.logSevere("Null material match string in file " + file);
            }
            return material;
        }

        private void setLore() {
            List<String> lore = toml.getList("lore");
            if (lore == null) return;
            item.lore = lore;
        }

        private void setDisplayName() {
            String displayName = toml.getString("displayName");
            if (displayName == null) return;
            item.displayName = displayName;
        }

        private void setEnchants() {
            Map<String, Object> enchantObjects;
            try {
                enchantObjects = toml.getTable("enchants").toMap();
            } catch (NullPointerException e) {
                return;
            }
            if (enchantObjects == null) return;
            HashMap<String, Integer> enchants = new HashMap<>();
            // Don't even dare to ask why this is here..
            enchantObjects.forEach((enchant, object) -> enchants.put(enchant, Math.toIntExact((Long) object)));
            item.enchants = enchants;
        }

        private void setMeta() {
            ShopMeta meta = toml.getTable("meta").to(ShopMeta.class);
            if (meta == null) {
                meta = new ShopMeta(null, null);
                PluginLogger.logSevere("Null meta in file " + file);
            }
            item.meta = meta;
        }
    }

}
