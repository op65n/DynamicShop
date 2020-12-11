package com.sebbaindustries.dynamicshop.engine.components.shop;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Icon {

    private Material material = Material.ACACIA_BOAT;
    private List<String> lore = new ArrayList<>();

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public Material getIcon() {
        return material;
    }

    public void setIcon(Material icon) {
        this.material = icon;
    }
}
