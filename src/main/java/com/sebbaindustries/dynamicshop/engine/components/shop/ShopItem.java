package com.sebbaindustries.dynamicshop.engine.components.shop;

import com.sebbaindustries.dynamicshop.engine.components.gui.components.ClickActions;
import com.sebbaindustries.dynamicshop.engine.components.gui.interfaces.BukkitItemStack;
import com.sebbaindustries.dynamicshop.engine.components.gui.interfaces.Clickable;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.util.List;

@Getter
@Setter
public class ShopItem implements BukkitItemStack, Clickable {

    private int priority = 1000;

    private Material material = Material.ACACIA_BOAT;
    private String display;
    private List<String> lore;
    private Double buyPrice;
    private Double sellPrice;

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
