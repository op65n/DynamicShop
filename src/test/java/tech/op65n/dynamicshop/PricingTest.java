package tech.op65n.dynamicshop;

import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;

@Getter
@Setter
public class PricingTest {

    public PricingTest(double priceBuy, double priceSell, int stock) {
        this.priceBuy = priceBuy;
        this.priceSell = priceSell;
        this.stock = stock;
    }

    private double priceBuy;
    private double priceSell;

    private double demand;
    private int stock;

    public void attachHistory(List<History> history) {
        history.sort(Comparator.comparing(History::getTransID).reversed());
        int importance = 100;
        Double hDemand = null;

        for (History itemHistory : history) {
            if (importance < 1) break;
            double calculatedDemand = (double) itemHistory.getBuys() / (double) itemHistory.getSells();
            if (hDemand == null) {
                hDemand = calculatedDemand;
                if (calculatedDemand == 1.0) break;
            }
            hDemand = (hDemand * importance + calculatedDemand) / (double) importance + 1;
            importance--;
        }
        if (hDemand == null) hDemand = 1.0;
        demand = hDemand;
    }

    private double priceStock(double price) {
        return price * Math.pow(1.003, demand);
    }

    private double price(double price) {
        return price * Math.pow(1.05, demand);
    }

    public double calculatePriceBuy() {
        if (demand == 1.0) return priceBuy;
        if (stock > 0) {
           priceBuy = Math.max(0, priceStock(priceBuy) * (1 - Math.pow(1.003, 1)) / (1 - 1.003));
           return priceBuy;
        }
        priceBuy = Math.max(0, price(priceBuy) * (1 - Math.pow(1.05, 1)) / (1 - 1.05));
        return priceBuy;
    }

    public double calculatePriceSell() {
        if (demand == 1.0) return priceSell;
        if (stock > 0) {
            priceSell = Math.max(0, priceStock(priceSell) * (1 - Math.pow(1.003, 1)) / (1 - 1.003));
            return priceSell;
        }
        priceSell = Math.max(0, price(priceSell) * (1 - Math.pow(1.05, 1)) / (1 - 1.05));
        return priceSell;
    }

    @Getter
    @Setter
    public static class History {
        private int transID;
        private double boughtFor;
        private double soldFor;
        private int buys;
        private int sells;
    }

}
