package tech.op65n.dynamicshop.engine.container;

import tech.op65n.dynamicshop.engine.components.SItem;

public class ComputeContainer {

    private double goldenDemand = 0.8;

    public void computePricingModel(SItem item) {
        if (item.getItemPricing().getPriceBuy() == null || item.getItemPricing().getPriceSell() == null) return;

    }

}
