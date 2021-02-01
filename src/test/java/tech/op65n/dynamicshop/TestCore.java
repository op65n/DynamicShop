package tech.op65n.dynamicshop;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TestCore {

    private List<PricingTest.History> histories = new ArrayList<>();

    @Test
    public void testPricingModule() {

        PricingTest pricingTest = new PricingTest(4.56, 2.79, 2);

        for (int i = 0; i < 10; i++) {
            System.out.println("Price buy:" + pricingTest.calculatePriceBuy());
            System.out.println("Price sell:" + pricingTest.calculatePriceSell());

            PricingTest.History history = new PricingTest.History();
            history.setTransID(i);
            history.setBuys(40);
            history.setSells(60);
            histories.add(history);
            pricingTest.attachHistory(histories);

            System.out.println("Demand " + pricingTest.getDemand() + " Stock " + pricingTest.getStock());
            System.out.println(" ");
        }



    }


}
