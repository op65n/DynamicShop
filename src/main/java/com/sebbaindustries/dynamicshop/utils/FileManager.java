package com.sebbaindustries.dynamicshop.utils;

import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.log.PluginLogger;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.Arrays;
import java.util.logging.Level;

/**
 * @author SebbaIndustries
 * @version 1.0
 */
public final class FileManager {

    enum PluginFiles {
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

    public void generateMissingFiles(Core core) {
        Arrays.stream(PluginFiles.values()).forEach(file -> {

            File pluginFile = new File(core.getDataFolder(), file.fileName);
            if (pluginFile.exists()) return;

            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(file.fileName);

            // the stream holding the file content
            if (inputStream == null) {
                PluginLogger.logWarn("File " + file.fileName + " not found inside plugin jar!");
                return;
            }

            try {
                FileUtils.copyInputStreamToFile(inputStream, pluginFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
