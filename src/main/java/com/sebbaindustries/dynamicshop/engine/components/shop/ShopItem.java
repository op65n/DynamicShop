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
    private String display = "%item%";
    private List<String> lore;
    private Double buyPrice = 0.00;
    private Double sellPrice = 0.00;

    private int amount = 1;

    private ClickActions onClick = ClickActions.NA;
    private ClickActions onRightClick = ClickActions.NA;
    private ClickActions onLeftClick = ClickActions.NA;
    private ClickActions onMiddleClick = ClickActions.NA;

    @Override
    public int amount() {
        return this.amount;
    }

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
        if (this.onRightClick == ClickActions.NA) return this.onClick;
        return this.onRightClick;
    }

    @Override
    public ClickActions leftClick() {
        if (this.onLeftClick == ClickActions.NA) return this.onClick;
        return this.onLeftClick;
    }

    @Override
    public ClickActions middleClick() {
        if (this.onMiddleClick == ClickActions.NA) return this.onClick;
        return this.onMiddleClick;
    }
}
