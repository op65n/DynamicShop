package tech.op65n.dynamicshop.engine.cache;

import com.mitchtalmadge.asciidata.graph.ASCIIGraph;
import com.rits.cloning.Cloner;
import lombok.Getter;
import org.apache.commons.math3.util.Precision;
import tech.op65n.dynamicshop.Core;
import tech.op65n.dynamicshop.database.DataSourceSetup;
import tech.op65n.dynamicshop.database.DataSource;
import tech.op65n.dynamicshop.database.ShopQuery;
import tech.op65n.dynamicshop.engine.components.SCategory;
import tech.op65n.dynamicshop.engine.components.SItem;
import tech.op65n.dynamicshop.engine.components.SItemHistory;
import tech.op65n.dynamicshop.engine.task.Task;


import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class DataSourceCache {

    private final Cloner cloner = new Cloner();
    private ShopQuery query;

    @Getter
    private final ConcurrentHashMap<Integer, SCategory> indexedCategoryCache = new ConcurrentHashMap<>();

    public DataSourceCache() {
        if (!DataSourceSetup.createTables()) return;

        List<SCategory> categoryList = Core.gCore().getEngine().container().getPrioritizedCategoryList().stream().map(SCategory::new).collect(Collectors.toList());
        query = new ShopQuery(DataSource.database);

        try {
            Connection connection = DataSource.connection();
            query.indexCategories(connection, categoryList);
            query.indexItems(connection, categoryList);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Core.gCore().getEngine()._LCACHE().getStartupInfo().setDbReady(false);
        }

        categoryList.forEach(category -> indexedCategoryCache.put(category.getID(), category));
        Core.gCore().getEngine()._LCACHE().getStartupInfo().setDbReady(true);
        syncCache();
    }

    public void syncCache() {
        Task.async(() -> {
            try {
                Connection connection = DataSource.connection();
                for (SCategory entry : indexedCategoryCache.values()) {
                    query.updateDataSource(connection, entry);
                }
                connection.close();
            } catch (SQLException e) {
                Core.gCore().getEngine()._LCACHE().getStartupInfo().setDbReady(false);
                e.printStackTrace();
            }
        });
    }

    public List<SCategory> getPrioritisedCategoryList() {
        List<SCategory> sorted = new ArrayList<>(indexedCategoryCache.values());
        sorted.sort(Comparator.comparing(SCategory::getPriority));
        return sorted;
    }

    public void buyItem(SItem item) throws IllegalAccessException {
        var category = indexedCategoryCache.getOrDefault(item.getCatID(), null);
        if (category == null) {
            throw new IllegalAccessException("How tf did you brake this?? Plugin requested category from id with null return CatID: " + item.getCatID() + " ItemID: " + item.getID());
        }
        boolean found = false;
        for (SItem cachedItem : category.getItems()) {
            if (cachedItem.getID() == item.getID()) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IllegalAccessException("How tf did you brake this?? Plugin requested Item from id with null return CatID: " + item.getCatID() + " ItemID: " + item.getID());
        }
        indexedCategoryCache.forEach((id, cat) -> {
            if (id != item.getCatID()) return;
            cat.getItems().forEach(cachedItem -> {
                if (cachedItem.getID() != item.getID()) return;
                cachedItem.getItemPricing().addBuys(item.getAmount());
                cachedItem.getItemHistory().addMark(cachedItem);
                System.out.println(generateGraph(cachedItem));
            });
        });
    }

    public void sellItem(SItem item) throws IllegalAccessException {
        var category = indexedCategoryCache.getOrDefault(item.getCatID(), null);
        if (category == null) {
            throw new IllegalAccessException("How tf did you brake this?? Plugin requested category from id with null return CatID: " + item.getCatID() + " ItemID: " + item.getID());
        }
        boolean found = false;
        for (SItem cachedItem : category.getItems()) {
            if (cachedItem.getID() == item.getID()) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IllegalAccessException("How tf did you brake this?? Plugin requested Item from id with null return CatID: " + item.getCatID() + " ItemID: " + item.getID());
        }
        indexedCategoryCache.forEach((id, cat) -> {
            if (id != item.getCatID()) return;
            cat.getItems().forEach(cachedItem -> {
                if (cachedItem.getID() != item.getID()) return;
                cachedItem.getItemPricing().addSells(item.getAmount());
                cachedItem.getItemHistory().addMark(cachedItem);
                System.out.println(generateGraph(cachedItem));
            });
        });
    }

    private String generateGraph(SItem item) {
        List<SItemHistory.Mark> marks = item.getItemHistory().getHistory();
        double[] demand = new double[100];
        int i = 0;
        for (SItemHistory.Mark historyMark : marks) {
            demand[i] = Precision.round(historyMark.getDemand(), 2);
            i++;
        }
        return ASCIIGraph.fromSeries(demand).withNumRows(30).plot();
    }



}
