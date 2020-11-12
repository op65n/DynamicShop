package com.sebbaindustries.dynamicshop.utils;

import com.sebbaindustries.dynamicshop.Core;

import java.io.File;

public class FileUtils {

    public boolean checkIfExists(String fileName) {
        File f = new File(Core.gCore().core.getDataFolder() + "/" + fileName);

        return f.isFile() && !f.isDirectory();
    }

}
