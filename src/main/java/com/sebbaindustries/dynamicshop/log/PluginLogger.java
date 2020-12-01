package com.sebbaindustries.dynamicshop.log;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PluginLogger {

    private static final Logger logger = Logger.getLogger("DynamicShop");

    /**
     * Logs normal #INFO style message to the console
     *
     * @param message String message, color will be default
     */
    @SuppressWarnings("unused")
    public static void log(String message) {
        logger.log(Level.INFO, message);
    }

    /**
     * Logs normal #ERROR style message to the console
     *
     * @param message String message, color will be red
     */
    @SuppressWarnings("unused")
    public static void logSevere(String message) {
        logger.log(Level.SEVERE, message);
    }

    /**
     * Logs normal #WARN style message to the console
     *
     * @param message String message, color will be yellow
     */
    @SuppressWarnings("unused")
    public static void logWarn(String message) {
        logger.log(Level.WARNING, message);
    }

}
