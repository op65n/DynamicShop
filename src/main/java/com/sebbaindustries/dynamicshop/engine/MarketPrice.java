package com.sebbaindustries.dynamicshop.engine;

public class MarketPrice {

    public static MarketPrice price = null;

    public final double base;  // Base price
    public final double rate;  // Rate at which the price changes (% + 1)
    public final double toll;  // Sales toll
    private int buys;          // Total buys
    private int sells;         // Total sells

    public MarketPrice(double base, double rate, double toll, int buys, int sells) {
        this.base  = base;
        this.rate  = rate;
        this.toll  = (1 - toll);
        this.buys  = buys;
        this.sells = sells;
    }

    public int getBuys()  { return buys;  }
    public int getSells() { return sells; }

    public void  addBuys(int amount) { buys  += amount; }
    public void addSells(int amount) { sells += amount; }

    public double price() {
        return base * Math.pow(rate, buys - sells);
    }

    public double buyPrice(int amount) {
        if (rate == 1.0) return price();
        return Math.max(0, price() * (1 - Math.pow(rate, amount)) / (1 - rate));
    }

    public double sellPrice(int amount) {
        if (rate == 1.0) return toll * price();
        return Math.max(0, toll * price() * (Math.pow(rate, -amount) - 1) / (1 - rate));
    }

}
