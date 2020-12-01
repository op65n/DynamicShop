package com.sebbaindustries.dynamicshop.utils;

import com.sebbaindustries.dynamicshop.Core;
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
                core.getLogger().log(Level.WARNING, "File " + file.fileName + " not found inside plugin jar!");
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
