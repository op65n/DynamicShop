package com.sebbaindustries.dynamicshop.settings;

import com.sebbaindustries.dynamicshop.Core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
            //configuration.load(new FileInputStream(Core.getPlugin(Core.class).getDataFolder() + "/configuration.properties"));
            FileInputStream input = new FileInputStream(new File(Core.getPlugin(Core.class).getDataFolder() + "/configuration.properties"));
            configuration.load(new InputStreamReader(input, StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //detectionX = Double.parseDouble(configuration.getProperty("detection.x"));
    }

}
