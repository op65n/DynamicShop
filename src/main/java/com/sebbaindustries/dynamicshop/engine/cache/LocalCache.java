package com.sebbaindustries.dynamicshop.engine.cache;

import com.sebbaindustries.dynamicshop.engine.components.shop.ShopCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
        List<ShopCategory> categories = new ArrayList<>();
    }

}
