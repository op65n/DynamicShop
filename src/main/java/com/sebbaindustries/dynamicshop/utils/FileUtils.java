package com.sebbaindustries.dynamicshop.utils;

import com.sebbaindustries.dynamicshop.Core;

import java.io.File;

@SuppressWarnings("unused")
public class FileUtils {

    public static boolean checkIfExists(String fileName) {
        File f = new File(Core.gCore().core.getDataFolder() + "/" + fileName);
        return f.isFile() && !f.isDirectory();
    }

    public static Directory cdItems() {
        return new Directory("items");
    }

    public static Directory cdStatistics() {
        return new Directory("statistics");
    }

    public static Directory cdGUI() {
        return new Directory("gui");
    }

    public static class Directory {

        String dir;

        public Directory(String dir) {
            this.dir = dir;
        }

        public void saveGsonFile(String fileName, Object object) {
            ObjectUtils.saveGsonFile(("shop/" + dir + "/" + fileName), object);
        }

        public <T> T getGsonFile(String fileName, Class<T> cl) {
            return ObjectUtils.getGsonFile(("shop/" + dir + "/" + fileName), cl);
        }

        public boolean checkIfExists(String fileName) {
            return FileUtils.checkIfExists(("shop/" + dir + "/" + fileName));
        }
    }

}
