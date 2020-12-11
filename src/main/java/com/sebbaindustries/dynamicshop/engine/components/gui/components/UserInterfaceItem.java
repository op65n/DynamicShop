package com.sebbaindustries.dynamicshop.engine.components.gui.components;

import com.sebbaindustries.dynamicshop.engine.components.shop.ShopCategory;
import com.sebbaindustries.dynamicshop.log.PluginLogger;
import com.sebbaindustries.dynamicshop.utils.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserInterfaceItem {

    public boolean placeholder = false;

    public UIAction onLeftClick;
    public UIAction onRightClick;
    public UIAction onMiddleClick;


    public Material material;
    public List<String> lore = new ArrayList<>();
    public String displayName;
    private HashMap<String, Integer> enchants;

    public boolean isPlaceholder() {
        return placeholder;
    }

    public ItemStack getBukkitItemStack() {
        ItemStack iStack = new ItemStack(getBukkitMaterial());

        ItemMeta iMeta = iStack.getItemMeta();

        List<String> coloredLore = new ArrayList<>();
        lore.forEach(loreLine -> coloredLore.add(Color.format(loreLine)));

        if (lore != null && !lore.isEmpty()) iMeta.setLore(coloredLore);
        if (displayName != null) iMeta.setDisplayName(Color.format(displayName));
        if (enchants != null && !enchants.isEmpty()) enchants.forEach((enchant, val) -> iMeta.addEnchant(new EnchantmentWrapper(enchant), val, true));
        
        iStack.setItemMeta(iMeta);
        return iStack;
    }
    
    public Material getBukkitMaterial() {
        if (material == null) {
            PluginLogger.logSevere("Null item");
            displayName = Color.format("&4&lERROR!");
            lore.add(Color.format("&cNull item / broken configuration"));
            return Material.ACACIA_BOAT;
        }
        return this.material;
    }
    
}
