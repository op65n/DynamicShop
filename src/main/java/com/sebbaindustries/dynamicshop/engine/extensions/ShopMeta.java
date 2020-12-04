package com.sebbaindustries.dynamicshop.engine.extensions;

public class ShopMeta {

    private double buyPrice;
    private double sellPrice;

    private boolean staticPrice = false;
    private double multiplier = 0.04;
    private double tax = 22;

    public ShopMeta(double buyPrice, double sellPrice) {
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
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

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }
}
