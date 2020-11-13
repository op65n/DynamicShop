package com.sebbaindustries.dynamicshop.utils;

import com.sebbaindustries.dynamicshop.Core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author SebbaIndustries
 * @version 1.0
 */
public final class FileManager {

    /*
    configuration.properties
     */
    public File configuration;
    /*
    messages.properties
    */
    public File messages;

    public FileManager(Core core) {
        generateConfiguration(core);
        generateMessages(core);
        generateDirs(core);
        //generateREADME(core);
    }

    private boolean checkIfDirExists(Core core, String dirName) {
        return new File(core.getDataFolder() + "/" + dirName + "/").isDirectory();
    }

    public void generateDirs(Core core) {
        try {
            if (!checkIfDirExists(core,"shop")) {
                Files.createDirectory(Paths.get(core.getDataFolder() + "/shop/"));
            }
            if (!checkIfDirExists(core,"shop/items")) {
                Files.createDirectory(Paths.get(core.getDataFolder() + "/shop/items/"));
            }
            if (!checkIfDirExists(core,"shop/statistics")) {
                Files.createDirectory(Paths.get(core.getDataFolder() + "/shop/statistics/"));
            }
            if (!checkIfDirExists(core,"shop/gui")) {
                Files.createDirectory(Paths.get(core.getDataFolder() + "/shop/gui/"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateConfiguration(Core core) {
        if (configuration == null) {
            configuration = new File(core.getDataFolder(), "configuration.properties");
        }
        if (!configuration.exists()) {
            core.saveResource("configuration.properties", false);
        }
    }

    /**
     * Generates messages.properties File
     */
    public final void generateMessages(Core core) {
        if (messages == null) {
            messages = new File(core.getDataFolder(), "messages.properties");
        }
        if (!messages.exists()) {
            core.saveResource("messages.properties", false);
        }
    }

    /**
     * Generates README.md File
     */
    public final void generateREADME(Core core) {
        File README = new File(core.getDataFolder(), "README.md");

        if (!README.exists()) {
            core.saveResource("README.md", false);
        }
    }

}
