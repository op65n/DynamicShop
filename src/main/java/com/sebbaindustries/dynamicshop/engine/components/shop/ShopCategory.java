package com.sebbaindustries.dynamicshop.engine.components.shop;

import com.sebbaindustries.dynamicshop.engine.ui.interfaces.BukkitItemStack;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ShopCategory implements BukkitItemStack {

    private String fileName = "$NULL";
    private int ID = -1;
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

    @Override
    public String texture() {
        return icon.getTexture();
    }

    @Override
    public byte[] base64() {
        if (icon.getBase64() == null) return null;
        return Base64.getDecoder().decode(icon.getBase64());
    }
}
