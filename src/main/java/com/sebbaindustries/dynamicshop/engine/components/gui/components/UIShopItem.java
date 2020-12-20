package com.sebbaindustries.dynamicshop.engine.components.gui.components;

import com.sebbaindustries.dynamicshop.engine.components.gui.interfaces.BukkitItemStack;
import com.sebbaindustries.dynamicshop.engine.components.gui.interfaces.Clickable;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class UIShopItem implements BukkitItemStack, Clickable {

    private Material material = Material.ACACIA_BOAT;
    private String display;
    private List<String> lore = new ArrayList<>();

    @Override
    public Material material() {
        return this.material;
    }

    @Override
    public String display() {
        return this.display;
    }

    @Override
    public List<String> lore() {
        return this.lore;
    }

    @Override
    public ClickActions rightClick() {
        return ClickActions.NA;
    }

    @Override
    public ClickActions leftClick() {
        return ClickActions.NA;
    }

    @Override
    public ClickActions middleClick() {
        return ClickActions.NA;
    }
}
