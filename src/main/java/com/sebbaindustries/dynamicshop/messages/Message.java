package com.sebbaindustries.dynamicshop.messages;

import com.sebbaindustries.dynamicshop.Core;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author SebbaIndustries
 */
public class Message {

    private Properties messages = null;

    public Message() {
        messages = new Properties();
        try {
            messages.load(new FileInputStream(Core.getPlugin(Core.class).getDataFolder() + "/messages.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //detectionX = Double.parseDouble(configuration.getProperty("detection.x"));
    }

    /**
     * Gets message from the properties file
     * @param name Message "name"
     * @return Un-Formatted message
     */
    public static String get(String name) {
        return Core.gCore().message.messages.getProperty(name);
    }

}
