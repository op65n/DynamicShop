package com.sebbaindustries.dynamicshop.engine.extensions;

import org.bukkit.Material;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemStackImpl {

    public ItemStackImpl(ItemStack iStack) {
        this.material = iStack.getType();
        if (iStack.hasItemMeta()) {
            ItemMeta iMeta = iStack.getItemMeta();
            this.lore = iMeta.getLore();
            this.displayName = iMeta.getDisplayName();
            iMeta.getEnchants().forEach((enchant, val) -> this.enchants.put(enchant.getKey().getKey(), val));
        }
    }

    /*
    BUKKIT Item implementation
     */
    private Material material;
    private List<String> lore;
    private String displayName;
    Map<String, Integer> enchants = new HashMap<>();

    public ItemStack getBukkitItemStack() {
        ItemStack iStack = new ItemStack(material);

        ItemMeta iMeta = iStack.getItemMeta();
        if (lore != null) iMeta.setLore(lore);
        if (displayName != null) iMeta.setDisplayName(displayName);
        if (enchants.size() > 0) enchants.forEach((enchant, val) -> iMeta.addEnchant(new EnchantmentWrapper(enchant), val, true));

        iStack.setItemMeta(iMeta);
        return iStack;
    }

}
