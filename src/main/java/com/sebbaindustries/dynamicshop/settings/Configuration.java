package com.sebbaindustries.dynamicshop.settings;

import com.sebbaindustries.dynamicshop.Core;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author SebbaIndustries
 * @version 1.0
 */
public class Configuration {

    private Properties configuration = null;

    public Configuration() {
        configuration = new Properties();
        try {
            // TODO: fix file encoding for Čč Šš Žž ect...
            configuration.load(new FileInputStream(Core.getPlugin(Core.class).getDataFolder() + "/configuration.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //detectionX = Double.parseDouble(configuration.getProperty("detection.x"));
    }

}
