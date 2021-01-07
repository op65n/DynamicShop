package com.sebbaindustries.dynamicshop.engine.cache;

import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.database.DBSetup;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopCategory;
import com.sebbaindustries.dynamicshop.utils.FileUtils;
import com.sebbaindustries.dynamicshop.utils.ObjectUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class LocalCache {

    private StartupInfo startupInfo = new StartupInfo();
    private CategoryFileInfo categoryFileInfo = new CategoryFileInfo();
    private IDInfo idInfo = new IDInfo();

    @Getter
    @Setter
    @NoArgsConstructor
    public static class StartupInfo {
        private boolean initStartup = true;
        private boolean configured = false;
        private boolean dbReady = false;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CategoryFileInfo {
        private List<ShopCategory> categories = new ArrayList<>();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class IDInfo {
        private HashMap<String, Integer> categoryIDs = new HashMap<>();
        private HashMap<String, Integer> itemIDs = new HashMap<>();
    }

    public void init() {
        if (!FileUtils.fileExists(".cache/startup_info.json")) {
            ObjectUtils.saveGsonFile(".cache/startup_info.json", startupInfo);
        }
        startupInfo = ObjectUtils.getGsonFile(".cache/startup_info.json", LocalCache.StartupInfo.class);

        if (!FileUtils.fileExists(".cache/category_file_info.json")) {
            ObjectUtils.saveGsonFile(".cache/category_file_info.json", categoryFileInfo);
        }
        categoryFileInfo = ObjectUtils.getGsonFile(".cache/category_file_info.json", LocalCache.CategoryFileInfo.class);

        if (!FileUtils.fileExists(".cache/id_info.json")) {
            ObjectUtils.saveGsonFile(".cache/id_info.json", idInfo);
        }
        idInfo = ObjectUtils.getGsonFile(".cache/id_info.json", LocalCache.IDInfo.class);
    }


    public void syncLCache() {
        Collection<ShopCategory> cached = categoryFileInfo.getCategories();
        Collection<ShopCategory> loaded = Core.gCore().getEngine().container().getPrioritizedCategoryList();

        Collection<ShopCategory> differentCached = new HashSet<>(loaded);
        Collection<ShopCategory> differentLoaded = new HashSet<>(cached);

        cached.forEach(cachedEntry -> loaded.forEach(loadedEntry -> {
            if (loadedEntry.getFileName().equals(cachedEntry.getFileName())) {
                differentCached.remove(loadedEntry);
                differentLoaded.remove(cachedEntry);
            }
        }));


        Core.devLogger.log("Different cached");
        differentCached.forEach(category -> Core.devLogger.log(category.getFileName()));
        DBSetup.createCategories(new ArrayList<>(differentCached));


        Core.devLogger.logWarn("Different loaded");
        differentLoaded.forEach(loadedCategoryName -> Core.devLogger.log(loadedCategoryName.getFileName()));

        categoryFileInfo.setCategories(new ArrayList<>(Core.gCore().getEngine().container().getPrioritizedCategoryList()));

    }

}
