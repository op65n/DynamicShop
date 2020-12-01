package com.sebbaindustries.dynamicshop.engine.structure;

import com.sebbaindustries.dynamicshop.Core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DirectoryStructure {

    enum Directory {
        BASE(Core.gCore().core.getDataFolder() + "/"),
        SHOP(Core.gCore().core.getDataFolder() + "/shop/"),
        GUI(Core.gCore().core.getDataFolder() + "/shop/gui/"),
        STATISTICS(Core.gCore().core.getDataFolder() + "/shop/statistics/"),
        CATEGORIES(Core.gCore().core.getDataFolder() + "/shop/categories/"),
        ;

        public String path;

        Directory(String path) {
            this.path = path;
        }
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
            Core.gCore().log("Created " + directory.path + " directory!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
