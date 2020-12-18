package com.sebbaindustries.dynamicshop.engine.components.shop;

import com.sebbaindustries.dynamicshop.engine.components.gui.interfaces.BukkitItemStack;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ShopCategory implements BukkitItemStack {

    private int priority = 1000;
    private Icon icon;

    private List<ShopItem> items = new ArrayList<>();

    @Override
    public Material material() {
        return icon.getMaterial();
    }

    @Override
    public String display() {
        return icon.getDisplay();
    }

    @Override
    public List<String> lore() {
        return icon.getLore();
    }
}
