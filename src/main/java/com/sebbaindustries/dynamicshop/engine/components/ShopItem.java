package com.sebbaindustries.dynamicshop.engine.components;

import org.bukkit.Material;

public class ShopItem {

    private double buyPrice;
    private double sellPrice;
    private boolean staticPrice = false;
    private Material item;
    private String formattedName = "";
    private String name = "";
    private int orderNo = -1;

    public ShopItem(double buyPrice, double sellPrice, Material item, String name) {
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.item = item;
        this.name = name;
        this.formattedName = name;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public boolean isStaticPrice() {
        return staticPrice;
    }

    public void setStaticPrice(boolean staticPrice) {
        this.staticPrice = staticPrice;
    }

    public Material getItem() {
        return item;
    }

    public void setItem(Material item) {
        this.item = item;
    }

    public String getFormattedName() {
        return formattedName;
    }

    public void setFormattedName(String formattedName) {
        this.formattedName = formattedName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
