package com.sebbaindustries.dynamicshop.engine.components.shop;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Icon {

    private Material icon = Material.ACACIA_BOAT;
    private List<String> lore = new ArrayList<>();
    private String displayName;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public Material getIcon() {
        return icon;
    }

    public void setIcon(Material icon) {
        this.icon = icon;
    }
}
