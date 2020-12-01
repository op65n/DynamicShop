package com.sebbaindustries.dynamicshop.engine.extensions;

import com.google.gson.JsonObject;
import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.log.PluginLogger;
import com.sebbaindustries.dynamicshop.utils.ObjectUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

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
        JsonObject jsonObject = ObjectUtils.getJson(fileName);

        if (jsonObject == null) {
            PluginLogger.log("Error encountered null json object (filename: " + fileName + ")!");
            return null;
        }

        this.buyPrice = jsonObject.get("buyPrice").getAsDouble();
        this.sellPrice = jsonObject.get("sellPrice").getAsDouble();
        this.staticPrice = jsonObject.get("staticPrice").getAsBoolean();
        this.tax = jsonObject.get("tax").getAsDouble();

        String itemStackName = jsonObject.getAsJsonObject("iStack").get("type").getAsString();
        if (itemStackName == null || itemStackName.equals("") || itemStackName.equals(" ")) {
            PluginLogger.log("Error encountered null json object for material name (filename: " + fileName + ")!");
            return null;
        }
        this.iStack = new ItemStack(Objects.requireNonNull(Material.matchMaterial(itemStackName)));

        // TODO: Add enchant support
        return this;
    }

    public void dataDump() {
        System.out.println(ObjectUtils.deserializeObjectToString(this));
    }

}
