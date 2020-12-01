package com.sebbaindustries.dynamicshop.engine.structure;

import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.log.PluginLogger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class DirectoryStructure {

    enum Directory {
        BASE("plugins/DynamicShop/"),
        SHOP("plugins/DynamicShop/shop/"),
        GUI("plugins/DynamicShop/shop/gui/"),
        STATISTICS("plugins/DynamicShop/shop/statistics/"),
        CATEGORIES("plugins/DynamicShop/shop/categories/"),
        ;

        public String path;

        Directory(String path) {
            this.path = path;
        }
    }

    public DirectoryStructure() {
        generateDirectoryStructure();
    }

    public void generateDirectoryStructure() {
        createMissing(Directory.BASE);
        createMissing(Directory.SHOP);
        createMissing(Directory.GUI);
        createMissing(Directory.STATISTICS);
        createMissing(Directory.CATEGORIES);
    }

    private void createMissing(Directory directory) {
        if (new File(directory.path).isDirectory()) return;
        try {
            Files.createDirectory(Paths.get(directory.path));
            PluginLogger.log("Created " + directory.path + " directory!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
