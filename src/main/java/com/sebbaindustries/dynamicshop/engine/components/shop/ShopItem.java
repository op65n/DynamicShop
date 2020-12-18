package com.sebbaindustries.dynamicshop.engine.components.shop;

import com.sebbaindustries.dynamicshop.engine.components.gui.interfaces.BukkitItemStack;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.util.List;

@Getter
@Setter
public class ShopItem implements BukkitItemStack {

    private Material material;
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
}
