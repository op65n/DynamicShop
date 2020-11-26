package com.sebbaindustries.dynamicshop.engine.extensions;

import com.sebbaindustries.dynamicshop.utils.ObjectUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ExtItemStack {

    public ExtItemStack() {
        buyPrice = null;
        sellPrice = null;
    }

    public ExtItemStack(ItemStack iStack) {
        this.iStack = iStack;
    }

    private ItemStack iStack;
    private Double buyPrice = 5.00;
    private Double sellPrice = 3.00;
    private boolean staticPrice = false;
    private double tax = 22.0;

    public void serialize() {
        String fileName = iStack.getType().name();
        ObjectUtils.saveGsonFile(fileName, this);
    }

    public ExtItemStack deserialize(String fileName) {
        var o = ObjectUtils.getJson(fileName);
        assert o != null;
        this.buyPrice = o.get("buyPrice").getAsDouble();
        this.sellPrice = o.get("sellPrice").getAsDouble();
        this.staticPrice = o.get("staticPrice").getAsBoolean();
        this.tax = o.get("tax").getAsDouble();

        this.iStack = new ItemStack(Material.matchMaterial(o.getAsJsonObject("iStack").get("type").getAsString()));
        return this;
    }

    public void dump() {
        System.out.println(ObjectUtils.deserializeObjectToString(this));
    }

}
