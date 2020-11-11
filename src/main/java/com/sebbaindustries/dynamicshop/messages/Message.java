package com.sebbaindustries.dynamicshop.messages;

import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.utils.Color;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * @author SebbaIndustries
 */
public class Message {

    private Properties messages = null;

    public Message() {
        messages = new Properties();
        try {
            messages.load(new InputStreamReader(new FileInputStream(new File(Core.getPlugin(Core.class).getDataFolder() + "/messages.properties")), StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //detectionX = Double.parseDouble(configuration.getProperty("detection.x"));
    }

    /**
     * Gets message from the properties file and formats it
     * @param name Message "name"
     * @return Un-Formatted message
     */
    public static String get(String name) {
        return Color.format(Core.gCore().message.messages.getProperty(name));
    }

    /**
     * Gets message from the properties file
     * @param name Message "name"
     * @return Un-Formatted message
     */
    public static String getUnformatted(String name) {
        return Core.gCore().message.messages.getProperty(name);
    }

}
