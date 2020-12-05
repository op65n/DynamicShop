package com.sebbaindustries.dynamicshop.engine.components.shop;

public class ShopMeta {

    private Double buyPrice;
    private Double sellPrice;

    private boolean staticPrice = false;
    private double multiplier = 0.04;
    private double tax = 22;

    public ShopMeta(Double buyPrice, Double sellPrice) {
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

    public Double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
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
