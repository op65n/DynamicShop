package com.sebbaindustries.dynamicshop.engine.components.shop;

public class ShopMeta {

    private Boolean staticPrice;
    private Double multiplier;
    private Double tax;

    public Boolean isStaticPrice() {
        return staticPrice;
    }

    public void setStaticPrice(Boolean staticPrice) {
        this.staticPrice = staticPrice;
    }

    public Double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(Double multiplier) {
        this.multiplier = multiplier;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }
}
