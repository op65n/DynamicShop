package com.sebbaindustries.dynamicshop.utils;

import com.sebbaindustries.dynamicshop.Core;

import java.io.File;

@SuppressWarnings("unused")
public final class FileUtils {

    public static boolean fileExists(String fileName) {
        File f = new File(Core.gCore().core.getDataFolder() + "/" + fileName);
        return f.isFile() && !f.isDirectory();
    }

}
