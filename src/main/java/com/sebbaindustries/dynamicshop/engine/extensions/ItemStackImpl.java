package com.sebbaindustries.dynamicshop.engine.extensions;

import org.bukkit.Material;
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
            this.itemDisplayName = iMeta.getDisplayName();
            iMeta.getEnchants().forEach((enchant, val) -> this.itemEnchants.put(enchant.getKey().getKey(), val));
        }
    }

    /*
    BUKKIT Item implementation
     */
    private Material material;
    private List<String> lore;
    private String itemDisplayName;
    Map<String, Integer> itemEnchants = new HashMap<>();

}
