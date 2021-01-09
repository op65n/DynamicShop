package com.sebbaindustries.dynamicshop.engine.cache;

import com.rits.cloning.Cloner;
import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.database.DataSource;
import com.sebbaindustries.dynamicshop.engine.components.EItemType;
import com.sebbaindustries.dynamicshop.engine.components.SCategory;
import com.sebbaindustries.dynamicshop.engine.components.SIcon;
import com.sebbaindustries.dynamicshop.engine.task.Task;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ShopCache {

    private final Cloner cloner = new Cloner();
    private final ConcurrentHashMap<Integer, SCategory> indexedCategoryCache = new ConcurrentHashMap<>();

    public void init() {
        Task.async(() -> {
            try {
                var connection = DataSource.connection();

                var fetch = connection.prepareStatement("SELECT * FROM category;");
                var fetchRes = fetch.executeQuery();
                while (fetchRes.next()) {
                    SCategory category = new SCategory();
                    int catID = fetchRes.getInt("id");
                    category.setID(catID);
                    category.setPriority(fetchRes.getInt("priority"));

                    SIcon icon = category.getIcon();
                    icon.setItem(fetchRes.getString("icon_item"));
                    switch (fetchRes.getInt("icon_type")) {
                        case 0 -> icon.setType(EItemType.MATERIAL);
                        case 1 -> icon.setType(EItemType.TEXTURE);
                        case 2 -> icon.setType(EItemType.BASE64);
                    }
                    icon.setDisplay(fetchRes.getString("icon_display"));
                    // TODO: setup lore

                    category.setIcon(icon);
                    indexedCategoryCache.put(catID, category);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
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
