package tech.op65n.dynamicshop.engine.cache;

import com.google.common.collect.Multimap;
import com.rits.cloning.Cloner;
import org.apache.commons.lang3.tuple.Pair;
import tech.op65n.dynamicshop.Core;
import tech.op65n.dynamicshop.database.DataSource;
import tech.op65n.dynamicshop.database.ShopQuery;
import tech.op65n.dynamicshop.engine.components.EItemType;
import tech.op65n.dynamicshop.engine.components.SCategory;
import tech.op65n.dynamicshop.engine.components.SIcon;
import tech.op65n.dynamicshop.engine.structure.ItemStruct;
import tech.op65n.dynamicshop.engine.structure.ShopCategoryStruct;
import tech.op65n.dynamicshop.engine.task.Task;
import tech.op65n.dynamicshop.utils.ObjectUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ShopCache {

    private final Cloner cloner = new Cloner();
    private final ConcurrentHashMap<Integer, SCategory> indexedCategoryCache = new ConcurrentHashMap<>();
    private ShopQuery query;

    public void init(List<ShopCategoryStruct> diffCached, List<ShopCategoryStruct> diffLoaded) {
        query = new ShopQuery(DataSource.database);

        try {
            Connection connection = DataSource.connection();

            for (ShopCategoryStruct shopCategoryStruct : diffLoaded) query.removeCategory(connection, shopCategoryStruct);
            for (ShopCategoryStruct shopCategoryStruct : diffCached) query.createCategory(connection, shopCategoryStruct);
            query.loadCategories(connection, indexedCategoryCache);

            connection.close();

        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public SCategory getCategoryFromIndex(final int index) {
        if (indexedCategoryCache.getOrDefault(1, null) == null) {
            Core.engineLogger.log("Missing category index (" + index + ") in the indexCache!");
            return null;
        }
        return cloner.deepClone(indexedCategoryCache.get(index));
    }

    public List<SCategory> getPrioritisedCategoryList() {
        List<SCategory> sorted = new ArrayList<>(indexedCategoryCache.values());
        sorted.sort(Comparator.comparing(SCategory::getPriority));
        return sorted;
    }

}
