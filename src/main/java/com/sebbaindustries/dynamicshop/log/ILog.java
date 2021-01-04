package com.sebbaindustries.dynamicshop.log;

import java.util.logging.Level;
import java.util.logging.Logger;

public interface ILog {

    Logger logger = Logger.getLogger("DynamicShop");

    default void log(String message) {
        logger.log(Level.INFO, message);
    }

    default void logWarn(String message) {
        logger.log(Level.WARNING, message);
    }

    default void logSevere(String message) {
        logger.log(Level.SEVERE, message);
    }

}
