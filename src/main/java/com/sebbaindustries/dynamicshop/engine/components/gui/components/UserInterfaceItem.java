package com.sebbaindustries.dynamicshop.engine.components.gui.components;

import com.sebbaindustries.dynamicshop.utils.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Deprecated
public class UserInterfaceItem {


    private Material material;
    private List<String> lore = new ArrayList<>();
    private String displayName;
    private HashMap<String, Integer> enchants;


    public ItemStack getBukkitItemStack() {
        // Create new ItemStack instance
        ItemStack iStack = new ItemStack(getBukkitMaterial());
        // Setup meta
        ItemMeta iMeta = iStack.getItemMeta();

        /*
        Item lore
         */
        List<String> coloredLore = new ArrayList<>();
        lore.forEach(loreLine -> coloredLore.add(Color.format(loreLine)));
        if (lore != null && !lore.isEmpty()) iMeta.setLore(coloredLore);

        /*
        Item display name
         */
        if (displayName != null) iMeta.setDisplayName(Color.format(displayName));

        /*
        Item enchants
         */
        if (enchants != null && !enchants.isEmpty()) enchants.forEach(
                (enchant, val) -> iMeta.addEnchant(new EnchantmentWrapper(enchant), val, true)
        );

        iStack.setItemMeta(iMeta);
        return iStack;
    }

    /**
     * Returns constructed and bukkit material, if material is not present it returns a boat.
     *
     * @return Bukkit Material
     */
    public Material getBukkitMaterial() {
        if (material == null) {
            // Add some info if material is null.
            displayName = Color.format("&4&lERROR, null Material instance");
            lore.clear();
            lore.add("Check configuration, if you cannot solve this error contact Nzd_1");
            return Material.ACACIA_BOAT;
        }
        return this.material;
    }

}
