package com.sebbaindustries.dynamicshop.utils;

import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.engine.structure.DirectoryStructure;
import com.sebbaindustries.dynamicshop.log.PluginLogger;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public final class FileUtils {

    public static boolean fileExists(String fileName) {
        File f = new File(Core.gCore().core.getDataFolder() + "/" + fileName);
        return f.isFile() && !f.isDirectory();
    }

    public static List<String> getFilePathsInCategories() {
        File categoryDirectory = new File(DirectoryStructure.Directory.CATEGORIES.path);
        if (!categoryDirectory.isDirectory()) {
            PluginLogger.logSevere("Can't find " + DirectoryStructure.Directory.CATEGORIES.path + " directory!");
            return null;
        }
        return Arrays.asList(categoryDirectory.list());
    }

}
