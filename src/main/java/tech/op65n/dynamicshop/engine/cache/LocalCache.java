package tech.op65n.dynamicshop.engine.cache;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.lang3.tuple.Pair;
import tech.op65n.dynamicshop.Core;
import tech.op65n.dynamicshop.engine.structure.ItemStruct;
import tech.op65n.dynamicshop.engine.structure.ShopCategoryStruct;
import tech.op65n.dynamicshop.utils.FileUtils;
import tech.op65n.dynamicshop.utils.ObjectUtils;
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

        //Multimap<String, Pair<ItemStruct, ItemStruct>> changedItems = LinkedListMultimap.create();

        cached.forEach(cachedEntry -> loaded.forEach(loadedEntry -> {
            if (loadedEntry.getFilename().equals(cachedEntry.getFilename())) {
                differentCached.remove(loadedEntry);
                differentLoaded.remove(cachedEntry);
            }
        }));

        /*
        cachedEntry.getItem().forEach(cachedItemStruct -> loadedEntry.getItem().forEach(loadedItemStruct -> {
                    ItemStruct newItemStruct = applyDifferences(cachedItemStruct, loadedItemStruct);
                    if (newItemStruct != null) changedItems.put(loadedEntry.getFilename(), Pair.of(cachedItemStruct, newItemStruct));
                }));
         */

        Core.gCore().setShopCache(new ShopCache());
        Core.gCore().getShopCache().init(new ArrayList<>(differentCached), new ArrayList<>(differentLoaded));

        categoryFileInfo.setCategories(new ArrayList<>(Core.gCore().getEngine().container().getPrioritizedCategoryList()));

    }

    private ItemStruct applyDifferences(ItemStruct cItem, ItemStruct lItem) {
        boolean isChanged = false;
        ItemStruct itm = cItem;

        // Material
        if (!Objects.equals(cItem.getMaterial(), lItem.getMaterial())) {
            itm.setMaterial(lItem.getMaterial());
            isChanged = true;
        }
        // Texture
        if (!Objects.equals(cItem.getTexture(), lItem.getTexture())) {
            itm.setTexture(lItem.getTexture());
            isChanged = true;
        }

        // Base64
        if (!Objects.equals(cItem.getBase64(), lItem.getBase64())) {
            itm.setBase64(lItem.getBase64());
            isChanged = true;
        }

        // Priority
        if (!Objects.equals(cItem.getPriority(), lItem.getPriority())) {
            itm.setPriority(lItem.getPriority());
            isChanged = true;
        }

        // Display
        if (!Objects.equals(cItem.getDisplay(), lItem.getDisplay())) {
            itm.setDisplay(lItem.getDisplay());
            isChanged = true;
        }

        // Lore
        if (!Objects.equals(cItem.getLore(), lItem.getLore())) {
            itm.setLore(lItem.getLore());
            isChanged = true;
        }

        // Command
        if (!Objects.equals(cItem.getCommand(), lItem.getCommand())) {
            itm.setCommand(lItem.getCommand());
            isChanged = true;
        }

        // Price buy
        if (!Objects.equals(cItem.getPrice_buy(), lItem.getPrice_buy())) {
            itm.setPrice_buy(lItem.getPrice_buy());
            isChanged = true;
        }

        // Price sell
        if (!Objects.equals(cItem.getPrice_sell(), lItem.getPrice_sell())) {
            itm.setPrice_sell(lItem.getPrice_sell());
            isChanged = true;
        }

        return isChanged ? itm : null;
    }

}
