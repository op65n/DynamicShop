package tech.op65n.dynamicshop.utils;

import tech.op65n.dynamicshop.Core;
import tech.op65n.dynamicshop.engine.structure.DirectoryStructure;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public final class FileUtils {

    /**
     * Checks plugins/DynamicShop/%fileName% for files
     *
     * @param fileName name of the file + path to it.
     * @return True if file exists and is not a directory.
     */
    public static boolean fileExists(String fileName) {
        File f = new File(Core.gCore().core.getDataFolder() + "/" + fileName);
        return f.isFile() && !f.isDirectory();
    }

    /**
     * Returns a list of files inside plugins/DynamicShop/shop/categories/ folder
     *
     * @return List of file names or null if no files are present
     */
    public static List<String> getCategoryFilePaths() {
        File categoryDirectory = new File(DirectoryStructure.Directory.CATEGORIES.path);
        if (!categoryDirectory.isDirectory()) {
            Core.engineLogger.logSevere("Can't find " + DirectoryStructure.Directory.CATEGORIES.path + " directory!");
            return null;
        }
        return Arrays.asList(categoryDirectory.list());
    }

}
