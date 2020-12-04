package com.sebbaindustries.dynamicshop.engine.components;

import com.destroystokyo.paper.Namespaced;
import com.google.common.collect.Multimap;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Deprecated
public class MockItemStack {

    Material material;

    boolean hasMeta = false;

    String displayName = null;
    boolean hasCustomModelData = false;
    int customModelData;
    Multimap<Attribute, AttributeModifier> attributeModifiers = null;
    Set<Namespaced> destroyableKeys = null;
    Set<Namespaced> placeableKeys = null;
    Map<String, Integer> enchants = new HashMap<>();
    Set<ItemFlag> itemFlags = null;
    String localisedName = null;
    List<String> lore = null;

    public MockItemStack(ItemStack iStack) {
        this.material = iStack.getType();
        if (iStack.hasItemMeta()) {
            hasMeta = true;
            ItemMeta meta = iStack.getItemMeta();
            displayName = meta.getDisplayName();
            if (meta.hasCustomModelData()) {
                hasCustomModelData = true;
                customModelData = meta.getCustomModelData();
            }
            attributeModifiers = meta.getAttributeModifiers();
            destroyableKeys = meta.getDestroyableKeys();
            placeableKeys = meta.getPlaceableKeys();
            itemFlags = meta.getItemFlags();
            localisedName = meta.getLocalizedName();
            lore = meta.getLore();

            Map<Enchantment, Integer> itemEnchants = meta.getEnchants();
            itemEnchants.forEach((enchant, val) -> enchants.put(enchant.getKey().getKey(), val));
        }
    }

    public ItemStack getItemStack() {
        ItemStack iStack = new ItemStack(material);
        if (hasMeta) {
            ItemMeta iMeta = iStack.getItemMeta();
            iMeta.setDisplayName(displayName);
            if (hasCustomModelData) {
                iMeta.setCustomModelData(customModelData);
            }
            iMeta.setAttributeModifiers(attributeModifiers);
            iMeta.setDestroyableKeys(destroyableKeys);
            iMeta.setPlaceableKeys(placeableKeys);
            itemFlags.forEach(iMeta::addItemFlags);
            iMeta.setLocalizedName(localisedName);
            iMeta.setLore(lore);
            enchants.forEach((enchant, val) -> {
                iMeta.addEnchant(new EnchantmentWrapper(enchant), val, true);
            });
            iStack.setItemMeta(iMeta);
        }
        return iStack;
    }

}
