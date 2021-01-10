package tech.op65n.dynamicshop.engine.container;

import com.moandjiezana.toml.Toml;
import tech.op65n.dynamicshop.Core;
import tech.op65n.dynamicshop.engine.components.maintainer.ComponentManager;
import tech.op65n.dynamicshop.engine.structure.DirectoryStructure;
import tech.op65n.dynamicshop.engine.structure.ShopCategoryStruct;
import tech.op65n.dynamicshop.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ShopContainer {

    private final List<ShopCategoryStruct> categories = new ArrayList<>();

    public ShopContainer() {
        load();
    }

    public void load() {

        // Check if there are files inside directory, if there are no files terminate the startup sequence
        List<String> files = FileUtils.getCategoryFilePaths();
        if (files == null || files.isEmpty()) {
            Core.engineLogger.logWarn("Plugin is not configured, maintenance mode is turned on!");
            // Add failed component to the list
            ComponentManager.addComponent(this.getClass(), "Missing files in plugins/DynamicShop/shop/categories/ directory");
            return;
        }

        // Clear the hashmap, necessary for the reloads
        categories.clear();

        // We iterate thru all file names, check if they end in .toml just to be sure
        files.forEach(fileName -> {
            // I dont thing any warns here are needed, this may be changed in the future tho.
            if (!fileName.endsWith(".toml")) return;

            // This caches every exception because I'm too lazy to find every one manually.
            // It works okay-ish for error handling, maybe do a cleaner implementation in the future?
            try {
                // Thanks to toml we can load classes to interfaces fairly easily.
                ShopCategoryStruct category = new Toml().read(
                        new File(DirectoryStructure.Directory.CATEGORIES.path + fileName)
                ).to(ShopCategoryStruct.class);
                category.setFilename(fileName);

                categories.add(category);
                Core.engineLogger.log("File " + fileName + " loaded successfully!");

            } catch (Exception e) {
                /*
                This produces a big fucking wall of text, but it works okay.
                 */
                Core.engineLogger.logWarn("Error happened while reading " + fileName + " please check if you have setup the plugin correctly.");
                e.printStackTrace();
                Core.engineLogger.logWarn("Error happened while reading " + fileName + " please check if you have setup the plugin correctly.");
                // Add failed component to the list
                ComponentManager.addComponent(this.getClass(), "Error in plugins/DynamicShop/shop/categories/ directory inside " + fileName + "file");
            }
        });
    }

    /**
     * Returns sorted list of categories based on priority value, base priority
     * value is -1, sorting works from lowest to highest
     *
     * @return Sorted ArrayList with ShopCategory component
     */
    public List<ShopCategoryStruct> getPrioritizedCategoryList() {
        List<ShopCategoryStruct> sorted = new ArrayList<>(categories);
        sorted.sort(Comparator.comparing(ShopCategoryStruct::getPriority));
        return sorted;
    }

}
