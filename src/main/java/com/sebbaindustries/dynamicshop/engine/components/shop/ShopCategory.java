package com.sebbaindustries.dynamicshop.engine.components.shop;

import com.sebbaindustries.dynamicshop.engine.ui.interfaces.BukkitItemStack;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ShopCategory implements BukkitItemStack {

    private int priority = 1000;
    private Icon icon = new Icon();

    private List<ShopItem> items = new ArrayList<>();

    public List<ShopItem> getOrderedItemList() {
        List<ShopItem> sorted = new ArrayList<>(items);
        sorted.sort(Comparator.comparing(ShopItem::getPriority));
        return sorted;
    }

    @Override
    public int amount() {
        return icon.getAmount();
    }

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
