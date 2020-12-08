package com.sebbaindustries.dynamicshop.utils;

import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.log.PluginLogger;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.Arrays;

/**
 * @author SebbaIndustries
 * @version 1.0
 */
public final class FileManager {

    public enum PluginFiles {
        CONFIGURATION("configuration.toml"),
        MESSAGES("messages.toml"),
        README("README.md"),

        SHOP_CONFIGURATION("shop/shop_configuration.toml"),

        GUI_MAIN_PAGE("shop/gui/main_page.toml"),
        GUI_STORE_PAGE("shop/gui/store_page.toml"),
        GUI_TRANSACTION_PAGE("shop/gui/transaction_page.toml"),
        ;

        public String fileName;

        PluginFiles(String fileName) {
            this.fileName = fileName;
        }
    }

    public FileManager(Core core) {
        generateMissingFiles(core);
    }

    /**
     * Generates all preloaded files inside a plugin .jar file
     *
     * @param core Main class
     */
    public void generateMissingFiles(Core core) {
        Arrays.stream(PluginFiles.values()).forEach(file -> {

            File pluginFile = new File(core.getDataFolder(), file.fileName);
            // if file was already generated exit
            if (pluginFile.exists()) return;

            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(file.fileName);

            // the stream holding the file content
            if (inputStream == null) {
                PluginLogger.logWarn("File " + file.fileName + " not found inside plugin jar, please contact the plugin developer!");
                return;
            }

            try {
                // thanks Apache Utils!
                FileUtils.copyInputStreamToFile(inputStream, pluginFile);
            } catch (IOException e) {
                PluginLogger.logSevere("Encountered an error while trying to generate " + file.fileName + "!");
                e.printStackTrace();
            }
        });
    }

    /**
     * Gets file from the plugins data folder
     *
     * @param file Full file path
     * @return New File instance
     */
    public File getFile(PluginFiles file) {
        File pluginFile = new File(Core.gCore().core.getDataFolder(), file.fileName);
        if (!pluginFile.exists()) {
            PluginLogger.logSevere("File " + file.fileName + " not found!");
            return null;
        }
        return pluginFile;
    }

}
