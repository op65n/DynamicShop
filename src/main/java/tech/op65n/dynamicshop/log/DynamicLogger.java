package tech.op65n.dynamicshop.log;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DynamicLogger implements ILog {

    private final Logger logger = Logger.getLogger("DShop");

    /**
     * Debug logger for DynamicMarket Plugin, it accepts an object
     * and prints out deserialized information to the console,
     * permanent logging is disabled with this logger
     *
     * @param o Object for deserialization and logging
     */
    @Override
    public void debug(Object o) {
        logger.log(Level.FINE, "dfsdf");
    }

    /**
     * INFO style logger for DynamicMarket Plugin, it accepts a String and prints
     * it out to the console, permanent logging is disabled with this logger
     *
     * @param message Message for logger
     */
    @Override
    public void log(String message) {
        logger.info(message);
    }

    /**
     * WARN style logger for DynamicMarket Plugin, it accepts a String and prints
     * it out to the console, permanent logging is disabled with this logger
     *
     * @param message Message for logger
     */
    @Override
    public void warn(String message) {
        logger.log(Level.WARNING, message);
    }

    /**
     * ERROR style logger for DynamicMarket Plugin, it accepts a String and prints
     * it out to the console, permanent logging is disabled with this logger
     *
     * @param message Message for logger
     */
    @Override
    public void error(String message) {
        logger.log(Level.SEVERE, message);
    }

    /**
     * INFO style logger for DynamicMarket Plugin, it accepts a String and prints
     * it out to the console, permanent logging is enabled with this logger.
     * File name is taken from the config.
     *
     * @param message Message for logger
     */
    @Override
    public void permLog(String message) {
        // TODO: Add permanent logging
    }

    /**
     * WARN style logger for DynamicMarket Plugin, it accepts a String and prints
     * it out to the console, permanent logging is enabled with this logger.
     * File name is taken from the config.
     *
     * @param message Message for logger
     */
    @Override
    public void permWarn(String message) {
        // TODO: Add permanent logging
    }

    /**
     * ERROR style logger for DynamicMarket Plugin, it accepts a String and prints
     * it out to the console, permanent logging is enabled with this logger.
     * File name is taken from the config.
     *
     * @param message Message for logger
     */
    @Override
    public void permError(String message) {
        // TODO: Add permanent logging
    }

    /**
     * TODO: Add transaction component here
     * INFO style logger for DynamicMarket Plugin, it accepts a ShopTransaction object
     * and prints it out to the console, permanent logging is enabled with this logger.
     * File name is taken from the config, this is logged to separate file.
     *
     * @param transaction Transaction for logger
     */
    @Override
    public void transactionLog(String transaction) {
        // TODO: Add permanent logging
    }

}
