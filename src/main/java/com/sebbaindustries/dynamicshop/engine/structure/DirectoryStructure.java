package com.sebbaindustries.dynamicshop.engine.structure;

import com.sebbaindustries.dynamicshop.Core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DirectoryStructure {

    enum Directories {
        BASE(Core.gCore().core.getDataFolder() + "/"),
        SHOP(Core.gCore().core.getDataFolder()+ "/shop/"),
        GUI(Core.gCore().core.getDataFolder() + "/shop/gui/"),
        STATISTICS(Core.gCore().core.getDataFolder() + "/shop/statistics/"),
        CATEGORIES(Core.gCore().core.getDataFolder() + "/shop/categories/"),
        ;

        public String path;

        Directories(String path) {
            this.path = path;
        }
    }

    public void generateDirectoryStructure() {
        createIfMissing(Directories.SHOP.path);
        createIfMissing(Directories.GUI.path);
        createIfMissing(Directories.STATISTICS.path);
        createIfMissing(Directories.CATEGORIES.path);
    }

    private void createIfMissing(String directory) {
        if (new File(directory).isDirectory()) return;
        try {
            Files.createDirectory(Paths.get(directory));
            Core.gCore().log("Created " + directory + " directory!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
