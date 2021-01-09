package com.sebbaindustries.dynamicshop.engine.cache;

import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.database.DBSetup;
import com.sebbaindustries.dynamicshop.engine.components.SCategory;
import com.sebbaindustries.dynamicshop.engine.structure.ShopCategoryStruct;
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
        private List<ShopCategoryStruct> categories = new ArrayList<>();
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
    }


    public void syncLCache() {
        Collection<ShopCategoryStruct> cached = categoryFileInfo.getCategories();
        Collection<ShopCategoryStruct> loaded = Core.gCore().getEngine().container().getPrioritizedCategoryList();

        Collection<ShopCategoryStruct> differentCached = new HashSet<>(loaded);
        Collection<ShopCategoryStruct> differentLoaded = new HashSet<>(cached);

        cached.forEach(cachedEntry -> loaded.forEach(loadedEntry -> {
            if (loadedEntry.getFilename().equals(cachedEntry.getFilename())) {
                differentCached.remove(loadedEntry);
                differentLoaded.remove(cachedEntry);
            }
        }));


        Core.devLogger.log("Different cached");
        differentCached.forEach(category -> Core.devLogger.log(category.getFilename()));
        DBSetup.createCategories(new ArrayList<>(differentCached));


        Core.devLogger.logWarn("Different loaded");
        differentLoaded.forEach(loadedCategoryName -> Core.devLogger.log(loadedCategoryName.getFilename()));

        categoryFileInfo.setCategories(new ArrayList<>(Core.gCore().getEngine().container().getPrioritizedCategoryList()));

    }

}
