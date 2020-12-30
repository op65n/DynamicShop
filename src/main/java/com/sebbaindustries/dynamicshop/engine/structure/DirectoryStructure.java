package com.sebbaindustries.dynamicshop.engine.structure;

import com.sebbaindustries.dynamicshop.Core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DirectoryStructure {

    public enum Directory {
        BASE("plugins/DynamicShop/"),
        GUI("plugins/DynamicShop/gui/"),
        STATISTICS("plugins/DynamicShop/statistics/"),
        CATEGORIES("plugins/DynamicShop/categories/"),
        CACHE("plugins/DynamicShop/.cache/")
        ;

        public String path;

        Directory(String path) {
            this.path = path;
        }
    }

    public DirectoryStructure() {
        generateDirectoryStructure();
    }

    /**
     * Generates missing directories inside plugins/DynamicShop directory
     */
    public void generateDirectoryStructure() {
        createMissing(Directory.BASE);
        createMissing(Directory.GUI);
        createMissing(Directory.STATISTICS);
        createMissing(Directory.CATEGORIES);
        createMissing(Directory.CACHE);
    }

    private void createMissing(Directory directory) {
        if (new File(directory.path).isDirectory()) return;
        try {
            Files.createDirectory(Paths.get(directory.path));
            Core.engineLogger.log("Created " + directory.path + " directory!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
