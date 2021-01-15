package tech.op65n.dynamicshop.engine.cache;

import com.rits.cloning.Cloner;
import lombok.Getter;
import tech.op65n.dynamicshop.Core;
import tech.op65n.dynamicshop.database.DBSetup;
import tech.op65n.dynamicshop.database.DataSource;
import tech.op65n.dynamicshop.database.ShopQuery;
import tech.op65n.dynamicshop.engine.components.SCategory;
import tech.op65n.dynamicshop.engine.task.Task;


import java.sql.Connection;
import java.sql.SQLException;
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
        if (!DBSetup.createTables()) return;

        List<SCategory> categoryList = Core.gCore().getEngine().container().getPrioritizedCategoryList().stream().map(SCategory::new).collect(Collectors.toList());
        query = new ShopQuery(DataSource.database);

        try {
            Connection connection = DataSource.connection();
            query.indexCategories(connection, categoryList);
            query.indexItems(connection, categoryList);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        categoryList.forEach(category -> indexedCategoryCache.put(category.getID(), category));
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
                e.printStackTrace();
            }
        });
    }

    public List<SCategory> getPrioritisedCategoryList() {
        List<SCategory> sorted = new ArrayList<>(indexedCategoryCache.values());
        sorted.sort(Comparator.comparing(SCategory::getPriority));
        return sorted;
    }


}
