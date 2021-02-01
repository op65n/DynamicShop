package tech.op65n.dynamicshop.engine.components;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.op65n.dynamicshop.engine.structure.ItemStruct;

@Getter
@Setter
@NoArgsConstructor
public class SItemPrice {

    private Double priceBuy = null;

    private Double priceSell = null;

    private int buys = 0;

    private int sells = 0;

    private boolean flatline = false;

    private Double cnfPriceBuy = null;

    private Double cnfPriceSell = null;

    private int stock = 0;

    private int currentBuys = 0;

    private int currentSells = 0;

    public SItemPrice(ItemStruct struct) {
        this.flatline = struct.isFlatline();
        this.cnfPriceBuy = struct.getPrice_buy();
        this.cnfPriceSell = struct.getPrice_sell();
    }

    public double calculateCurrentDemand() {
        return (double) (currentBuys + 1000) / (double) (currentSells + 1000);
    }

    public double calcBuyBase(int amount) {
        double goldenDemandStockCurve = ((double) (currentBuys + amount + 0x3E8) / (double) (currentSells + 0x3E8)) - 0.8;
        double artificialBaseExponent = (priceBuy * goldenDemandStockCurve) / (priceBuy + goldenDemandStockCurve);
        return priceBuy + artificialBaseExponent;
    }

    public double calcSellBase(int amount) {
        double goldenDemandStockCurve = ((double) (currentBuys + 0x3E8) / (double) (currentSells + amount + 0x3E8)) - 0.8;
        double artificialBaseExponent = (priceSell * goldenDemandStockCurve) / (priceSell + goldenDemandStockCurve);
        return priceSell - artificialBaseExponent;
    }

    public void addBuys(int amount) {
        this.currentBuys += amount;
    }

    public void addSells(int amount) {
        this.currentSells += amount;
    }

    public Double getPriceBuy() {
        if (priceBuy == null || priceBuy.isInfinite() || priceBuy.isNaN()) return 0.0;
        return priceBuy;
    }

    public Double getPriceSell() {
        if (priceSell == null || priceSell.isInfinite() || priceSell.isNaN()) return 0.0;
        return priceSell;
    }
}
