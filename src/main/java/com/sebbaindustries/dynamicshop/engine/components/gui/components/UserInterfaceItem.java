package com.sebbaindustries.dynamicshop.engine.components.gui.components;

import com.sebbaindustries.dynamicshop.log.PluginLogger;
import org.bukkit.Material;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;

public class UserInterfaceItem {

    private Material material;
    private List<String> lore;
    private String displayName;
    private HashMap<String, Integer> enchants;

    public ItemStack getBukkitItemStack() {
        ItemStack iStack = new ItemStack(getBukkitMaterial());

        ItemMeta iMeta = iStack.getItemMeta();
        if (lore != null) iMeta.setLore(lore);
        if (displayName != null) iMeta.setDisplayName(displayName);
        if (enchants != null) enchants.forEach((enchant, val) -> iMeta.addEnchant(new EnchantmentWrapper(enchant), val, true));

        iStack.setItemMeta(iMeta);
        return iStack;
    }
    
    public Material getBukkitMaterial() {
        if (material == null) {
            PluginLogger.logSevere("Null item");
            return Material.ACACIA_BOAT;
        }
        return this.material;
    }
    
}
