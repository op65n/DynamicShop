package tech.op65n.dynamicshop.engine.components;

import lombok.Getter;
import tech.op65n.dynamicshop.Core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SItemHistory {

    public SItemHistory(List<Mark> storedHistoryMarks) {
        goldenDemand = Core.gCore().configuration.getShop().golden_demand;
        if (storedHistoryMarks == null || storedHistoryMarks.isEmpty()) return;
        storedHistoryMarks.sort(Comparator.comparing(Mark::getId));
        int stop = storedHistoryMarks.size() - Core.gCore().getConfiguration().getShop().history_length;
        if (stop < 1) return;
        for (int i = storedHistoryMarks.size() - 1; i > stop; i--) {
            Mark hMark = storedHistoryMarks.get(i);
            historyList.add(hMark);
            if (hMark.getId() >= nextID) nextID = hMark.getId() + 1;
        }
        historyList.sort(Comparator.comparing(Mark::getId));
    }

    private int nextID = 0;
    private final double goldenDemand;

    private List<Mark> historyList = new ArrayList<>();

    public void addMark(SItem item) {
        historyList.add(new Mark(
                getNextID(),
                item.getItemPricing().getPriceBuy(),
                item.getItemPricing().getPriceSell(),
                goldenDemand - item.getItemPricing().calculateCurrentDemand(),
                item.getItemPricing().getStock())
        );
        if (historyList.size() > Core.gCore().getConfiguration().getShop().history_length) historyList.remove(0);
    }

    public List<Mark> getHistory() {
        return this.historyList;
    }

    private int getNextID() {
        nextID++;
        return nextID;
    }

    @Getter
    public static class Mark {

        public Mark(int id, Double priceBuy, Double priceSell, Double demand, int stock) {
            this.id = id;
            this.priceBuy = priceBuy;
            this.priceSell = priceSell;
            this.demand = demand;
            this.stock = stock;
        }

        private final int id;
        private final Double priceBuy;
        private final Double priceSell;
        private final double demand;
        private final int stock;
    }

}
